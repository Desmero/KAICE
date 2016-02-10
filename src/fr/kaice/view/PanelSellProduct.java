package fr.kaice.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;

public class PanelSellProduct extends JPanel {

	public PanelSellProduct() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getSoldProdCollection());
		JButton add = new JButton("Ajouter"), remove = new JButton("Supprimer"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					new ViewSellProductDetails(
							KaiceModel.getSoldProdCollection().getSoldProduct(table.getSelectedRow()));
				}
			}
		});

		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(remove);
		ctrl.add(view);
	}

}
