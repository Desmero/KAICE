package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.view.window.WindowInform;

public class PanelPurcheseProduct extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelPurcheseProduct() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getPurchasedProdCollection());
		JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowInform("Nouvelle article", false, new PanelNewPurchasedProduct());
			}
		});
		view.setEnabled(false);
		
		table.setMultiselection(false);
		
		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(view);
	}

}
