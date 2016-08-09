package fr.kaice.model.sell;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.SoldProduct.prodType;
import fr.kaice.tools.cells.CellRenderSoldProduct;
import fr.kaice.tools.generic.*;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class store all {@link SoldProduct} the programme need to know.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 4 columns : <br/>
 * - "Nom", witch display {@link SoldProduct}'s names (editable {@link String});<br/>
 * - "Prix de vente", witch display sell prices (editable {@link Double});<br/>
 * - "Prix d'achat", witch display buy prices (editable {@link Double});<br/>
 * - "Bénéfices", witch display benefits (editable {@link Double});<br/>
 * - "Quantité", witch display the available quantities (editable {@link Integer});<br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 2.1
 *
 * @see SoldProduct
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class SoldProductCollection extends DTableModel {
    
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_SELL_PRICE = 1;
    private static final int COL_NUM_BUY_PRICE = 2;
    private static final int COL_NUM_BENEFIT = 3;
    private static final int COL_NUM_QTY = 4;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, true);
    private static final DTableColumnModel colSellPrice = new DTableColumnModel("Prix de vente", Double.class, true);
    private static final DTableColumnModel colBuyPrice = new DTableColumnModel("Prix d'achat", Double.class, false);
    private static final DTableColumnModel colBenef = new DTableColumnModel("Bénéfices", Double.class, false);
    private static final DTableColumnModel colQty = new DTableColumnModel("Quantité disponible", Integer.class, false);
    private final Map<Integer, SoldProduct> map;
    private List<SoldProduct> alphabeticList;
    
    /**
     * Construct a {@link SoldProductCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public SoldProductCollection() {
        colModel = new DTableColumnModel[5];
        colModel[COL_NUM_BENEFIT] = colBenef;
        colModel[COL_NUM_BUY_PRICE] = colBuyPrice;
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_SELL_PRICE] = colSellPrice;
        map = new HashMap<>();
        alphabeticList = new ArrayList<>();
    }
    
    /**
     * Create and store a new {@link SoldProduct}, id auto-generate.
     *
     * @param product {@link String} - The name of the new {@link SoldProduct}.
     * @param price   int - The price of the new {@link SoldProduct}.
     * @param type    {@link prodType} - The type of the new {@link SoldProduct}.
     */
    public SoldProduct addNewSoldProduct(String product, int price, prodType type) {
        int id = getNewId();
        SoldProduct newMaterial = new SoldProduct(id, product, price, type);
        map.put(id, newMaterial);
        updateAlphabeticalList();
        return newMaterial;
    }
    
    /**
     * Generate a new free identification number for this collection of
     * {@link SoldProduct}.
     *
     * @return A new identification number for a {@link SoldProduct}.
     */
    private int getNewId() {
        int newId = -1;
        for (Integer i : map.keySet()) {
            newId = Integer.max(i, newId);
        }
        return newId + 1;
    }
    
    /**
     * Update the alphabetical sorted list.
     */
    private void updateAlphabeticalList() {
        ArrayList<SoldProduct> newList = new ArrayList<>(map.values());
        newList.sort((arg0, arg1) -> arg0.getName().compareTo(arg1.getName()));
        alphabeticList = newList;
    }
    
    /**
     * Return the {@link SoldProduct} corresponding to the given id.
     *
     * @param id int - The id of the product.
     * @return The {@link SoldProduct} corresponding to the given id.
     */
    public SoldProduct getSoldProduct(int id) {
        return map.get(id);
    }
    
    /**
     * Return all {@link SoldProduct} of a certain type, with a positive available quantity value, in a {@link ArrayList}.
     *
     * @param type {@link prodType} - The type of the product.
     * @return All available product of the given type in a {@link ArrayList}.
     */
    ArrayList<SoldProduct> getAvailableProduct(SoldProduct.prodType type) {
        return alphabeticList.stream().filter(prod -> prod.getType() == type).collect(Collectors.toCollection(ArrayList::new));
    }
    
    /**
     * Return a red color if the product of a given row is unavailable, null if not.
     *
     * @param row int - The row of a {@link SoldProduct}.
     * @return {@link DColor#RED} if the product is unavailable.
     */
    public Color getRowColor(int row) {
        Integer qtty = alphabeticList.get(row).getQuantity();
        if (qtty != null && qtty == 0) {
            return DColor.RED;
        } else {
            return null;
        }
    }
    
    @Override
    public int getRowCount() {
        return map.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SoldProduct prod = alphabeticList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                return prod.getName();
            case COL_NUM_SELL_PRICE:
                return DMonetarySpinner.intToDouble(prod.getPrice());
            case COL_NUM_BUY_PRICE:
                return DMonetarySpinner.intToDouble(prod.getBuyPrice());
            case COL_NUM_BENEFIT:
                return DMonetarySpinner.intToDouble(prod.getProfit());
            case COL_NUM_QTY:
                return prod.getQuantity();
            default:
                return null;
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        SoldProduct prod = alphabeticList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                prod.setName((String) aValue);
                break;
            case COL_NUM_SELL_PRICE:
                prod.setSalePrice(DMonetarySpinner.doubleToInt((double) aValue));
                break;
            default:
                break;
        }
        KaiceModel.update();
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_QTY) {
            return new CellRenderSoldProduct(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
        }
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }
    
}
