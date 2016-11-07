package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;

import static fr.kaice.tools.local.French.*;

/**
 * This panel display all {@link SoldProduct} contains in the
 * {@linkplain fr.kaice.model.sell.SoldProductCollection SoldProductCollection} known by {@link KaiceModel}.
 * The interface allow to visualise or create a new product.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see SoldProduct
 * @see fr.kaice.model.sell.SoldProductCollection
 */
public class PanelSoldProduct extends JPanel {
    
    /**
     * Create a nex {@link PanelSoldProduct}
     */
    public PanelSoldProduct() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getSoldProdCollection());
        JButton add = new JButton(B_ADD), view = new JButton(B_VIEW), hide = new JButton(B_HIDE);
        JPanel ctrl = new JPanel();
        
        add.addActionListener(e -> KaiceModel.getInstance().setDetails(new PanelNewSoldProduct()));
        view.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                SoldProduct prod = KaiceModel.getSoldProdCollection().getSoldProductAtRow(table.getSelectedRow());
                KaiceModel.getInstance().setDetails(new PanelSoldProductDetails(prod));
            }
        });
        if (KaiceModel.editor) {
            hide.setText(B_DELETE);
            hide.addActionListener(e -> KaiceModel.getSoldProdCollection().removeRow(table.getSelectedRow()));
        } else {
            hide.addActionListener(e -> KaiceModel.getSoldProdCollection().hideRow(table.getSelectedRow()));
        }

        table.setMultiSelection(false);
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        ctrl.add(add);
        ctrl.add(view);
        ctrl.add(hide);
    }
}
