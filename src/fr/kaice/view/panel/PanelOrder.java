package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

public class PanelOrder extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelOrder() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getOrderCollection());
		JButton valid = new JButton("Valider"), rem = new JButton("Annuler");
		JPanel ctrl = new JPanel();
		DMonetarySpinner cashBack = new DMonetarySpinner(0.1);

		valid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				KaiceModel.getOrderCollection().validOrdre(row);
			}
		});
		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int row = table.getSelectedRow();
					KaiceModel.getOrderCollection().removeOrdre(row, cashBack.getIntValue());
				}
			}
		});

		table.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					KaiceModel.getOrderCollection().validOrdre(row);
				}
			}
		});

		table.setMultiselection(false);
		
		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(valid);
		ctrl.add(rem);
		ctrl.add(cashBack);
	}
}
