package fr.kaice.model.historic;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;
import fr.kaice.view.panel.PanelTransaction;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static fr.kaice.model.historic.TransactionTableModel.*;
import static fr.kaice.tools.generic.DColor.*;
import static fr.kaice.tools.local.French.*;

/**
 * This class represent one trade (buy, sell, stock change, ...).<br/><br/>
 * <p>
 * It contains : <br/> - a collection of {@link ArchivedProduct}; <br/> - the client's id; <br/> - the price in cents;
 * <br/> - the cash given; <br/> - the {@link Date}; <br/> - and the type of the transaction ({@link transactionType}).
 * <br/><br/>
 * <p>
 * It extends {@link DTableModel}, a custom {@linkplain javax.swing.table.AbstractTableModel
 * AbstractTableModel}.<br/><br/>
 * In a table, it display the {@link ArchivedProduct} collection with 4 columns : <br/>
 * - "{@value fr.kaice.tools.local.French#COL_NAME}", witch display names (non editable {@link String});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_UNIT_PRICE}", witch display unitary price (non editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_QUANTITY}", witch display the bought quantity (non editable {@link Integer});
 * <br/>
 * - "{@value fr.kaice.tools.local.French#COL_PRICE}", witch display the total price (non editable {@link Double}).<br/>
 * And a summary of all {@link ArchivedProduct} on the last line.
 *
 * @author Raphaël Merkling
 * @version 2.2
 */
public class Transaction implements Serializable {
    
    private static final long serialVersionUID = -8468280991560540628L;
    private final List<ArchivedProduct> productList;
    private final transactionType type;
    private final Integer clientId;
    private final int price;
    private final int paid;
    private final Date date;
    private final int adminId;
    private transient PanelTransaction details;
    
    /**
     * Create a new {@link Transaction}.
     *
     * @param clientId {@link Integer} - The membership number of the concerned client for a sell, canceling or an
     *                 enrolment. Null for something else.
     * @param type     {@link transactionType} - The type of transaction.
     * @param price    int - The transaction's price.
     * @param paid     int - The amount paid in cash.
     * @param date     {@link Date} - The date of the transaction.
     */
    public Transaction(Integer clientId, transactionType type, int price, int paid, Date date) {
        this(clientId, type, price, paid, date, null);
    }
    
    public Transaction(Integer clientId, transactionType type, int price, int paid, Date date, Integer adminId) {
        this.clientId = clientId;
        this.type = type;
        this.price = price;
        this.paid = paid;
        this.date = date;
        if (adminId != null) {
            this.adminId = adminId;
        } else {
            Member member = KaiceModel.getMemberCollection().consumeSelectedAdmin();
            if (member != null) {
                this.adminId = member.getMemberId();
            } else {
                this.adminId = 0;
            }
        }
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
    
    public boolean containsProdId(int id) {
        for (ArchivedProduct prod :
                productList) {
            if (prod.getId() == id) return true;
        }
        return false;
    }
    
    /**
     * Return the full name of the client.
     *
     * @return The full name of the client
     */
    public String getClient() {
        if (clientId == -1) {
            return "CENS";
        }
        Member member = KaiceModel.getMemberCollection().getMember(clientId);
        if (member == null) {
            return "...";
        }
        return member.getFullName();
    }
    
    public Integer getClientId() {
        return clientId;
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
     *
     * @return The {@link Color} to display for the {@link Transaction}.
     */
    public Color getColor() {
        return type.getColor();
    }
    
    public List<ArchivedProduct> getProductList() {
        return productList;
    }
    
    public int getAdminId() {
        return adminId;
    }
    
    public String getName() {
        return type.getTitle();
    }
    
    public PanelTransaction getDetails() {
        if (details == null) {
            details = new PanelTransaction(this);
        }
        return details;
    }
    
    public ArchivedProduct getProductAtRow(int row) {
        return productList.get(row);
    }
    
    public int getSize() {
        return productList.size();
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == productList.size()) {
            switch (columnIndex) {
                case COL_NUM_NAME:
                    return TOTAL_LINE;
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
                sb.append(" x").append(prod.getQuantity());
            }
            sb.append("; ");
        }
        return sb.toString();
    }
    
    /**
     * This enum represent the type of transaction. This type is mostly a graphical distinction. <br/> This enum
     * contains : <br/> - {@link transactionType#SELL} : for {@linkplain fr.kaice.model.sell.SoldProduct SoldProduct}
     * sold to members; <br/> - {@link transactionType#BUY} : for {@linkplain fr.kaice.model.buy.PurchasedProduct
     * PurchasedProduct} bought by the association; <br/> - {@link transactionType#ADD} : for {@linkplain
     * fr.kaice.model.raw.RawMaterial RawMaterial} add to the stock; <br/> - {@link transactionType#SUB} : for
     * {@linkplain fr.kaice.model.raw.RawMaterial RawMaterial} remove from the stock; <br/> - {@link
     * transactionType#CANCEL} : for {@linkplain fr.kaice.model.order.Order Order} canceled by members; <br/> - {@link
     * transactionType#ENR} : for {@linkplain Member Member}s's enrolment.
     */
    public enum transactionType {
        SELL(TR_SELL, GREEN, TR_FULL_NAME_SELL),
        CANCEL(TR_CANCEL, ORANGE, TR_FULL_NAME_CANCEL),
        ADD(TR_ADD, CYAN, TR_FULL_NAME_ADD),
        SUB(TR_SUB, RED, TR_FULL_NAME_SUB),
        BUY(TR_BUY, BLUE, TR_FULL_NAME_BUY),
        SELL_CHANGE(TR_SELL_CHANGE, PINK, TR_FULL_NAME_SELL_CHANGE),
        ENR(TR_ENR, YELLOW, TR_FULL_NAME_ENR),
        CODE_CHANGE(TR_ADMIN_CHANGE, PURPLE, TR_FULL_ADMIN_CHANGE),
        DEPOSIT(TR_DEPOSIT, WHITE, TR_FULL_DEPOSIT),
        MISC(TR_MISC, GRAY, TR_FULL_NAME_MISC);
        
        private boolean display;
        private final Color color;
        private final String title;
        private final String fullName;
        
        transactionType(String title, Color color, String fullName) {
            this.title = title;
            this.color = color;
            this.fullName = fullName;
            this.display = true;
        }
        
        public Color getColor() {
            return color;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String getFullName() {
            return fullName;
        }
        
        public boolean isDisplay() {
            return display;
        }
        
        public void changeDisplay() {
            display = !display;
        }
    }
    
}
