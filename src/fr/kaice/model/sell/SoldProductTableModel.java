package fr.kaice.model.sell;

import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

/**
 * Created by merkling on 14/08/16.
 */
class SoldProductTableModel extends DTableModel {
    static final int COL_NUM_ID = -1;
    static final int COL_NUM_NAME = 0;
    static final int COL_NUM_USED = 1;
    static final int COL_NUM_STOCK = 2;
    static final int COL_NUM_PRICE = 3;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, false);
    private static final DTableColumnModel colUsed = new DTableColumnModel("Quantit� utilis�e", Integer.class, true);
    private static final DTableColumnModel colStock = new DTableColumnModel("Stock", Integer.class, false);
    private static final DTableColumnModel colPrice = new DTableColumnModel("Prix unitaire", Double.class, false);
    
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
