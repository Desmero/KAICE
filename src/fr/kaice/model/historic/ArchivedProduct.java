package fr.kaice.model.historic;

import fr.kaice.tools.GenericProduct;

/**
 * This class represent a product (any kind) used in a past {@link Transaction}. This stock the name, price and quantity
 * at the date of the transaction.
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
public class ArchivedProduct implements GenericProduct {
    
    private String name;
    private int quantity;
    private int price;
    
    /**
     * Create a new {@link ArchivedProduct}. The quantity should be a positive number. It is the {@link Transaction} who
     * define if it is an addition or a subtraction from the stock.
     *
     * @param name     {@link String} - The name of the product.
     * @param quantity int - The quantity add or remove from the stock (should be positive).
     * @param price    int - The price in cents of the product.
     */
    public ArchivedProduct(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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
    
    /**
     * The {@link GenericProduct} have not id. This function return -1.
     *
     * @return -1
     */
    @Override
    public int getId() {
        return -1;
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
