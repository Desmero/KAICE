package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProduct;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.ShoppingCheckBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * This panel display the list of {@link RawMaterial} below the alert level, and how much are needed to reach twice the
 * alert level.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see JPanel
 * @see KaiceModel
 */
class PanelShoppingList2 extends JPanel implements Observer {
    
    /**
     * Create a new {@link PanelShoppingList2}
     */
    public PanelShoppingList2() {
        KaiceModel.getInstance().addObserver(this);
        JPanel list = new JPanel();
        JScrollPane scroll = new JScrollPane(list);
        
        HashMap<RawMaterial, Integer> map = KaiceModel.getRawMatCollection().getShoppingList();
        
        GroupLayout groupLayout = new GroupLayout(list);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        GroupLayout.ParallelGroup hPGroup = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        
        for (RawMaterial mat : map.keySet()) {
            ArrayList<PurchasedProduct> matList = KaiceModel.getPurchasedProdCollection().getContainers(mat);
            for (PurchasedProduct product : matList) {
                int number = map.get(mat) / product.getQuantity();
                if (map.get(mat) % product.getQuantity() != 0) {
                    number++;
                }
                ShoppingCheckBox box = new ShoppingCheckBox(product, number);
                hPGroup.addComponent(box);
                vGroup.addGroup(groupLayout.createParallelGroup().addComponent(box));
            }
        }
        hGroup.addGroup(hPGroup);
        groupLayout.setVerticalGroup(vGroup);
        groupLayout.setHorizontalGroup(hGroup);
        list.setLayout(groupLayout);
        
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.PURCHASED_PRODUCT)
                || KaiceModel.isPartModified(KaiceModel.RAW_MATERIAL)) {
            KaiceModel.getInstance().setDetails(new PanelShoppingList2());
        }
    }
}
