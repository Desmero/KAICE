package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProductCollection;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.view.window.WindowInform;

public class PanelPurchasedProduct extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel price;
	
	public PanelPurchasedProduct() {
		KaiceModel.getInstance().addObserver(this);
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getPurchasedProdCollection());
		JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
		JButton valid = new JButton("Valider"), cancel = new JButton("Annuler");
		DMonetarySpinner paid = new DMonetarySpinner(0.01);
		JCheckBox cash = new JCheckBox("Paiement liquide");
		JPanel ctrl = new JPanel(new BorderLayout());
		JPanel tablePanel = new JPanel(new BorderLayout());
		JPanel tableCtrl = new JPanel();
		JPanel paidCtrl = new JPanel();
		JPanel tradCtrl = new JPanel();
		price = new JLabel("0.00 �");
		
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KaiceModel.getInstance().setDetails(new PanelNewPurchasedProduct());
			}
		});
		view.setEnabled(false);
		
		table.setMultiselection(false);

		this.setLayout(new BorderLayout());
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		tablePanel.add(table, BorderLayout.CENTER);
		tablePanel.add(tableCtrl, BorderLayout.SOUTH);
		
		tableCtrl.add(add);
		tableCtrl.add(view);
		
		ctrl.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		ctrl.add(paidCtrl, BorderLayout.CENTER);
		ctrl.add(tradCtrl, BorderLayout.SOUTH);
		
		paidCtrl.add(new JLabel("Prix calcul� : "));
		paidCtrl.add(price);
		paidCtrl.add(new JLabel("Prix pai�e : "));
		paidCtrl.add(paid);
		paidCtrl.add(cash);
		
		tradCtrl.add(valid);
		tradCtrl.add(cancel);
	}

	@Override
	public void update(Observable o, Object arg) {
		PurchasedProductCollection coll = KaiceModel.getPurchasedProdCollection();
		double p = DMonetarySpinner.intToDouble(coll.getTotalPrice());
		price.setText("" + DFormat.MONEY_FORMAT.format(p) + " �");
	}

}
