package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProductCollectionLight;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * This panel display all {@linkplain fr.kaice.model.buy.PurchasedProduct PurchasedProduct} contains in the
 * {@link PurchasedProductCollectionLight} known by {@link KaiceModel}.
 * The interface allow to visualise or create a new product.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see PurchasedProductCollectionLight
 * @see fr.kaice.model.buy.PurchasedProduct
 */
public class PanelPurchasedProductLight extends JPanel {
    
    /**
     * Create a new {@link PurchasedProductCollectionLight}
     */
    public PanelPurchasedProductLight() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), new PurchasedProductCollectionLight());
        JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
        JPanel ctrl = new JPanel();
        
        add.addActionListener(e -> KaiceModel.getInstance().setDetails(new PanelNewPurchasedProduct()));
        view.setEnabled(false);
        
        table.setMultiSelection(false);
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        ctrl.add(add);
        ctrl.add(view);
    }
}