package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.view.window.WindowInform;

public class PanelMember extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelMember() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getMemberCollection());
		JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();
		JPanel selec = new JPanel(new BorderLayout());
		JPanel search = new JPanel(new GridLayout(2, 2));
		JTextField name = new JTextField(15);
		JTextField firstname = new JTextField(15);
		
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int memberId = KaiceModel.getMemberCollection().getNewId();
				KaiceModel.getInstance().setDetails(new PanelMemberDetails(memberId, true));
			}
		});
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int memberId = KaiceModel.getMemberCollection().getMemberIdAtRow(table.getSelectedRow());
					KaiceModel.getInstance().setDetails(new PanelMemberDetails(memberId));
				}
			}
		});

		table.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					KaiceModel.getMemberCollection().setSelectedMember(row);
				}
			}
		});
		table.getTable().getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = table.getTable().columnAtPoint(e.getPoint());
				String name = table.getTable().getColumnName(col);
				KaiceModel.getMemberCollection().setSortColl(name);
			}
		});

		name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				KaiceModel.getMemberCollection().setSearchName(name.getText());
			}
		});
		
		firstname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				KaiceModel.getMemberCollection().setSearchFirstname(firstname.getText());
			}
		});
		
		table.setWidth(300);
		table.setMultiselection(false);

		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(selec, BorderLayout.NORTH);
		this.add(ctrl, BorderLayout.SOUTH);
		
		selec.add(search, BorderLayout.CENTER);
		
		search.add(new JLabel("Nom :"));
		search.add(name);
		search.add(new JLabel("Prénom :"));
		search.add(firstname);
		
		
		ctrl.add(add);
		ctrl.add(view);
	}

}
