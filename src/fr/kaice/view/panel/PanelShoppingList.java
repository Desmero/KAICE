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
class PanelShoppingList extends JPanel implements Observer {
    
    /**
     * Create a new {@link PanelShoppingList}
     */
    public PanelShoppingList() {
        KaiceModel.getInstance().addObserver(this);
        build();
    }
    
    /**
     * Reconstruct all the panel.
     */
    public void build() {
        JPanel list = new JPanel();
        JScrollPane scroll = new JScrollPane(list);
        
        HashMap<RawMaterial, Integer> map = KaiceModel.getRawMatCollection().getShoppingList();
        
        GroupLayout groupLayout = new GroupLayout(list);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        
        ArrayList<GroupLayout.ParallelGroup> hPGroupList = new ArrayList<>();
        
        for (RawMaterial mat : map.keySet()) {
            ArrayList<PurchasedProduct> matList = KaiceModel.getPurchasedProdCollection().getContainers(mat);
            int i = 0;
            GroupLayout.ParallelGroup vPGroupe = groupLayout.createParallelGroup();
            for (PurchasedProduct product : matList) {
                if (hPGroupList.size() <= i) {
                    hPGroupList.add(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING));
                }
                int number = map.get(mat) / product.getQuantity();
                if (map.get(mat) % product.getQuantity() != 0) {
                    number++;
                }
                ShoppingCheckBox box = new ShoppingCheckBox(product, number);
                hPGroupList.get(i).addComponent(box);
                vPGroupe.addComponent(box);
                i++;
            }
            vGroup.addGroup(vPGroupe);
        }
        for (GroupLayout.ParallelGroup pGroup : hPGroupList) {
            hGroup.addGroup(pGroup);
        }
        groupLayout.setVerticalGroup(vGroup);
        groupLayout.setHorizontalGroup(hGroup);
        list.setLayout(groupLayout);
        
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
        this.repaint();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.PURCHASED_PRODUCT)
                || KaiceModel.isPartModified(KaiceModel.RAW_MATERIAL)) {
            build();
        }
    }
}
