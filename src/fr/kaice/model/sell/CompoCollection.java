package fr.kaice.model.sell;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class store the {@link RawMaterial} that compose a {@link SoldProduct}.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 3 columns : <br/>
 * - "Nom", witch display {@link RawMaterial}'s names (non editable {@link String});<br/>
 * - "Prix", witch display unitary price (non editable {@link Double});<br/>
 * - "Quantité", witch display the use quantity (editable {@link Integer});<br/>
 * And a summary of all {@link RawMaterial} on the last line.
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 2.1
 * @see RawMaterial
 * @see SoldProduct
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class CompoCollection extends DTableModel {
    
    private static final int COL_NUM_ID = -1;
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_PRICE = 1;
    private static final int COL_NUM_QTY = 2;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, false);
    private static final DTableColumnModel colPrice = new DTableColumnModel("Prix", Double.class, false);
    private static final DTableColumnModel colQty = new DTableColumnModel("Quantité", Integer.class, true);
    private final HashMap<RawMaterial, Integer> composition;
    
    /**
     * Construct a {@link CompoCollection}.
     */
    public CompoCollection() {
        super();
        colModel = new DTableColumnModel[3];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_QTY] = colQty;
        totalLine = true;
        composition = new HashMap<>();
    }
    
    /**
     * Add a {@link RawMaterial} to the collection and set his quantity to 1.
     * If the {@link RawMaterial} is already present, increase the quantity by 1.
     *
     * @param mat {@link RawMaterial} - The raw material to add.
     */
    public void addRawMaterial(RawMaterial mat) {
        Integer oldNum = composition.get(mat);
        if (oldNum != null) {
            composition.put(mat, oldNum + 1);
        } else {
            composition.put(mat, 1);
        }
        KaiceModel.update(KaiceModel.SOLD_PRODUCT);
    }
    
    /**
     * Return the quantity of {@link RawMaterial} needed.
     *
     * @param mat {@link RawMaterial} - The raw material.
     * @return The quantity of {@link RawMaterial} needed.
     */
    public int getQuantity(RawMaterial mat) {
        return composition.get(mat);
    }
    
    /**
     * Remove the selected {@link RawMaterial} from the collection.
     *
     * @param select int - The row number of the {@link RawMaterial}.
     */
    public void removeSelectedRows(int select) {
        ArrayList<RawMaterial> list = new ArrayList<>(composition.keySet());
        composition.remove(list.get(select));
    }

    public void clear() {
        composition.clear();
    }

    @Override
    public int getRowCount() {
        return composition.size() + 1;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<RawMaterial> list = new ArrayList<>(composition.keySet());
        if (rowIndex != composition.size()) {
            RawMaterial mat = list.get(rowIndex);
            switch (columnIndex) {
                case COL_NUM_ID:
                    return mat.getId();
                case COL_NUM_NAME:
                    return mat.getName();
                case COL_NUM_PRICE:
                    int price = mat.getPrice();
                    return DMonetarySpinner.intToDouble(price);
                case COL_NUM_QTY:
                    return composition.get(mat);
                default:
                    return null;
            }
        } else {
            switch (columnIndex) {
                case COL_NUM_ID:
                    return null;
                case COL_NUM_NAME:
                    return "Total :";
                case COL_NUM_PRICE:
                    int price = getTotalPrice();
                    return DMonetarySpinner.intToDouble(price);
                case COL_NUM_QTY:
                    return getNumberCompo();
                default:
                    return null;
            }
        }
    }
    
    /**
     * Return the total cost in cents of all the {@link RawMaterial}.
     *
     * @return The total cost in cents of all the {@link RawMaterial}.
     */
    private int getTotalPrice() {
        int price = 0;
        ArrayList<RawMaterial> list = new ArrayList<>(composition.keySet());
        for (RawMaterial mat : list) {
            price += mat.getPrice() * composition.get(mat);
        }
        return price;
    }
    
    /**
     * Return the total number of {@link RawMaterial}. This count the duplicate material, not a very useful
     * information.
     *
     * @return The total number of {@link RawMaterial}.
     */
    public int getNumberCompo() {
        int qtyTotal = 0;
        ArrayList<Integer> list = new ArrayList<>(composition.values());
        for (int qty : list) {
            qtyTotal += qty;
        }
        return qtyTotal;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex != getColumnCount() - 1) {
            ArrayList<RawMaterial> list = new ArrayList<>(composition.keySet());
            RawMaterial mat = list.get(rowIndex);
            switch (columnIndex) {
                case COL_NUM_QTY:
                    setRawMaterial(mat, (Integer) aValue);
            }
        }
        KaiceModel.update(KaiceModel.SOLD_PRODUCT);
    }
    
    /**
     * Set the quantity of a {@link RawMaterial}.
     *
     * @param mat    {@link RawMaterial} - The raw material.
     * @param number int - The quantity.
     */
    public void setRawMaterial(RawMaterial mat, int number) {
        composition.put(mat, number);
    }
    
    /**
     * Return an array of all different {@link RawMaterial}.
     *
     * @return An array of all different {@link RawMaterial}.
     */
    public RawMaterial[] getAllRawMaterial() {
        RawMaterial[] tab = new RawMaterial[composition.size()];
        int i = 0;
        for (RawMaterial mat : composition.keySet()) {
            tab[i++] = mat;
        }
        return tab;
    }
    
}
