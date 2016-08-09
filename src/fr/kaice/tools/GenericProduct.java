package fr.kaice.tools;

/**
 * This interface represent any kind of product (sold, buy, raw material, ...).
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see fr.kaice.model.raw.RawMaterial
 * @see fr.kaice.model.sell.SoldProduct
 * @see fr.kaice.model.buy.PurchasedProduct
 * @see fr.kaice.model.historic.ArchivedProduct
 */
public interface GenericProduct {
    
    /**
     * Return the id of the products.
     *
     * @return The id of the products.
     */
    int getId();
    
    /**
     * Return the name of the products.
     *
     * @return The name of the products.
     */
    String getName();
    
    /**
     * Return the price of all the products.
     *
     * @return The price of all the products.
     */
    int getPrice();
}
