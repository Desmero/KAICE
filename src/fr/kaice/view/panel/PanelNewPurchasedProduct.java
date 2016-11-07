package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.generic.DMonetarySpinner;

import javax.swing.*;
import java.awt.*;

import static fr.kaice.tools.local.French.*;

/**
 * This panel allow the user to create a new {@linkplain fr.kaice.model.buy.PurchasedProduct PurchasedProduct} and store
 * it in the {@linkplain fr.kaice.model.buy.PurchasedProductCollection PurchasedProductCollection} known by the
 * {@link KaiceModel}
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see fr.kaice.model.buy.PurchasedProductCollection
 * @see fr.kaice.model.buy.PurchasedProduct
 */
class PanelNewPurchasedProduct extends JPanel {
    
    private final JLabel material;
    private final JList<RawMaterial> list;
    private final JTextField name;
    private final DMonetarySpinner price;
    private final JSpinner quantity;
    
    /**
     * Create a new {@link PanelNewPurchasedProduct}.
     */
    PanelNewPurchasedProduct() {
        
        JButton accept = new JButton(B_VALID);
        name = new JTextField();
        price = new DMonetarySpinner(0.01);
        quantity = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
        list = new JList<>(KaiceModel.getRawMatCollection().getAllRawMaterial());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane scrollList = new JScrollPane(list);
        material = new JLabel("...");
        
        list.addListSelectionListener(e -> update());
        
        accept.addActionListener(e -> {
            int select = list.getSelectedIndex();
            RawMaterial material = KaiceModel.getRawMatCollection().getMaterialAtRow(select);
            int idMat;
            if (material == null) {
                idMat = -1;
            } else {
                idMat = material.getId();
            }
            KaiceModel.getPurchasedProdCollection().addNewPurchasedProduct(name.getText(), price.getIntValue(),
                    idMat, (int) quantity.getValue());
            reset();
        });
        
        this.setLayout(new BorderLayout());
        PanelTitle title = new PanelTitle(TITLE_BUY_NEW_ARTICLE, e -> KaiceModel.getInstance().setDetails(new JPanel()));
        JPanel centerPlace = new JPanel(new GridLayout(1, 2));
        JPanel centerField = new JPanel();
        JPanel center = new JPanel(new GridLayout(4, 2));
        JPanel ctrl = new JPanel();
    
        this.add(title, BorderLayout.NORTH);
        this.add(centerPlace, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
    
        centerPlace.add(scrollList);
        centerPlace.add(centerField);
    
        centerField.add(center);
        
        center.add(new JLabel(TF_ARTICLE_NAME));
        center.add(name);
        center.add(new JLabel(TF_PRICE));
        center.add(price);
        center.add(new JLabel(TF_PRODUCT));
        center.add(material);
        center.add(new JLabel(TF_QUANTITY));
        center.add(quantity);
        
        ctrl.add(accept);
    }
    
    /**
     * Change the text of the selected {@link RawMaterial} label, by the name of the new selected material.
     */
    private void update() {
        int index = list.getSelectedIndex();
        String selection = "...";
        if (index != -1) {
            selection = KaiceModel.getRawMatCollection().getMaterialAtRow(index).getName();
        }
        material.setText(selection);
    }
    
    /**
     * Clear all fields.
     */
    private void reset() {
        name.setText("");
        price.setValue(0);
        quantity.setValue(0);
        list.clearSelection();
    }
}
