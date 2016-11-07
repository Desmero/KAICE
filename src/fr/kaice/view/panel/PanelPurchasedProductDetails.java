package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProduct;
import fr.kaice.model.historic.PartialHistoric;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static fr.kaice.tools.local.French.*;

/**
 * Created by merkling on 14/08/16.
 */
public class PanelPurchasedProductDetails extends JPanel implements Observer {
    
    private PurchasedProduct product;
    private JLabel prod, qty, price;
    private PanelTitle title;
    
    private final PartialHistoric historic;
    
    public PanelPurchasedProductDetails(PurchasedProduct product) {
        this.product = product;
        KaiceModel.getInstance().addObserver(this);
        
        JPanel all = new JPanel(new BorderLayout());
        JPanel details = new JPanel();
        
        if (product.getRawMat() != null) {
            prod = new JLabel(TF_PRODUCT + product.getRawMat().getName());
        } else {
            prod = new JLabel(TF_PRODUCT + NO_PROD);
        }
        qty = new JLabel(TF_QUANTITY + product.getQuantity());
        price = new JLabel(TF_PRICE + DMonetarySpinner.intToString(product.getPrice()));
        title = new PanelTitle(TITLE_DETAILS_PURCHASE_PRODUCT + product.getName(), e -> KaiceModel.getInstance().setDetails(new
                JPanel()));
        
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        
        all.add(details, BorderLayout.NORTH);
        details.add(prod);
        details.add(qty);
        details.add(price);
        
        historic = new PartialHistoric(product);
        
        all.add(new DTablePanel(KaiceModel.getInstance(), historic), BorderLayout.CENTER);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.HISTORIC_PERIOD)) {
            historic.update();
        }
        if (KaiceModel.isPartModified(KaiceModel.PURCHASED_PRODUCT)) {
            if (product.getRawMat() != null) {
                prod = new JLabel(TF_PRODUCT + product.getRawMat().getName());
            } else {
                prod = new JLabel(TF_PRODUCT + NO_PROD);
            }
            qty.setText(TF_QUANTITY + product.getQuantity());
            price.setText(TF_PRICE + DMonetarySpinner.intToString(product.getPrice()));
            title.setTitle(TITLE_DETAILS_PURCHASE_PRODUCT + product.getName());
        }
    }
}
