package fr.kaice.model.buy;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.GenericProduct;

import java.io.Serializable;

/**
 * This class represent one kind of purchased product. <br/>
 * It is composed of : <br/>
 * - An Id; <br/>
 * - A name; <br/>
 * - A price in cents; <br/>
 * - A {@link RawMaterial}; <br/>
 * - And the quantity of {@link RawMaterial}.
 *
 * @author Raphaël Merkling
 * @version 2.1
 */
public class PurchasedProduct implements GenericProduct, Serializable {
    
    private static final long serialVersionUID = -2994362280899753885L;
    private final int id;
    private final int materialId;
    private String name;
    private int purchasedPrice;
    private int quantity;
    private transient int numberBought;
    private boolean hidden;
    
    /**
     * Simple constructor. This use a auto-generate id.
     *
     * @param name           {@link String} - The name of the product.
     * @param purchasedPrice int - The purchase price in cents of the product.
     * @param mat            {@link RawMaterial} - The {@link RawMaterial} that corresponds to the product.
     * @param quantity       int - The number of the {@link RawMaterial} that contains the product.
     */
    public PurchasedProduct(int id, String name, int purchasedPrice, RawMaterial mat, int quantity) {
        this.id = id;
        this.name = name;
        this.purchasedPrice = purchasedPrice;
        this.materialId = mat.getId();
        this.quantity = quantity;
        this.numberBought = 0;
    }
    
    /**
     * Set the price in cents of the {@link PurchasedProduct}.
     *
     * @param purchasedPrice int - The price in cents of the {@link PurchasedProduct}.
     */
    void setPurchasedPrice(int purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }
    
    /**
     * Return the quantity of {@link RawMaterial} contains in this {@link PurchasedProduct}.
     *
     * @return The quantity of {@link RawMaterial}.
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Set the quantity of {@link RawMaterial} contains in this {@link PurchasedProduct}.
     *
     * @param quantity int - The quantity of {@link RawMaterial}.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Return the total price in cents of all {@link PurchasedProduct} bought.
     * It calls {@link PurchasedProduct#getNumberBought()} and {@link PurchasedProduct#getPrice()}.
     *
     * @return The total price in cents of all {@link PurchasedProduct} bought.
     */
    int getTotalPrice() {
        return getNumberBought() * getPrice();
    }
    
    /**
     * Return the number of {@link PurchasedProduct} bought.
     * This is used for calculating the average price af a {@link RawMaterial}.
     *
     * @return The number of {@link PurchasedProduct} bought.
     */
    public int getNumberBought() {
        return numberBought;
    }
    
    /**
     * Set the number of {@link PurchasedProduct} bought.
     *
     * @param numberBought int - The number of {@link PurchasedProduct} bought.
     */
    public void setNumberBought(int numberBought) {
        this.numberBought = numberBought;
    }
    
    /**
     * Return the id of the {@link PurchasedProduct}.
     *
     * @return The id of the {@link PurchasedProduct}.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Return the name of the {@link PurchasedProduct}.
     *
     * @return The name of the {@link PurchasedProduct}.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the price in cents of the {@link PurchasedProduct}.
     *
     * @return The price in cents of the {@link PurchasedProduct}.
     */
    public int getPrice() {
        return purchasedPrice;
    }
    
    /**
     * Set the name of the {@link PurchasedProduct}.
     *
     * @param name {@link String} - The new name of the {@link PurchasedProduct}
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * return the {@link RawMaterial} contains in the {@link PurchasedProduct}.
     *
     * @return The {@link RawMaterial} contains in the {@link PurchasedProduct}.
     */
    public RawMaterial getRawMat() {
        return KaiceModel.getRawMatCollection().getMat(materialId);
    }
    
    @Override
    public String toString() {
        return name + "(" + id + ")";
    }
    
    public boolean isHidden() {
        return hidden;
    }
    
    public void changeHiddenState() {
        hidden = !hidden;
    }
}
