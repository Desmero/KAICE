package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.generic.DMonetarySpinner;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class PanelNewPurchasedProduct extends JPanel {
    
    private JLabel material;
    private JList<RawMaterial> list;
    
    /**
     * Create a new {@link PanelNewPurchasedProduct}.
     */
    public PanelNewPurchasedProduct() {
        
        JButton accept = new JButton("Valide");
        JTextField name = new JTextField();
        DMonetarySpinner price = new DMonetarySpinner(0.01);
        JSpinner quantity = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
        list = new JList<RawMaterial>(KaiceModel.getRawMatCollection().getAllRawMaterial());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane scrollList = new JScrollPane(list);
        material = new JLabel("...");
        
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                update();
            }
        });
        
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int select = list.getSelectedIndex();
                int id = (int) KaiceModel.getRawMatCollection().getIdAtRow(select);
                KaiceModel.getPurchasedProdCollection().addNewPurchasedProduct(name.getText(), price.getIntValue(),
                        id, (int) quantity.getValue());
            }
        });
        
        this.setLayout(new BorderLayout());
        JPanel centerDisp = new JPanel();
        JPanel center = new JPanel(new GridLayout(4, 2));
        JPanel ctrl = new JPanel();
        
        this.add(centerDisp, BorderLayout.EAST);
        this.add(scrollList, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        
        centerDisp.add(center);
        
        center.add(new JLabel("Nom de l'articler :"));
        center.add(name);
        center.add(new JLabel("Prix :"));
        center.add(price);
        center.add(new JLabel("Produit :"));
        center.add(material);
        center.add(new JLabel("Quantit? :"));
        center.add(quantity);
        
        ctrl.add(accept);
    }
    
    /**
     * Change the text of the selected {@link RawMaterial} label, by the name of the new selected material.
     */
    private void update() {
        int id = list.getSelectedIndex();
        String selection = KaiceModel.getRawMatCollection().getMat(id).getName();
        material.setText(selection);
    }
    
}
