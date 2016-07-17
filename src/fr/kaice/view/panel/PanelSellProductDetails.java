package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

public class PanelSellProductDetails extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelSellProductDetails(SoldProduct prod) {

		JLabel name = new JLabel(prod.getName());
		name.setHorizontalAlignment(JLabel.CENTER);
		JLabel type = new JLabel("" + prod.getType());
		type.setHorizontalAlignment(JLabel.CENTER);
		JLabel quantity = new JLabel("Quantité : " + prod.getQuantity());
		quantity.setHorizontalAlignment(JLabel.CENTER);
		JLabel price = new JLabel(
				"Prix : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(prod.getSalePrice())) + " €");
		price.setHorizontalAlignment(JLabel.CENTER);
		JLabel cost = new JLabel(
				"Coût : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(prod.getBuyPrice())) + " €");
		cost.setHorizontalAlignment(JLabel.CENTER);
		JLabel profit = new JLabel(
				"Bénéfice : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(prod.getProfit())) + " €");
		profit.setHorizontalAlignment(JLabel.CENTER);

		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), prod, 6);

		JTable tableMat = new JTable(prod);
		JScrollPane scrollPaneMat = new JScrollPane(tableMat);
		Dimension d = tableMat.getPreferredSize();
		scrollPaneMat.setPreferredSize(new Dimension(d.width, tableMat.getRowHeight() * 6));
		// TableModelSellProductHistoric sellProd = new
		// TableModelSellProductHistoric(prod.getName());
		// JTable tableSell = new JTable(sellProd);
		// JScrollPane scrollPaneSell = new JScrollPane(tableSell);
		// d = tableSell.getPreferredSize();
		// scrollPaneSell.setPreferredSize(new Dimension(d.width,
		// tableSell.getRowHeight() * 16));

		JPanel detail = new JPanel(new BorderLayout());
		JPanel detailUp = new JPanel(new GridLayout(1, 3));
		JPanel detailDown = new JPanel(new GridLayout(1, 3));
		JPanel center = new JPanel(new BorderLayout());

		this.setLayout(new BorderLayout());
		this.add(detail, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);

		center.add(table, BorderLayout.NORTH);
		// center.add(scrollPaneSell, BorderLayout.CENTER);
		// center.add(new HistoricSelector(sellProd), BorderLayout.SOUTH);

		detail.add(detailUp, BorderLayout.NORTH);
		detail.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.CENTER);
		detail.add(detailDown, BorderLayout.SOUTH);
		detailUp.add(name);
		detailUp.add(type);
		detailUp.add(quantity);
		detailDown.add(price);
		detailDown.add(cost);
		detailDown.add(profit);
	}

}
