package fr.kaice.model.buy;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class store all {@link PurchasedProduct} the programme need to know.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 4 columns : <br/>
 * - "Nom", witch display names (editable {@link String});<br/>
 * - "Prix unitaire", witch display unitary prices (editable {@link Double});<br/>
 * - "Quantité", witch display the bought quantities (editable {@link Integer});<br/>
 * - "Prix total", witch display total bought prices (non editable {@link Double}).<br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 2.1
 * @see PurchasedProduct
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class PurchasedProductCollection extends DTableModel {
    
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_UNIT_PRICE = 1;
    private static final int COL_NUM_QTY = 2;
    private static final int COL_NUM_TOTAL_PRICE = 3;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, true);
    private static final DTableColumnModel colUnitPrice = new DTableColumnModel("Prix unitaire", Double.class, true);
    private static final DTableColumnModel colQty = new DTableColumnModel("Quantité", Integer.class, true);
    private static final DTableColumnModel colTotalPrice = new DTableColumnModel("Prix total", Double.class, false);
    private Map<Integer, PurchasedProduct> map;
    private List<PurchasedProduct> alphabeticList;
    
    /**
     * Construct a {@link PurchasedProductCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public PurchasedProductCollection() {
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_UNIT_PRICE] = colUnitPrice;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_TOTAL_PRICE] = colTotalPrice;
        deserialize();
        updateAlphabeticalList();
    }
    
    /**
     * Load a serialized historic and deserialize-it. Erase completely the current collection.
     */
    private void deserialize() {
        try {
            FileInputStream fileIn = new FileInputStream(KFilesParameters.pathPurchasedProduct);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(KFilesParameters.pathPurchasedProduct + " read successful.");
        } catch (IOException i) {
            System.err.println(KFilesParameters.pathPurchasedProduct + " read error : file not found.");
            map = new HashMap<>();
        } catch (ClassNotFoundException c) {
            System.out.println("HashMap<Integer, PurchasedProduct> class not found");
            c.printStackTrace();
        }
    }
    
    /**
     * Update the alphabetical sorted list.
     */
    private void updateAlphabeticalList() {
        ArrayList<PurchasedProduct> newList = new ArrayList<>(map.values());
        newList.sort((arg0, arg1) -> arg0.getName().compareTo(arg1.getName()));
        alphabeticList = newList;
    }
    
    /**
     * Create and store a new {@link PurchasedProduct}. The Id is auto-generated, and the quantity is set to 0.
     *
     * @param name           {@link String} - The name of the product.
     * @param purchasedPrice int - The price in cents of the product.
     * @param matId          int - The raw material's id that compose the product.
     * @param quantity       int - The quantity of {@link RawMaterial} contains in the product.
     */
    public void addNewPurchasedProduct(String name, int purchasedPrice, int matId, int quantity) {
        int id = getNewId();
        RawMaterial mat = KaiceModel.getRawMatCollection().getMat(matId);
        PurchasedProduct newProduct = new PurchasedProduct(id, name, purchasedPrice, mat, quantity);
        map.put(id, newProduct);
        serialize();
        updateAlphabeticalList();
        KaiceModel.update();
    }
    
    /**
     * Auto-generate a new free identification number for this collection of
     * {@link PurchasedProduct}.
     *
     * @return A new identification number for a {@link PurchasedProduct}.
     */
    private int getNewId() {
        int newId = -1;
        for (Integer i : map.keySet()) {
            newId = Integer.max(i, newId);
        }
        return newId + 1;
    }
    
    /**
     * Serialize the historic, and save-it in a file.
     */
    public void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream(KFilesParameters.pathPurchasedProduct);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Return the price in cents of all bought product. This calls {@link PurchasedProduct#getTotalPrice()}.
     *
     * @return The price in cents of all bought product.
     */
    public int getTotalPrice() {
        int price = 0;
        for (PurchasedProduct prod : alphabeticList) {
            price += prod.getTotalPrice();
        }
        return price;
    }
    
    /**
     * Return the {@link PurchasedProduct} corresponding to the given id. If the id is not used, return null.
     *
     * @param id int - The id of the wanted {@link PurchasedProduct}.
     * @return The {@link PurchasedProduct} corresponding to the given id, or null if the id is not used.
     */
    public PurchasedProduct getProd(int id) {
        return map.get(id);
    }
    
    @Override
    public int getRowCount() {
        return map.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PurchasedProduct prod = alphabeticList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                return prod.getName();
            case COL_NUM_UNIT_PRICE:
                return DMonetarySpinner.intToDouble(prod.getPrice());
            case COL_NUM_QTY:
                return prod.getNumberBought();
            case COL_NUM_TOTAL_PRICE:
                return DMonetarySpinner.intToDouble(prod.getTotalPrice());
            default:
                return null;
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PurchasedProduct prod = alphabeticList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                prod.setName((String) aValue);
                break;
            case COL_NUM_UNIT_PRICE:
                prod.setPurchasedPrice(DMonetarySpinner.doubleToInt((double) aValue));
                break;
            case COL_NUM_QTY:
                prod.setNumberBought((int) aValue);
                break;
        }
        serialize();
        KaiceModel.update();
    }
}
