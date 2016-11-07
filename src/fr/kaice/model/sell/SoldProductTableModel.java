package fr.kaice.model.sell;

import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import static fr.kaice.tools.local.French.*;

/**
 * Created by merkling on 14/08/16.
 */
class SoldProductTableModel extends DTableModel {
    static final int COL_NUM_ID = -1;
    static final int COL_NUM_NAME = 0;
    static final int COL_NUM_USED = 1;
    static final int COL_NUM_STOCK = 2;
    static final int COL_NUM_PRICE = 3;
    private static final DTableColumnModel colName = new DTableColumnModel(COL_NAME, String.class, false);
    private static final DTableColumnModel colUsed = new DTableColumnModel(COL_USED_QUANTITY, Integer.class, true);
    private static final DTableColumnModel colStock = new DTableColumnModel(COL_STOCK, Integer.class, false);
    private static final DTableColumnModel colPrice = new DTableColumnModel(COL_UNIT_PRICE, Double.class, false);
    
    private final SoldProduct product;
    
    public SoldProductTableModel(SoldProduct product) {
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_USED] = colUsed;
        colModel[COL_NUM_STOCK] = colStock;
        colModel[COL_NUM_PRICE] = colPrice;
        this.product = product;
    }
    
    @Override
    public int getRowCount() {
        return product.getNumberCompo();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return product.getValueAt(rowIndex, columnIndex);
    }
}
