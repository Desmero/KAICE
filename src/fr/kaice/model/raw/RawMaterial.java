package fr.kaice.model.raw;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.tools.GenericProduct;
import fr.kaice.tools.generic.DColor;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class represent one kind of basic item that compose {@linkplain fr.kaice.model.sell.SoldProduct SoldProduct}
 * <br/>
 * <p>
 * It contains : <br/> - A name; <br/> - A stock value; <br/> - A alert value; <br/> - And a unit price automatically
 * calculated; <br/>
 *
 * @author Raphaël Merkling
 * @version 2.1
 */
public class RawMaterial implements GenericProduct, Serializable {
    
    private static final long serialVersionUID = -8634053620056195331L;
    private final int id;
    private int unitPrice;
    private String name;
    private int stock;
    private int alert;
    private boolean hidden;
    private transient int restockNum; // Used for average unit price calculation
    private transient int restockCost; // Used for average unit price calculation
    
    /**
     * Simple constructor. Take only the name, auto-generate the id, and
     * initialize everything else to 0.
     *
     * @param name {@link String} - The name of the material.
     */
    public RawMaterial(int id, String name) {
        this.id = id;
        this.name = name;
        this.stock = 0;
        this.unitPrice = 0;
        this.alert = 0;
        this.restockNum = 0;
        this.restockCost = 0;
    }
    
    /**
     * Return the current stock value of the {@link RawMaterial}.
     *
     * @return The current stock value of the {@link RawMaterial}.
     */
    public int getStock() {
        return stock;
    }
    
    /**
     * Set the current stock value of the {@link RawMaterial}.
     * And add a new row in the historic.
     *
     * @param stock int - The new current stock value of the {@link RawMaterial}.
     */
    public void setStock(int stock) {
        int add = stock - this.stock;
        transactionType type = transactionType.ADD;
        if (add < 0) {
            type = transactionType.SUB;
            add = -add;
        }
        Transaction tran = new Transaction(0, type, 0, 0, new Date());
        ArchivedProduct archProd = new ArchivedProduct(name, add, 0, id);
        tran.addArchivedProduct(archProd);
        KaiceModel.getHistoric().addTransaction(tran);
        this.stock = stock;
    }
    
    /**
     * Return the alert value of the {@link RawMaterial}.
     *
     * @return The alert value of the {@link RawMaterial}.
     */
    public int getAlert() {
        return alert;
    }
    
    /**
     * Set the alert value of the {@link RawMaterial}.
     *
     * @param alert int - The new alert value of the {@link RawMaterial}.
     */
    public void setAlert(int alert) {
        this.alert = alert;
    }
    
    /**
     * Return the number of the {@link RawMaterial} to buy to increase the stock to the double of the alert value.
     *
     * @return The number of the {@link RawMaterial} to buy.
     */
    public int getNumberToBuy() {
        return Integer.max(0, (alert * 2 - stock));
    }
    
    /**
     * Return a color depending of the stock state of the {@link RawMaterial}. This state depends of the current stock
     * and alert values : <br/>
     * - stock > alert => White; <br/>
     * - alert > stock > 0 => Orange; <br/>
     * - stock = 0 => Red; <br/>
     * - stock < 0 (Error) => Gray <br/>
     *
     * @return The color of the state of the current stock.
     */
    public Color getColor() {
        Color col = DColor.WHITE;
        if (stock < 0) {
            col = DColor.GRAY;
        } else if (stock == 0) {
            col = DColor.RED;
        } else if (stock < alert) {
            col = DColor.ORANGE;
        }
        return col;
    }
    
    /**
     * Reduce the stock by the given value. This method should be used to reduce the stock, when a
     * {@linkplain fr.kaice.model.sell.SoldProduct SoldProduct} is sold.
     *
     * @param number int - The value to subtract to the stock.
     */
    public void consumption(int number) {
        stock -= number;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            RawMaterial material = (RawMaterial) obj;
            return this.getName().equals(material.getName()) && this.getId() == material.getId();
        } else {
            return false;
        }
    }
    
    /**
     * Return the id of the {@link RawMaterial}.
     *
     * @return The id of the {@link RawMaterial}.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Return the name of the {@link RawMaterial}.
     *
     * @return The name of the {@link RawMaterial}.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the {@link RawMaterial}.
     *
     * @param name {@link String} - The new name of the {@link RawMaterial}.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Return the unit price in cents of the {@link RawMaterial}.
     *
     * @return The unit price in cents of the {@link RawMaterial}.
     */
    public int getPrice() {
        return unitPrice;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public void addRestockNum(int addNum) {
        this.restockNum += addNum;
    }
    
    public void addRestockCost(int addCost) {
        this.restockCost = addCost;
    }
    
    void validRestock() {
        if (restockNum != 0) {
            unitPrice = restockCost / restockNum;
            stock += restockNum;
            restockNum = 0;
            restockCost = 0;
        }
    }
    
    public boolean isHidden() {
        return hidden;
    }
    
    public void changeHiddenState() {
        hidden = !hidden;
    }
}