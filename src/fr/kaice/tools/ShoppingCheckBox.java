package fr.kaice.tools;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProduct;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a {@link JCheckBox} linked to a {@link PurchasedProduct} with a quantity to buy. <br/>
 * The box have 3 states : <br/>
 * - Check : the bought quantity of the corresponding product is equal to the quantity to buy. <br/>
 * - Not check : the bought quantity is equal to 0. <br/>
 * - Not determined : the bought quantity is equal to something else. <br/>
 * The not determined state was inspired by a code found on
 * http://stackoverflow.com/questions/2701817/java-swing-jcheckbox-with-3-states-full-selected-partially-selected-and-dese
 *
 * @author Raphaël Merkling
 * @version 1.1
 * @see JCheckBox
 * @see PurchasedProduct
 */
public class ShoppingCheckBox extends JCheckBox implements Icon {
    
    public static final int NOT_SELECTED = 0;
    public static final int UNCOMPLETED = 1;
    public static final int SELECTED = 2;
    private final static Icon icon = UIManager.getIcon("CheckBox.icon");
    private final PurchasedProduct product;
    private final int quantity;
    private int state;
    
    /**
     * Create a {@link ShoppingCheckBox} linked to a {@link PurchasedProduct} with a quantity to buy.
     * The name of the checkBox is compose of the name of the product and the quantity.
     *
     * @param product
     * @param quantity
     */
    public ShoppingCheckBox(PurchasedProduct product, int quantity) {
        super();
        if (quantity > 1) {
            this.setText("\"" + product.getName() + "\" x" + quantity);
        } else {
            this.setText("\"" + product.getName() + "\"");
        }
        
        if (product.getNumberBought() == quantity) {
            this.setSelected(true);
            state = SELECTED;
        } else if (product.getNumberBought() == 0) {
            state = NOT_SELECTED;
        } else {
            state = UNCOMPLETED;
        }
        
        this.product = product;
        this.quantity = quantity;
        this.addActionListener(e -> checkChange());
        this.setIcon(this);
    }
    
    /**
     * Set the number bought of the product to the {@link ShoppingCheckBox} quantity if the box is checked, to 0 if the
     * box is unchecked.
     */
    private void checkChange() {
        if (this.isSelected()) {
            product.setNumberBought(quantity);
            state = SELECTED;
        } else {
            product.setNumberBought(0);
            state = NOT_SELECTED;
        }
        KaiceModel.update(KaiceModel.RESTOCK);
    }
    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        icon.paintIcon(c, g, x, y);
        if (state != UNCOMPLETED) {
            return;
        }
        
        int w = getIconWidth();
        int h = getIconHeight();
        g.setColor(c.isEnabled() ? new Color(51, 51, 51) : new Color(122, 138, 153));
        g.fillRect(x + 4, y + 4, w - 8, h - 8);
        
        if (!c.isEnabled()) return;
        g.setColor(new Color(81, 81, 81));
        g.drawRect(x + 4, y + 4, w - 9, h - 9);
    }
    
    @Override
    public int getIconWidth() {
        return icon.getIconWidth();
    }
    
    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }
}
