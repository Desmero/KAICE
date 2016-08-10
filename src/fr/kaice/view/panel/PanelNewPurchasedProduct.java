package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.generic.DMonetarySpinner;

import javax.swing.*;
import java.awt.*;

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
        
        JButton accept = new JButton("Valide");
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
            int id = KaiceModel.getRawMatCollection().getIdAtRow(select);
            KaiceModel.getPurchasedProdCollection().addNewPurchasedProduct(name.getText(), price.getIntValue(),
                    id, (int) quantity.getValue());
            reset();
        });
        
        this.setLayout(new BorderLayout());
        JPanel centerPlace = new JPanel(new GridLayout(1, 2));
        JPanel centerField = new JPanel();
        JPanel center = new JPanel(new GridLayout(4, 2));
        JPanel ctrl = new JPanel();
    
        this.add(centerPlace, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
    
        centerPlace.add(scrollList);
        centerPlace.add(centerField);
    
        centerField.add(center);
        
        center.add(new JLabel("Nom de l'articler :"));
        center.add(name);
        center.add(new JLabel("Prix :"));
        center.add(price);
        center.add(new JLabel("Produit :"));
        center.add(material);
        center.add(new JLabel("Quantité :"));
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
