package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProductCollection;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This panel display all {@linkplain fr.kaice.model.buy.PurchasedProduct PurchasedProduct} contains in the
 * {@link PurchasedProductCollection} known by {@link KaiceModel}.
 * The interface allow to visualise or create a new product and valid a global restock.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see PurchasedProductCollection
 * @see fr.kaice.model.buy.PurchasedProduct
 */
public class PanelPurchasedProduct extends JPanel implements Observer {
    
    private final JLabel price;
    private final DMonetarySpinner paid;
    private final PanelShoppingList panelShoppingList;
    
    /**
     * Create a new {@link PanelPurchasedProduct}
     */
    public PanelPurchasedProduct() {
        KaiceModel.getInstance().addObserver(this);
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getPurchasedProdCollection());
        JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
        JButton valid = new JButton("Valider"), cancel = new JButton("Annuler");
        JButton shopping = new JButton("Liste de courses");
        paid = new DMonetarySpinner(0.01);
        JCheckBox cash = new JCheckBox("Paiement liquide");
        JPanel ctrl = new JPanel(new BorderLayout());
        JPanel tablePanel = new JPanel(new BorderLayout());
        JPanel tableCtrl = new JPanel();
        JPanel paidCtrl = new JPanel();
        JPanel tradCtrl = new JPanel();
        panelShoppingList = new PanelShoppingList();
        price = new JLabel("0.00 " + DFormat.EURO);
        
        add.addActionListener(e -> KaiceModel.getInstance().setDetails(new PanelNewPurchasedProduct()));
        view.addActionListener(e -> KaiceModel.getInstance().setDetails(KaiceModel.getPurchasedProdCollection()
                .getProductAtRow(table.getSelectedRow()).getDetails()));
        valid.addActionListener(e -> WindowAskAdmin.generate(e2 -> reset(KaiceModel.getPurchasedProdCollection()
                .validBought(paid.getIntValue(), cash.isSelected()))));
        cancel.addActionListener(e -> {
            KaiceModel.getPurchasedProdCollection().resetBought();
            reset(true);
        });
        shopping.addActionListener(e -> KaiceModel.getInstance().setDetails(panelShoppingList));
        
        table.setMultiSelection(false);
        
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
        
        paidCtrl.add(new JLabel("Prix calculé : "));
        paidCtrl.add(price);
        paidCtrl.add(new JLabel("Prix paiée : "));
        paidCtrl.add(paid);
        paidCtrl.add(cash);
        
        tradCtrl.add(valid);
        tradCtrl.add(cancel);
        tradCtrl.add(shopping);
    }
    
    private void reset(boolean res) {
        if (res) {
            paid.setValue(0);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.PURCHASED_PRODUCT)
                || KaiceModel.isPartModified(KaiceModel.RESTOCK)) {
            PurchasedProductCollection coll = KaiceModel.getPurchasedProdCollection();
            double p = DMonetarySpinner.intToDouble(coll.getTotalPrice());
            price.setText("" + DFormat.MONEY_FORMAT.format(p) + " " + DFormat.EURO);
        }
    }
}
