package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProduct;
import fr.kaice.model.historic.PartialHistoric;
import fr.kaice.tools.PeriodGetter;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.tools.generic.TimePeriodChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by merkling on 14/08/16.
 */
public class PanelPurchasedProductDetails extends JPanel implements PeriodGetter {
    
    private final PartialHistoric historic;
    private final TimePeriodChooser periodChooser;
    
    public PanelPurchasedProductDetails(PurchasedProduct product) {
        
        JPanel all = new JPanel(new BorderLayout());
        JPanel details = new JPanel(new BorderLayout());
        
        this.setLayout(new BorderLayout());
        this.add(new PanelTitle(product.getName()), BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        
        all.add(details, BorderLayout.NORTH);
        details.add(new JLabel("Prix : " + DMonetarySpinner.intToString(product.getPrice())), BorderLayout.WEST);
        details.add(new JLabel("Produit : " + product.getRawMat().getName()), BorderLayout.CENTER);
        details.add(new JLabel("Quantité : " + product.getQuantity()), BorderLayout.EAST);
        
        historic = new PartialHistoric(product);
        periodChooser = new TimePeriodChooser(this);
        
        all.add(new DTablePanel(KaiceModel.getInstance(), historic), BorderLayout.CENTER);
        all.add(periodChooser, BorderLayout.SOUTH);
    }
    
    @Override
    public void setPeriod(Date start, Date end) {
        historic.update(start, end);
    }
}
