package fr.kaice.view.panel;

import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

/**
 * Created by merkling on 21/09/16.
 */
public class PanelSoldProductIcon extends JPanel {
    
    private final SoldProduct product;
    private final JLabel name;
    private final JLabel stock;
    private final JLabel price;
    private final sizedLabel logo;
    private ImageIcon icon;
    
    public PanelSoldProductIcon(SoldProduct product) {
        this.product = product;
        
        this.setLayout(new BorderLayout());
        this.setBackground(product.getType().getColor());
        
        JPanel details = new JPanel();
        name = new JLabel("nom");
        stock = new JLabel("0");
        price = new JLabel("0.00" + DFormat.EURO);
        icon = new ImageIcon("/home/merkling/.KAICE/Logo/" + product.getName() + ".jpg");
        logo = new sizedLabel();
        
        details.add(name);
        details.add(price);
        details.add(stock);
        
        this.add(logo, CENTER);
        this.add(details, SOUTH);
        
    }
    
    private class sizedLabel extends JLabel {
        @Override
        public void paintComponent (Graphics g) {
            super.paintComponent (g);
            if (icon != null) {
                int iw = icon.getIconWidth();
                int ih = icon.getIconHeight();
                double r = Double.min(((double) getWidth()) / iw, ((double) getHeight()) / ih);
                int w = (int) (iw * r), h = (int) (ih * r);
                int sw = (getWidth() - w) / 2;
                int sh = (getHeight() - h) / 2;
                g.drawImage(icon.getImage(), sw, sh, w, h, null);
            }
            name.setText(product.getName());
            if (product.getQuantity() == null) {
                stock.setText("");
            } else {
                stock.setText("(" + product.getQuantity() + ")");
            }
            price.setText(DMonetarySpinner.intToString(product.getPrice()));
        }
    }
}
