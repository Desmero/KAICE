package fr.kaice.model.raw;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.cells.CellRenderRawMaterial;
import fr.kaice.tools.exeption.AlreadyUsedIdException;
import fr.kaice.tools.generic.DCellRender;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class store all {@link RawMaterial} the programme need to know.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 4 columns : <br/>
 * - "Nom", witch display {@link RawMaterial}'s names (editable {@link String});<br/>
 * - "Stock", witch display currents stocks (editable {@link Integer});<br/>
 * - "Prix", witch display unit prices (non editable {@link Double}).<br/>
 * - "Alert", witch display alert values(editable {@link Integer}).<br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 2.1
 *
 * @see RawMaterial
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class RawMaterialCollection extends DTableModel {
    
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_QTY = 1;
    private static final int COL_NUM_PRICE = 2;
    private static final int COL_NUM_ALERT = 3;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, true);
    private static final DTableColumnModel colQty = new DTableColumnModel("Stock", Integer.class, true);
    private static final DTableColumnModel colPrice = new DTableColumnModel("Prix", Double.class, false);
    private static final DTableColumnModel colAlert = new DTableColumnModel("Alert", Double.class, true);
    
    private final Map<Integer, RawMaterial> map;
    private List<RawMaterial> alphabeticList;
    
    /**
     * Construct a {@link RawMaterialCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public RawMaterialCollection() {
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_ALERT] = colAlert;
        map = new HashMap<>();
        alphabeticList = new ArrayList<>();
    }
    
    /**
     * Create and store a new {@link RawMaterial}, id auto-generate and stock,
     * alert and price initialize to 0.
     *
     * @param product {@link String} - The name of the new {@link RawMaterial}.
     */
    public void addNewRawMaterial(String product) {
        int id = getNewId();
        RawMaterial newMaterial = new RawMaterial(id, product);
        addRawMaterial(newMaterial);
        KaiceModel.update();
    }
    
    /**
     * Generate a new free identification number for this collection of {@link RawMaterial}.
     *
     * @return A new identification number for a {@link RawMaterial}.
     */
    private int getNewId() {
        int newId = -1;
        for (Integer i : map.keySet()) {
            newId = Integer.max(i, newId);
        }
        return newId + 1;
    }
    
    /**
     * Store an existing {@link RawMaterial}.
     *
     * @param mat {@link RawMaterial} - The raw material to store in the collection.
     */
    private void addRawMaterial(RawMaterial mat) {
        int id = mat.getId();
        if (map.containsKey(id)) {
            throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
        }
        map.put(id, mat);
        updateAlphabeticalList();
    }
    
    /**
     * Update the alphabetical sorted list.
     */
    private void updateAlphabeticalList() {
        ArrayList<RawMaterial> newList = new ArrayList<>(map.values());
        newList.sort((arg0, arg1) -> arg0.getName().compareTo(arg1.getName()));
        alphabeticList = newList;
    }
    
    /**
     * Use a given amount af a given {@link RawMaterial} by selling a
     * {@linkplain fr.kaice.model.sell.SoldProduct SoldProduct}.
     *
     * @param mat    {@link RawMaterial} - The material used in the product sold.
     * @param number int - The number of the raw material used.
     */
    public void sale(RawMaterial mat, int number) {
        mat.consumption(number);
        KaiceModel.update();
    }
    
    /**
     * Return a {@link HashMap} with in key, the {@link RawMaterial} needed to buy, and in value the quantity.
     * This method use {@link RawMaterial#getNumberToBuy()}.
     *
     * @return A {@link HashMap} contains all {@link RawMaterial} needed to buy, and their quantity.
     */
    public HashMap<RawMaterial, Integer> getShoppingList() {
        HashMap<RawMaterial, Integer> list = new HashMap<>();
        int buyNum;
        for (RawMaterial mat : alphabeticList) {
            buyNum = mat.getNumberToBuy();
            if (buyNum > 0) {
                list.put(mat, buyNum);
            }
        }
        return list;
    }
    
    /**
     * Return the {@link Color} of a {@link RawMaterial} choose by a row number, depending of his stock and alert value, using
     * {@link RawMaterial#getColor()}.
     *
     * @param row int - The row of the {@link RawMaterial}.
     * @return The color of the {@link RawMaterial} chosen by the row number.
     */
    public Color getRowColor(int row) {
        return alphabeticList.get(row).getColor();
    }
    
    /**
     * Return the {@link RawMaterial} corresponding to the given id.
     *
     * @param id int - The id number of a {@link RawMaterial}
     * @return The {@link RawMaterial} corresponding to the given id.
     */
    public RawMaterial getMat(int id) {
        return map.get(id);
    }
    
    // TODO remove this method
    public int getIdAtRow(int row) {
        return alphabeticList.get(row).getId();
    }
    
    /**
     * Return an array contening all {@link RawMaterial}.
     *
     * @return An array contening all {@link RawMaterial}.
     */
    public RawMaterial[] getAllRawMaterial() {
        RawMaterial[] tab = new RawMaterial[alphabeticList.size()];
        int i = 0;
        for (RawMaterial mat : alphabeticList) {
            tab[i++] = mat;
        }
        return tab;
    }
    
    @Override
    public int getRowCount() {
        return map.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COL_NUM_NAME:
                return alphabeticList.get(rowIndex).getName();
            case COL_NUM_QTY:
                return alphabeticList.get(rowIndex).getStock();
            case COL_NUM_PRICE:
                return DMonetarySpinner.intToDouble(alphabeticList.get(rowIndex).getPrice());
            case COL_NUM_ALERT:
                return alphabeticList.get(rowIndex).getAlert();
            default:
                return null;
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        RawMaterial mat = alphabeticList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                mat.setName((String) aValue);
                break;
            case COL_NUM_QTY:
                mat.setStock((int) aValue);
                break;
            case COL_NUM_ALERT:
                mat.setAlert((int) aValue);
                break;
        }
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_QTY) {
            return new CellRenderRawMaterial(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
        }
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }
    
}
