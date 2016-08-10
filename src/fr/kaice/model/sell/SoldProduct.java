package fr.kaice.model.sell;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.tools.GenericProduct;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class represent one kind of sold product. <br/>
 * It is composed by : <br/>
 *  - An id; <br/>
 *  - A name; <br/>
 *  - A sale price; <br/>
 *  - A type; <br/>
 *  - And a collection of {@link RawMaterial}.
 *
 * @author Raphaël Merkling
 * @version 2.0
 *
 */
public class SoldProduct extends DTableModel implements GenericProduct {
    
    private static final int COL_NUM_ID = -1;
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_USED = 1;
    private static final int COL_NUM_STOCK = 2;
    private static final int COL_NUM_PRICE = 3;
    // private static DTableColumnModel colId = new DTableColumnModel("Id", Integer.class, false);
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, false);
    private static final DTableColumnModel colUsed = new DTableColumnModel("Quantité utilisée", Integer.class, true);
    private static final DTableColumnModel colStock = new DTableColumnModel("Stock", Integer.class, false);
    private static final DTableColumnModel colPrice = new DTableColumnModel("Prix unitaire", Double.class, false);
    private final int id;
    private final Map<RawMaterial, Integer> listRawMat;
    private final prodType type;
    private String name;
    private int salePrice;
    
    /**
     * SoldProduct constructor.
     *
     * @param id        int - The id of the product.
     * @param name      {@link String} - The name of the product.
     * @param salePrice int - The sale price of the product.
     * @param type      {@link prodType} - The type of the product.
     */
    public SoldProduct(int id, String name, int salePrice, prodType type) {
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_STOCK] = colStock;
        colModel[COL_NUM_USED] = colUsed;
        this.id = id;
        this.name = name;
        this.salePrice = salePrice;
        this.listRawMat = new HashMap<>();
        this.type = type;
    }
    
    /**
     * Reduce the stock a the {@link RawMaterial} that compose the {@link SoldProduct}.
     *
     * @param number int - The number of the {@link SoldProduct} sold.
     */
    void sale(int number) {
        RawMaterialCollection coll = KaiceModel.getRawMatCollection();
        for (Entry<RawMaterial, Integer> entry : listRawMat.entrySet()) {
            coll.sale(entry.getKey(), entry.getValue() * number);
        }
    }
    
    /**
     * Create an {@link ArchivedProduct} for the historic.
     *
     * @param number int - The number of the {@link SoldProduct} sold.
     * @return A new {@link ArchivedProduct}.
     */
    ArchivedProduct archivedProduct(int number) {
        return new ArchivedProduct(name, number, salePrice * number, id);
    }
    
    /**
     * Set the quantity of {@link RawMaterial} that need this product.
     * The quantity must be positive. If the quantity is equals to 0, the
     * {@link RawMaterial} is remove to collection.
     *
     * @param mat      /!\
     *                 {@link RawMaterial} - The raw material.
     * @param quantity int - The quantity, must be positive or equals to 0.
     */
    public void setRawMaterial(RawMaterial mat, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity equals to " + quantity + " must be positive.");
        } else if (quantity == 0) {
            listRawMat.remove(mat);
        } else {
            listRawMat.put(mat, quantity);
        }
    }
    
    /**
     * Set the sale price in cents of the {@link SoldProduct}.
     *
     * @param salePrice int - The new sale price in cents of the {@link SoldProduct}.
     */
    void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }
    
    /**
     * Return the id of the {@link SoldProduct}.
     *
     * @return The id of the {@link SoldProduct}.
     */
    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Return the name of the {@link SoldProduct}.
     *
     * @return The name of the {@link SoldProduct}.
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the {@link SoldProduct}.
     *
     * @param name The new name of the {@link SoldProduct}.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Return the sale price in cents of the {@link SoldProduct}.
     *
     * @return The sale price in cents of the {@link SoldProduct}.
     */
    @Override
    public int getPrice() {
        return salePrice;
    }
    
    /**
     * Return the {@link prodType} of the {@link SoldProduct}.
     *
     * @return The {@link prodType} of the {@link SoldProduct}.
     */
    public prodType getType() {
        return type;
    }
    
    /**
     * Return the profit in cents of the {@link SoldProduct}. Calculate by subtracting the sell and buy prices.
     *
     * @return The profit in cents of the {@link SoldProduct}.
     */
    public int getProfit() {
        return getPrice() - getBuyPrice();
    }
    
    /**
     * return the buy price in cents of the {@link SoldProduct}. Calculate with the price of the composing
     * {@link RawMaterial}.
     *
     * @return The buy price in cents of the {@link SoldProduct}.
     */
    public int getBuyPrice() {
        int price = 0;
        int qtt;
        for (RawMaterial mat : listRawMat.keySet()) {
            qtt = listRawMat.get(mat);
            price += qtt * mat.getPrice();
        }
        return price;
    }
    
    /**
     * Calculate the available quantity of the {@link SoldProduct} with the stock of each composing {@link RawMaterial}.
     *
     * @return The available quantity of the {@link SoldProduct}.
     */
    public Integer getQuantity() {
        int qty = Integer.MAX_VALUE;
        for (RawMaterial mat : listRawMat.keySet()) {
            qty = Integer.min(qty, (mat.getStock() / listRawMat.get(mat)));
        }
        if (qty == Integer.MAX_VALUE) {
            return null;
        }
        return qty;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int getRowCount() {
        return listRawMat.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<RawMaterial> list = new ArrayList<>(listRawMat.keySet());
        RawMaterial mat = list.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_ID:
                return mat.getId();
            case COL_NUM_NAME:
                return mat.getName();
            case COL_NUM_USED:
                return listRawMat.get(mat);
            case COL_NUM_STOCK:
                return mat.getStock();
            case COL_NUM_PRICE:
                return DMonetarySpinner.intToDouble(mat.getPrice());
            default:
                return null;
        }
    }
    
    /**
     * This define the type of a {@link SoldProduct}. This could be FOOD, DRINK
     * or MISC.
     *
     * @author Raph
     */
    public enum prodType {
        FOOD("Nourriture"), DRINK("Boisson"), MISC("Autre");
        
        private final String name;
        
        /**
         * Create a new element of the enumeration {@link prodType} with a display name.
         *
         * @param name {@link String} - The display name of the type.
         */
        prodType(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
}
