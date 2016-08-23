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

        prod = new JLabel("Produit : " + product.getRawMat().getName());
        qty = new JLabel("Quantité : " + product.getQuantity());
        price = new JLabel("Prix : " + DMonetarySpinner.intToString(product.getPrice()));
        title = new PanelTitle("Achat : " + product.getName(), e -> KaiceModel.getInstance().setDetails(new JPanel()));

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        
        all.add(details, BorderLayout.NORTH);
        details.add(new JLabel("Produit : " + product.getRawMat().getName()));
        details.add(new JLabel("Quantité : " + product.getQuantity()));
        details.add(new JLabel("Prix : " + DMonetarySpinner.intToString(product.getPrice())));

        historic = new PartialHistoric(product);

        all.add(new DTablePanel(KaiceModel.getInstance(), historic), BorderLayout.CENTER);
    }

    @Override
    public void update(Observable o, Object arg) {
        prod.setText("Produit : " + product.getRawMat().getName());
        qty.setText("Quantité : " + product.getQuantity());
        price.setText("Prix : " + DMonetarySpinner.intToString(product.getPrice()));
        title.setTitle("Achat : " + product.getName());
    }
}
