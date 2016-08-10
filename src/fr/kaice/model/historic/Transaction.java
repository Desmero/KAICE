package fr.kaice.model.historic;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.generic.DColor;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represent one trade (buy, sell, stock change, ...).<br/><br/>
 * <p>
 * It contains : <br/>
 * - a collection of {@link ArchivedProduct}; <br/>
 * - the client's id; <br/>
 * - the price in cents; <br/>
 * - the cash given; <br/>
 * - the {@link Date}; <br/>
 * - and the type of the transaction ({@link transactionType}). <br/><br/>
 * <p>
 * It extends {@link DTableModel}, a custom {@linkplain javax.swing.table.AbstractTableModel AbstractTableModel}.<br/><br/>
 * In a table, it display the {@link ArchivedProduct} collection with 4 columns : <br/>
 * - "Nom", witch display names (non editable {@link String});<br/>
 * - "Prix unitaire", witch display unitary price (non editable {@link Double});<br/>
 * - "Quantité", witch display the bought quantity (non editable {@link Integer});<br/>
 * - "Prix", witch display the total price (non editable {@link Double}).<br/>
 * And a summary of all {@link ArchivedProduct} on the last line.
 *
 * @author Raphaël Merkling
 * @version 2.2
 */
public class Transaction extends DTableModel implements Serializable {
    
    private static final transient int COL_NUM_ID = 0;
    private static final transient int COL_NUM_NAME = 1;
    private static final transient int COL_NUM_UNIT_PRICE = 2;
    private static final transient int COL_NUM_QTY = 3;
    private static final transient int COL_NUM_PRICE = 4;
    private static final transient int COL_COUNT = 5;
    private static final transient DTableColumnModel colId = new DTableColumnModel("Id", Integer.class, false);
    private static final transient DTableColumnModel colName = new DTableColumnModel("Nom", String.class, false);
    private static final transient DTableColumnModel colQty = new DTableColumnModel("Quantité", Integer.class, false);
    private static final transient DTableColumnModel colUnitPrice = new DTableColumnModel("Prix unitaire", Double.class, false);
    private static final transient DTableColumnModel colPrice = new DTableColumnModel("Prix", Double.class, false);
    private final List<ArchivedProduct> productList;
    private final transactionType type;
    private final Integer clientId;
    private final int price;
    private final int paid;
    private final Date date;
    
    /**
     * Create a new {@link Transaction}.
     *
     * @param clientId
     *          {@link Integer} - The membership number of the concerned client for a sell, canceling or an enrolment.
     *          Null for something else.
     * @param type
     *          {@link transactionType} - The type of transaction.
     * @param price
     *          int - The transaction's price.
     * @param paid
     *          int - The amount paid in cash.
     * @param date
     *          {@link Date} - The date of the transaction.
     */
    public Transaction(Integer clientId, transactionType type, int price, int paid, Date date) {
        this.clientId = clientId;
        this.type = type;
        this.price = price;
        this.paid = paid;
        this.date = date;
        colModel = new DTableColumnModel[COL_COUNT];
        colModel[COL_NUM_ID] = colId;
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_UNIT_PRICE] = colUnitPrice;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_PRICE] = colPrice;
        productList = new ArrayList<>();
    }
    
    /**
     * Add an existing {@link ArchivedProduct} to the collection.
     *
     * @param prod The {@link ArchivedProduct} to add to the collection.
     */
    public void addArchivedProduct(ArchivedProduct prod) {
        productList.add(prod);
    }
    
    /**
     * Return the full name of the client.
     *
     * @return The full name of the client
     */
    public String getClient() {
        if (clientId == 0) {
            return "...";
        }
        return KaiceModel.getMemberCollection().getMember(clientId).getFullName();
    }
    
    /**
     * Return the cash paid for the {@link Transaction}.
     *
     * @return The cash paid for the {@link Transaction}.
     */
    public int getPaid() {
        return paid;
    }
    
    /**
     * Return the {@link Date} of the {@link Transaction}.
     *
     * @return The {@link Date} of the {@link Transaction}.
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Return the {@link transactionType} of the {@link Transaction}.
     *
     * @return The {@link transactionType} of the {@link Transaction}.
     */
    public transactionType getType() {
        return type;
    }
    
    /**
     * Return the {@link Color} to display for the {@link Transaction}.
     * This use the method {@link Transaction#getTypeColor(transactionType)}.
     *
     * @return The {@link Color} to display for the {@link Transaction}.
     */
    public Color getColor() {
        return getTypeColor(type);
    }
    
    /**
     * Return the {@link Color} to display for the {@link transactionType}.
     *
     * @param type
     *          {@link transactionType} - The type of transaction.
     * @return The color for this kind ot transaction.
     */
    private static Color getTypeColor(transactionType type) {
        switch (type) {
            case SELL:
                return DColor.GREEN;
            case CANCEL:
                return DColor.ORANGE;
            case BUY:
                return DColor.BLUE;
            case ADD:
                return DColor.CYAN;
            case SUB:
                return DColor.RED;
            case ENR:
                return DColor.YELLOW;
            default:
                return DColor.GRAY;
        }
    }
    
    @Override
    public int getRowCount() {
        return productList.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == productList.size()) {
            switch (columnIndex) {
                case COL_NUM_NAME:
                    return "Total";
                case COL_NUM_PRICE:
                    return DMonetarySpinner.intToDouble(getPrice());
                default:
                    return null;
            }
        } else {
            ArchivedProduct prod = productList.get(rowIndex);
            
            switch (columnIndex) {
                case COL_NUM_ID:
                    return prod.getId();
                case COL_NUM_NAME:
                    return prod.getName();
                case COL_NUM_QTY:
                    return prod.getQuantity();
                case COL_NUM_UNIT_PRICE:
                    return DMonetarySpinner.intToDouble(prod.getPrice());
                case COL_NUM_PRICE:
                    return DMonetarySpinner.intToDouble(prod.getQuantity() * prod.getPrice());
                default:
                    break;
            }
        }
        return null;
    }
    
    /**
     * Return the price in cents of the {@link Transaction}.
     *
     * @return The price in cents of the {@link Transaction}.
     */
    public int getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArchivedProduct prod : productList) {
            sb.append(prod.getName());
            if (prod.getQuantity() > 1) {
                sb.append(" x").append(prod.getQuantity()).append(";");
            }
        }
        return sb.toString();
    }
    
    /**
     * This enum represent the type of transaction. This type is mostly a graphical distinction. <br/>
     * This enum contains : <br/>
     *  - {@link transactionType#SELL} : for {@linkplain fr.kaice.model.sell.SoldProduct SoldProduct} sold to members; <br/>
     *  - {@link transactionType#BUY} : for {@linkplain fr.kaice.model.buy.PurchasedProduct PurchasedProduct} bought by the association; <br/>
     *  - {@link transactionType#ADD} : for {@linkplain fr.kaice.model.raw.RawMaterial RawMaterial} add to the stock; <br/>
     *  - {@link transactionType#SUB} : for {@linkplain fr.kaice.model.raw.RawMaterial RawMaterial} remove from the stock; <br/>
     *  - {@link transactionType#CANCEL} : for {@linkplain fr.kaice.model.order.Order Order} canceled by members; <br/>
     *  - {@link transactionType#ENR} : for {@linkplain Member Member}s's enrolment.
     */
    public enum transactionType {
        SELL, BUY, ADD, SUB, CANCEL, ENR
    }
    
}
