package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.generic.DMonetarySpinner;

public class PanelNewPurchasedProduct extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelNewPurchasedProduct() {

		JButton accept = new JButton("Valide");
		JTextField name = new JTextField();
		DMonetarySpinner price = new DMonetarySpinner(0.01);
		JSpinner quantity = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
		JComboBox<RawMaterial> material = new JComboBox<>(KaiceModel.getRawMatCollection().getAllRawMaterial());

		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = material.getSelectedIndex();
				int id = (int) 	KaiceModel.getRawMatCollection().getIdAtRow(select);
				KaiceModel.getPurchasedProdCollection().addNewPurchasedProduct(name.getText(), price.getIntValue(),
						id, (int) quantity.getValue());
			}
		});

		this.setLayout(new BorderLayout());
		JPanel center = new JPanel(new GridLayout(4, 2));
		JPanel ctrl = new JPanel();
		
		this.add(center, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		center.add(new JLabel("Nom de l'articler :"));
		center.add(name);
		center.add(new JLabel("Prix :"));
		center.add(price);
		center.add(new JLabel("Produit :"));
		center.add(material);
		center.add(new JLabel("Quantité :"));
		center.add(quantity);
		
		ctrl.add(accept);
	}

}
