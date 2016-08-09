package fr.kaice.model.buy;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.table.AbstractTableModel;


/**
 * This class is NOT a real collection, just a light copy of the TableModel part of
 * {@link PurchasedProductCollection} <br/><br/>
 * <p>
 * In a table, it display 2 columns : <br/>
 * - "Nom", witch display names (editable {@link String});<br/>
 * - "Prix unitaire", witch display unitary price (editable {@link Double});<br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see AbstractTableModel;
 * @see DTableModel
 * @see PurchasedProductCollection
 */
public class PurchasedProductCollectionLight extends DTableModel {
    
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_UNIT_PRICE = 1;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, true);
    private static final DTableColumnModel colUnitPrice = new DTableColumnModel("Prix unitaire", Double.class, true);
    
    /**
     * Construct a {@link PurchasedProductCollectionLight}.
     */
    public PurchasedProductCollectionLight() {
        colModel = new DTableColumnModel[2];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_UNIT_PRICE] = colUnitPrice;
    }
    
    @Override
    public int getRowCount() {
        return KaiceModel.getPurchasedProdCollection().getRowCount();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO trouver une méthode plus adaptative
        return KaiceModel.getPurchasedProdCollection().getValueAt(rowIndex, columnIndex);
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        KaiceModel.getPurchasedProdCollection().setValueAt(aValue, rowIndex, columnIndex);
    }
    
}
