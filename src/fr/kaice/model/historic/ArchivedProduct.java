package fr.kaice.model.historic;

import fr.kaice.tools.GenericProduct;

import java.io.Serializable;

/**
 * This class represent a product (any kind) used in a past {@link Transaction}. This stock the name, price and quantity
 * at the date of the transaction.
 *
 * @author Raphaël Merkling
 * @version 1.1
 */
public class ArchivedProduct implements GenericProduct, Serializable {
    
    private final String name;
    private final int quantity;
    private final int price;
    private final int id;
    
    /**
     * Create a new {@link ArchivedProduct}. The quantity should be a positive number. It is the {@link Transaction} who
     * define if it is an addition or a subtraction from the stock.
     *
     * @param name     {@link String} - The name of the product.
     * @param quantity int - The quantity add or remove from the stock (should be positive).
     * @param price    int - The price in cents of the product.
     * @param id       int - The product's id.
     */
    public ArchivedProduct(String name, int quantity, int price, int id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
    }
    
    /**
     * Return the quantity add or remove from the stock. Normally, this value is positive, it is the {@link Transaction}
     * who define if it is an addition from the stock or not.
     *
     * @return The quantity add or remove from the stock.
     */
    int getQuantity() {
        return quantity;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Return the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }
    
    @Override
    public int getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
