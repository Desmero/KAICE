package fr.kaice.view.window;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.view.panel.PanelSoldProductIcon;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by merkling on 21/09/16.
 */
public class WindowStock extends JFrame implements Observer {
    
    public WindowStock() throws HeadlessException {
        KaiceModel.getInstance().addObserver(this);
         
        int num = KaiceModel.getSoldProdCollection().getRowCount();
        int edge = (int) Math.sqrt(((double) num)) + 1;
        
        this.setLayout(new GridLayout(edge, edge));
        for (SoldProduct soldProduct : KaiceModel.getSoldProdCollection().getList()) {
            this.add(new PanelSoldProductIcon(soldProduct));
        }
        
        Dimension windowDim;
        windowDim = new Dimension(1280, 1024);
        setPreferredSize(windowDim);
    
        pack();
        setVisible(true);
    
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.SOLD_PRODUCT, KaiceModel.RAW_MATERIAL)) {
            
        }
    }
}
