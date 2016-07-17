package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.membre.Member;
import fr.kaice.model.sell.CurrentTransaction;
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
	private JLabel member;

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
		member = new JLabel();

		JButton add = new JButton("Ajouter");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				product.addSelection();
			}
		});
		JButton rem = new JButton("Retirer");
		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeProduct();
				KaiceModel.update();
			}
		});
		JButton valide = new JButton("Valider");
		valide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valid();
				reset();
			}
		});
		JButton cancel = new JButton("Annuler");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});

		JPanel tran = new JPanel(new BorderLayout());
		JPanel ctrl = new JPanel(new BorderLayout());

		JPanel ctrlButtonMember = new JPanel(new BorderLayout());
		JPanel ctrlButton = new JPanel();
		JPanel ctrlMember = new JPanel();
		JPanel price = new JPanel(new BorderLayout());
		JPanel cashPanel = new JPanel(new GridLayout(2, 2));
		JPanel totalPrice = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(product, BorderLayout.CENTER);
		this.add(tran, BorderLayout.SOUTH);

		tran.add(ctrl, BorderLayout.NORTH);
		tran.add(currentTran, BorderLayout.CENTER);

		ctrl.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		ctrl.add(ctrlButtonMember, BorderLayout.CENTER);
		ctrl.add(price, BorderLayout.EAST);

		ctrlButtonMember.add(ctrlButton, BorderLayout.NORTH);
		ctrlButtonMember.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		ctrlButtonMember.add(ctrlMember, BorderLayout.SOUTH);
		
		ctrlButton.add(add);
		ctrlButton.add(rem);
		ctrlButton.add(valide);
		ctrlButton.add(cancel);

		ctrlMember.add(member);
		
		price.add(cashPanel, BorderLayout.CENTER);
		price.add(totalPrice, BorderLayout.EAST);
		price.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.WEST);

		cashPanel.add(new JLabel("Espece : "));
		cashPanel.add(cash);
		cashPanel.add(cashBackText);
		cashPanel.add(cashBack);

		totalPrice.add(total);
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
		// cashBack.setText(DFormat.MONEY_FORMAT.format(0.0));
		KaiceModel.getCurrentTransaction().reset();
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
			member.setText("Client : ... (0)");
		} else {
			member.setText("Client : " + mem.getFullName() + " (" + mem.getUserId() + ")");
		}
	}

}
