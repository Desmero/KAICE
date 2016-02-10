package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;
import fr.kaice.view.window.ViewSellProductDetails;

public class PanelPurcheseProduct extends JPanel {

	public PanelPurcheseProduct() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getPurchasedProdCollection());
		JButton add = new JButton("Ajouter"), remove = new JButton("Supprimer"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(remove);
		ctrl.add(view);
	}

}
