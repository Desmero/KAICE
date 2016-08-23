package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProductCollectionVar;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * This panel display all {@linkplain fr.kaice.model.buy.PurchasedProduct PurchasedProduct} contains in the
 * {@link PurchasedProductCollectionVar} known by {@link KaiceModel}.
 * The interface allow to visualise or create a new product.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see PurchasedProductCollectionVar
 * @see fr.kaice.model.buy.PurchasedProduct
 */
public class PanelPurchasedProductVal extends JPanel {
    
    /**
     * Create a new {@link PurchasedProductCollectionVar}
     */
    public PanelPurchasedProductVal() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), new PurchasedProductCollectionVar());
        JButton add = new JButton("Ajouter"), view = new JButton("Visualiser"), hide = new JButton("Cacher");
        JPanel ctrl = new JPanel();
        
        add.addActionListener(e -> KaiceModel.getInstance().setDetails(new PanelNewPurchasedProduct()));
        view.addActionListener(e -> KaiceModel.getInstance().setDetails(KaiceModel.getPurchasedProdCollection().getProductAtRow(table.getSelectedRow()).getDetails()));
        hide.addActionListener(e -> KaiceModel.getPurchasedProdCollection().hideRow(table.getSelectedRow()));
        
        table.setMultiSelection(false);
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        ctrl.add(add);
        ctrl.add(view);
        ctrl.add(hide);
    }
}
