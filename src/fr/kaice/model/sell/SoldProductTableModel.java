package fr.kaice.model.sell;

import fr.kaice.tools.generic.DTableColumnModel;

/**
 * Created by merkling on 14/08/16.
 */
class SoldProductTableModel {
    static final int COL_NUM_ID = -1;
    static final int COL_NUM_NAME = 0;
    static final int COL_NUM_USED = 1;
    static final int COL_NUM_STOCK = 2;
    static final int COL_NUM_PRICE = 3;
    static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, false);
    static final DTableColumnModel colUsed = new DTableColumnModel("Quantité utilisée", Integer.class, true);
    static final DTableColumnModel colStock = new DTableColumnModel("Stock", Integer.class, false);
    static final DTableColumnModel colPrice = new DTableColumnModel("Prix unitaire", Double.class, false);
    
}
