package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.stream.ImageInputStreamImpl;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.membre.Member;
import fr.kaice.model.membre.MemberCollection;
import fr.kaice.model.sell.CurrentTransaction;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

public class PanelCurrentTransaction extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DTablePanel currentTran;
	private DMonetarySpinner cash;
	private JLabel cashBack;
	private JLabel cashBackText;
	private JLabel total;
	private JTextField memberName;
	private JTextField memberFirstname;
	private IdSpinner memberId;
	

	public PanelCurrentTransaction() {
		KaiceModel.getInstance().addObserver(this);
		currentTran = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getCurrentTransaction(), 10);
		PanelChoosSoldProduct product = new PanelChoosSoldProduct();
		cash = new DMonetarySpinner(0.1);
		cash.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				KaiceModel.update();
			}
		});
		cashBack = new JLabel();
		cashBackText = new JLabel("Rendu : ");
		total = new JLabel("Total : 0.00€");
		total.setBorder(new LineBorder(Color.RED));
		total.setFont(new Font(total.getFont().getFontName(), Font.BOLD, 20));

		memberName = new JTextField();
		memberName.setColumns(10);
		memberName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectMember();
			}
		});
		memberFirstname = new JTextField();
		memberFirstname.setColumns(10);
		memberFirstname.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectMember();
			}
		});
		memberId = new IdSpinner();
		Dimension dimId = memberFirstname.getPreferredSize();
		dimId.setSize(80, dimId.getHeight());
		memberId.setPreferredSize(dimId);
		memberId.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				KaiceModel.getMemberCollection().setSelectedMemberById(memberId.getValue());
			}
		});
		
		JButton add = new JButton("Ajouter");
		add.setIcon(new ImageIcon("icon/downArrow.png"));
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				product.addSelection();
			}
		});
		JButton rem = new JButton("Retirer");
		rem.setIcon(new ImageIcon("icon/upArrow.png"));
		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeProduct();
				KaiceModel.update();
			}
		});
		JButton valide = new JButton("Valider");
		valide.setIcon(new ImageIcon("icon/valid.png"));
		valide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valid();
				reset();
			}
		});
		JButton cancel = new JButton("Annuler");
		cancel.setIcon(new ImageIcon("icon/cancel.png"));
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});

		JPanel tran = new JPanel(new BorderLayout());
		JPanel ctrl = new JPanel(new BorderLayout());

		this.setLayout(new BorderLayout());
		this.add(product, BorderLayout.CENTER);
		this.add(tran, BorderLayout.SOUTH);

		tran.add(ctrl, BorderLayout.NORTH);
		tran.add(currentTran, BorderLayout.CENTER);

		JPanel ctrlButton = new JPanel();
		JPanel ctrlMember = new JPanel();
		JPanel ctrlPrice = new JPanel();
		JPanel ctrlSeparator = new JPanel(new BorderLayout());
		
		ctrl.add(ctrlButton, BorderLayout.NORTH);
		ctrl.add(ctrlSeparator, BorderLayout.CENTER);
		ctrl.add(ctrlPrice, BorderLayout.SOUTH);
		
		ctrlSeparator.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		ctrlSeparator.add(ctrlMember, BorderLayout.CENTER);
		ctrlSeparator.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
		
		ctrlButton.add(valide);
		ctrlButton.add(cancel);
		ctrlButton.add(add);
		ctrlButton.add(rem);

		ctrlMember.add(new JLabel("Prènom : "));
		ctrlMember.add(memberFirstname);
		ctrlMember.add(new JLabel("Nom : "));
		ctrlMember.add(memberName);
		ctrlMember.add(new JLabel("Id : "));
		ctrlMember.add(memberId);

		ctrlPrice.add(new JLabel("Espece : "));
		ctrlPrice.add(cash);
		ctrlPrice.add(cashBackText);
		ctrlPrice.add(cashBack);
		ctrlPrice.add(total);

		update(null, null);
	}

	private void valid() {
		CurrentTransaction tran = KaiceModel.getCurrentTransaction();
		int res = JOptionPane.YES_OPTION;
		int price = tran.getPrice();
		int surp = cash.getIntValue() - price;
		// TODO membre non enregistrée
		if (res == JOptionPane.YES_OPTION && surp < 0) {
			res = JOptionPane.showConfirmDialog(null,
					"Le payment en espece est inssufisant, voulez-vous débiter le compte ?", "Espece insuffisant",
					JOptionPane.YES_NO_OPTION, 2);
			if (res == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "Pensez à débiter le compte dans le carnet de comptes, merci",
						"Comptes", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (res == JOptionPane.YES_OPTION) {
			int cashIn = Integer.min(price, cash.getIntValue());
			tran.validTransaction(cashIn);
		}
	}

	private void reset() {
		cash.setValue(0.);
		KaiceModel.getCurrentTransaction().reset();
		KaiceModel.getMemberCollection().setSelectedMember(null);
		KaiceModel.update();
	}

	private void removeProduct() {
		CurrentTransaction tran = KaiceModel.getCurrentTransaction();
		int lastRow = tran.getRowCount() - 1;
		ArrayList<Integer> items = tran.getAllProduct();
		int[] val = currentTran.getSelectedRows();
		for (int i = 0; i < val.length; i++) {
			if (val[i] != lastRow) {
				KaiceModel.getCurrentTransaction().removeSolldProduct(items.get(val[i]));
			}
		}
	}

	private void selectMember() {
		KaiceModel.getMemberCollection().setSelectedMemberByName(memberName.getText(), memberFirstname.getText());
	}
	
	public void update(Observable o, Object arg) {
		CurrentTransaction tran = KaiceModel.getCurrentTransaction();
		int price = tran.getPrice();
		int surp = cash.getIntValue() - price;
		
		total.setText("Total : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(price)) + " €");
		if (surp > 0) {
			cashBack.setText("" + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(surp)) + " €");
			cashBack.setForeground(Color.BLACK);
			cashBackText.setForeground(Color.BLACK);
		} else {
			surp = 0;
			cashBack.setText("" + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(surp)) + " €");
			cashBack.setForeground(Color.LIGHT_GRAY);
			cashBackText.setForeground(Color.LIGHT_GRAY);
		}
		Member mem = KaiceModel.getMemberCollection().getSelectedMember();
		if (mem == null) {
			memberName.setText("");
			memberFirstname.setText("");
			memberId.setValue(0);
		} else {
			memberName.setText(mem.getName());
			memberFirstname.setText(mem.getFirstname());
			memberId.setValue(mem.getUserId());
			
		}
	}

}
