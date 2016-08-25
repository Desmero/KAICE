package fr.kaice.model.order;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.model.member.Member;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class store all {@link Order} paid but not delivered.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 3 columns : <br/>
 * - "Ordre", witch display numbers only used for ordinate delivery (non editable {@link Integer});<br/>
 * - "Client", witch display {@link Member}'s names (non editable {@link String});<br/>
 * - "Article", witch display {@link SoldProduct}'s names (non editable {@link String}).<br/>
 * The table entries is sorted by chronological creation of orders.
 *
 * @author Raphaël Merkling
 * @version 1.1
 * @see Order
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class OrderCollection extends DTableModel {
    
    private static final int COL_NUM_FIRST_ORDER = 0;
    private static final int COL_NUM_FIRST_MEMBER = 1;
    private static final int COL_NUM_FIRST_PRODUCT = 2;
    private static final DTableColumnModel colOrder = new DTableColumnModel("Ordre", Integer.class, false);
    private static final DTableColumnModel colMember = new DTableColumnModel("Client", String.class, false);
    private static final DTableColumnModel colProduct = new DTableColumnModel("Article", String.class, false);
    private final ArrayList<Order> list;
    
    /**
     * Construct a {@link OrderCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public OrderCollection() {
        colModel = new DTableColumnModel[3];
        colModel[COL_NUM_FIRST_MEMBER] = colMember;
        colModel[COL_NUM_FIRST_ORDER] = colOrder;
        colModel[COL_NUM_FIRST_PRODUCT] = colProduct;
        list = new ArrayList<>();
    }
    
    /**
     * Add an existing {@link Order} several times to the table.
     * This send an alert to the model about some data modifications.
     *
     * @param order  {@link Order} - The order to add into the table.
     * @param number int - The number of copy to add in the table.
     */
    public void addOrder(Order order, int number) {
        for (int i = 0; i < number; i++) {
            list.add(order.clone());
        }
        KaiceModel.update(KaiceModel.ORDER);
    }
    
    /**
     * Cancel an order, and save how much has been refund. This add a new line in the historic.
     * This send an alert to the model about some data modifications.
     *
     * @param row      int - The row number of the order.
     * @param cashBack int - The cash refund in cents.
     */
    public void cancelOrder(int row, int cashBack) {
        Order order = list.get(row);
        list.remove(row);
        SoldProduct prod = order.getProduct();
        prod.restock();
        Transaction tran = new Transaction(order.getMember().getMemberId(), transactionType.CANCEL, -prod.getPrice(),
                -cashBack, new Date());
        ArchivedProduct archProd = new ArchivedProduct(prod.getName(), 1, -prod.getPrice(), prod.getId());
        tran.addArchivedProduct(archProd);
        KaiceModel.getHistoric().addTransaction(tran);
        KaiceModel.update(KaiceModel.ORDER);
    }
    
    /**
     * Valid an order. The product is delivered, so the order can be erase.
     * This send an alert to the model about some data modifications.
     *
     * @param row int - The row of the order.
     */
    public void validOrder(int row) {
        list.remove(row);
        KaiceModel.update(KaiceModel.ORDER);
    }
    
    /**
     * Valid multiple order. All those products are delivered, so the orders cas be erase.
     * This send an alert to the model about some data modifications.
     *
     * @param rows int[] - All the rows of the orders.
     */
    public void validOrders(int[] rows) {
        for (int i = rows.length - 1; i >= 0; i--) {
            System.out.println("2 " + rows[i]);
            list.remove(rows[i]);
        }
        KaiceModel.update(KaiceModel.ORDER);
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order ord = list.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_FIRST_ORDER:
                return rowIndex + 1;
            case COL_NUM_FIRST_MEMBER:
                Member mem = ord.getMember();
                if (mem == null) {
                    return ("...");
                } else {
                    return mem.getFullName();
                }
            case COL_NUM_FIRST_PRODUCT:
                return ord.getProduct().getName();
        }
        return null;
    }
}
