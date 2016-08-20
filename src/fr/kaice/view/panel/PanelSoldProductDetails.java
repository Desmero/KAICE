package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * This panel display all information about a {@link SoldProduct}.
 * The interface allow to edit a member the composition of the product.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see SoldProduct
 */
public class PanelSoldProductDetails extends JPanel {
    
    /**
     * Create a new {@link PanelSoldProductDetails} who display the details of the given {@link SoldProduct}.
     *
     * @param prod {@link SoldProduct} - The product to visualise.
     */
    public PanelSoldProductDetails(SoldProduct prod) {
        
        JLabel name = new JLabel(prod.getName());
        name.setHorizontalAlignment(JLabel.CENTER);
        JLabel type = new JLabel("" + prod.getType());
        type.setHorizontalAlignment(JLabel.CENTER);
        JLabel quantity = new JLabel("Quantité : " + prod.getQuantity());
        quantity.setHorizontalAlignment(JLabel.CENTER);
        JLabel price = new JLabel(
                "Prix : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(prod.getPrice())) + " ?");
        price.setHorizontalAlignment(JLabel.CENTER);
        JLabel cost = new JLabel(
                "Coût : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(prod.getBuyPrice())) + " ?");
        cost.setHorizontalAlignment(JLabel.CENTER);
        JLabel profit = new JLabel(
                "Bénéfice : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(prod.getProfit())) + " ?");
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
    
        JPanel all = new JPanel(new BorderLayout());
        PanelTitle title = new PanelTitle("Détails d'un article en vente", e -> KaiceModel.getInstance().setDetails(new JPanel()));
        JPanel detail = new JPanel(new BorderLayout());
        JPanel detailUp = new JPanel(new GridLayout(1, 3));
        JPanel detailDown = new JPanel(new GridLayout(1, 3));
        JPanel center = new JPanel(new BorderLayout());
    
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        all.add(detail, BorderLayout.NORTH);
        all.add(center, BorderLayout.CENTER);
        
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
