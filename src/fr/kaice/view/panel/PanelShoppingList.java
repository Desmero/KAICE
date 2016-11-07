package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProduct;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.ShoppingCheckBox;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static fr.kaice.tools.local.French.TITLE_SHOPPING_LIST;

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
        ArrayList<RawMaterial> mapSet = new ArrayList<>(map.keySet());
        mapSet.sort((arg0, arg1) -> arg0.getName().compareTo(arg1.getName()));
        
        GroupLayout groupLayout = new GroupLayout(list);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        
        ArrayList<GroupLayout.ParallelGroup> hPGroupList = new ArrayList<>();
        
        int line = 1;
        boolean addLine;
        hPGroupList.add(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING));
        for (RawMaterial mat : mapSet) {
            
            JLabel label = new JLabel(mat.getName());
    
            ArrayList<PurchasedProduct> matList = KaiceModel.getPurchasedProdCollection().getContainers(mat);
            int i = 1;
            addLine = false;
            GroupLayout.ParallelGroup vPGroup = groupLayout.createParallelGroup();
            if (matList.size() > 0) {
                hPGroupList.get(0).addComponent(label);
                vPGroup.addComponent(label);
            }
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
                vPGroup.addComponent(box);
                i++;
                addLine = true;
            }
            if (addLine) {
                line++;
            }
            vGroup.addGroup(vPGroup);
        }
        for (GroupLayout.ParallelGroup pGroup : hPGroupList) {
            hGroup.addGroup(pGroup);
        }
        groupLayout.setVerticalGroup(vGroup);
        groupLayout.setHorizontalGroup(hGroup);
        list.setLayout(groupLayout);
        
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new PanelTitle(TITLE_SHOPPING_LIST, e -> KaiceModel.getInstance().setDetails(new JPanel())),
                BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        
        this.setMinimumSize(new Dimension(this.getWidth(), Integer.min(200, line * 28)));
        
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
