package fr.kaice.model.historic;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import static fr.kaice.tools.local.French.*;

/**
 * Created by merkling on 18/09/16.
 */
public class TransactionTableModel extends DTableModel{
    
    static final transient int COL_NUM_ID = -1;
    static final transient int COL_NUM_NAME = 0;
    static final transient int COL_NUM_UNIT_PRICE = 1;
    static final transient int COL_NUM_QTY = 2;
    static final transient int COL_NUM_PRICE = 3;
    static final transient int COL_COUNT = 4;
    private static final transient DTableColumnModel colId = new DTableColumnModel(COL_ID, Integer.class, false);
    private static final transient DTableColumnModel colName = new DTableColumnModel(COL_NAME, String.class, false);
    private static final transient DTableColumnModel colQty = new DTableColumnModel(COL_QUANTITY, Integer.class, false);
    private static final transient DTableColumnModel colUnitPrice = new DTableColumnModel(COL_UNIT_PRICE, Double.class, false);
    private static final transient DTableColumnModel colPrice = new DTableColumnModel(COL_PRICE, Double.class, false);
    private final Transaction transaction;
    
    public TransactionTableModel(Transaction transaction) {
        this.transaction = transaction;
        colModel = new DTableColumnModel[COL_COUNT];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_UNIT_PRICE] = colUnitPrice;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_PRICE] = colPrice;
    }
    
    @Override
    public void actionCell(int row, int column) {
        int id = transaction.getProductAtRow(row).getId();
        switch (transaction.getType()) {
            case SELL:
            case CANCEL:
                KaiceModel.getInstance().setDetails(KaiceModel.getSoldProdCollection().getSoldProduct(id).getDetails());
                break;
            case BUY:
                KaiceModel.getInstance().setDetails(KaiceModel.getPurchasedProdCollection().getProd(id).getDetails());
                break;
            case ADD:
            case SUB:
                KaiceModel.getInstance().setDetails(KaiceModel.getRawMatCollection().getMat(id).getDetails());
                break;
            case ENR:
                break;
            default:
        }
    }

    @Override
    public int getRowCount() {
        return transaction.getSize();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return transaction.getValueAt(rowIndex, columnIndex);
    }
}
