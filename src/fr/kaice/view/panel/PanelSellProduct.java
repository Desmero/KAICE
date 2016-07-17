package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.view.window.WindowInform;

public class PanelSellProduct extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelSellProduct() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getSoldProdCollection());
		JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowInform("Nouvelle article", false, new PanelNewSellProduct());
			}
		});
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					SoldProduct prod = KaiceModel.getSoldProdCollection().getSoldProduct(table.getSelectedRow());
					KaiceModel.getInstance().setDetails(new PanelSellProductDetails(prod));
				}
			}
		});

		table.setMultiselection(false);
		
		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(view);
	}

}
