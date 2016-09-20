package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.PartialHistoric;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This panel display all information about a {@link SoldProduct}.
 * The interface allow to edit a member the composition of the product.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see SoldProduct
 */
public class PanelSoldProductDetails extends JPanel implements Observer {

    private SoldProduct product;
    private JLabel type, qty, price, cost, profit;
    private DTablePanel table;
    private PanelTitle title;
    private final PartialHistoric historic;

    /**
     * Create a new {@link PanelSoldProductDetails} who display the details of the given {@link SoldProduct}.
     *
     * @param product {@link SoldProduct} - The product to visualise.
     */
    public PanelSoldProductDetails(SoldProduct product) {
        this.product = product;
        KaiceModel.getInstance().addObserver(this);

        type = new JLabel("Type : " + product.getType());
        qty = new JLabel("Quantité : " + product.getQuantity());
        price = new JLabel("Prix : " + DMonetarySpinner.intToString(product.getPrice()));
        cost = new JLabel("Coût : " + DMonetarySpinner.intToString(product.getBuyPrice()));
        profit = new JLabel("Bénéfice : " + DMonetarySpinner.intToString(product.getProfit()));

        DTableModel tableModel = product.createTableModel();
        table = new DTablePanel(KaiceModel.getInstance(), tableModel, 6);
    
        historic = new PartialHistoric(product);
        DTablePanel panelHistoric = new DTablePanel(KaiceModel.getInstance(), historic);
    
        JTable tableMat = new JTable(tableModel);
        JScrollPane scrollPaneMat = new JScrollPane(tableMat);
        Dimension d = tableMat.getPreferredSize();
        scrollPaneMat.setPreferredSize(new Dimension(d.width, tableMat.getRowHeight() * 6));

        JPanel all = new JPanel(new BorderLayout());
        title = new PanelTitle("Vente : " + product.getName(), e -> KaiceModel.getInstance().setDetails(new JPanel()));
        JPanel detail = new JPanel();
        JPanel center = new JPanel(new BorderLayout());
    
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Membre", all);
        jTabbedPane.add("Historique", panelHistoric);
    
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(jTabbedPane, BorderLayout.CENTER);
        
        all.add(detail, BorderLayout.NORTH);
        all.add(center, BorderLayout.CENTER);
        
        center.add(table, BorderLayout.NORTH);

        detail.add(type);
        detail.add(qty);
        detail.add(price);
        detail.add(cost);
        detail.add(profit);
    }

    public void setBackPanel(JPanel back) {
        if (back == null) {
            title.setGreenAction(null);
        } else {
            title.setGreenAction(e -> KaiceModel.getInstance().setDetails(back));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.HISTORIC_PERIOD)) {
            historic.update();
        }
        if (KaiceModel.isPartModified(KaiceModel.SOLD_PRODUCT)) {
            type.setText("Type : " + product.getType());
            qty.setText("Quantité : " + product.getQuantity());
            price.setText("Prix : " + DMonetarySpinner.intToString(product.getPrice()));
            cost.setText("Coût : " + DMonetarySpinner.intToString(product.getBuyPrice()));
            profit.setText("Bénéfice : " + DMonetarySpinner.intToString(product.getProfit()));
            title.setTitle("Vente : " + product.getName());
            table.setNumberRow(product.getNumberCompo());
        }
    }
}
