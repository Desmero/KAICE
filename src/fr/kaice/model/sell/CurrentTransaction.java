package fr.kaice.model.sell;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.model.member.Member;
import fr.kaice.model.order.Order;
import fr.kaice.model.order.OrderCollection;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This class repertories the {@link SoldProduct} and theirs quantities of the current not ended transaction.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 4 columns : <br/>
 * - "Article", witch display {@link SoldProduct}'s names (non editable {@link String});<br/>
 * - "Prix unitaire", witch display unitary prices (non editable {@link Double});<br/>
 * - "Quantité", witch display the sold quantities (editable {@link Integer});<br/>
 * - "Prix", witch display total sold prices (non editable {@link Double}).<br/>
 * And a summary of all {@link SoldProduct} on the last line.
 *
 * @author Raphaél Merkling
 * @version 2.2
 * @see SoldProduct
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class CurrentTransaction extends DTableModel {
    
    private static final int COL_NUM_ARTICLE = 0;
    private static final int COL_NUM_UNIT_PRICE = 1;
    private static final int COL_NUM_QTY = 2;
    private static final int COL_NUM_PRICE = 3;
    private static final DTableColumnModel colArticle = new DTableColumnModel("Article", String.class, false);
    private static final DTableColumnModel colUnitPrice = new DTableColumnModel("Prix unitaire", Double.class, false);
    private static final DTableColumnModel colQty = new DTableColumnModel("Quantité", Integer.class, true);
    private static final DTableColumnModel colPrice = new DTableColumnModel("Prix", Double.class, false);
    private final HashMap<SoldProduct, Integer> listArticles;
    
    
    /**
     * Construct a {@link CurrentTransaction}. This should be only call one time, and by {@link KaiceModel}.
     */
    public CurrentTransaction() {
        super();
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_ARTICLE] = colArticle;
        colModel[COL_NUM_UNIT_PRICE] = colUnitPrice;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_PRICE] = colPrice;
        totalLine = true;
        listArticles = new HashMap<>();
    }
    
    /**
     * Add a {@link SoldProduct} to the collection with a specified quantity.
     * This send an alert to the model about some data modifications.
     *
     * @param prod     {@link SoldProduct} - The product to add.
     * @param quantity int - The quantity of the product.
     */
    public void addSoldProduct(SoldProduct prod, int quantity) {
        int add = quantity;
        if (listArticles.containsKey(prod)) {
            add += listArticles.get(prod);
        }
        if (prod.getQuantity() != null) {
            if (add > prod.getQuantity()) {
                add = prod.getQuantity();
            }
        }
        listArticles.put(prod, add);
        KaiceModel.update(KaiceModel.TRANSACTION);
    }
    
    /**
     * Remove a {@link SoldProduct} from the collection.
     * This send an alert to the model about some data modifications.
     *
     * @param prod {@link SoldProduct} - The product to remove.
     */
    public void removeSoldProduct(SoldProduct prod) {
        listArticles.remove(prod);
        KaiceModel.update(KaiceModel.TRANSACTION);
    }
    
    /**
     * Valid the {@link CurrentTransaction}.
     * This method decrease te stock, add a line in the historic and reset the current transaction.
     * This send an alert to the model about some data modifications.
     *
     * @param cashIn int - The amount paid in cash.
     */
    public void validTransaction(int cashIn) {
        if (getNumberArticle() == 0) {
            return;
        }
        OrderCollection ordColl = KaiceModel.getOrderCollection();
        Member member = KaiceModel.getMemberCollection().getSelectedMember();
        Transaction tran = new Transaction(member.getMemberId(), transactionType.SELL, getPrice(), cashIn, new Date());
        for (Entry<SoldProduct, Integer> article : listArticles.entrySet()) {
            SoldProduct prod = article.getKey();
            prod.sale(article.getValue());
            ArchivedProduct archProd = prod.archivedProduct(article.getValue());
            tran.addArchivedProduct(archProd);
            Order ord = new Order(member, prod);
            ordColl.addOrder(ord, article.getValue());
        }
        KaiceModel.getHistoric().addTransaction(tran);
        KaiceModel.update(KaiceModel.HISTORIC, KaiceModel.TRANSACTION);
    }
    
    /**
     * Return the total number of article contains on the {@link CurrentTransaction}. This include multiple ones.
     *
     * @return The total number of article in the {@link CurrentTransaction}.
     */
    private int getNumberArticle() {
        int number = 0;
        for (SoldProduct prod : listArticles.keySet()) {
            number += listArticles.get(prod);
        }
        return number;
    }
    
    /**
     * Return the total price of the {@link CurrentTransaction}.
     *
     * @return The total price of the {@link CurrentTransaction}.
     */
    public int getPrice() {
        int price = 0;
        for (SoldProduct prod : listArticles.keySet()) {
            price += prod.getPrice() * listArticles.get(prod);
        }
        return price;
    }
    
    /**
     * Reset the {@link CurrentTransaction}.
     * This send an alert to the model about some data modifications.
     */
    public void reset() {
        listArticles.clear();
        KaiceModel.update(KaiceModel.TRANSACTION);
    }
    
    /**
     * Return all {@link SoldProduct} in a {@link ArrayList}.
     *
     * @return All {@link SoldProduct} in a {@link ArrayList}.
     */
    public ArrayList<SoldProduct> getAllProduct() {
        return new ArrayList<>(listArticles.keySet());
    }
    
    @Override
    public int getRowCount() {
        return listArticles.size() + 1;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == listArticles.size()) {
            switch (columnIndex) {
                case 0:
                    return "Total :";
                case 2:
                    return getNumberArticle();
                case 3:
                    return DMonetarySpinner.intToDouble(getPrice());
                default:
                    return null;
            }
        } else {
            ArrayList<SoldProduct> list = new ArrayList<>(listArticles.keySet());
            SoldProduct prod = list.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return prod.getName();
                case 1:
                    return DMonetarySpinner.intToDouble(prod.getPrice());
                case 2:
                    return listArticles.get(prod);
                case 3:
                    return DMonetarySpinner.intToDouble(prod.getPrice() * listArticles.get(prod));
                default:
                    return null;
            }
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex != listArticles.size() && columnIndex == 2) {
            ArrayList<SoldProduct> list = new ArrayList<>(listArticles.keySet());
            SoldProduct prod = list.get(rowIndex);
            if (prod.getQuantity() != null) {
                if ((int) aValue > prod.getQuantity()) {
                    aValue = prod.getQuantity();
                }
            }
            listArticles.put(prod, (Integer) aValue);
            KaiceModel.update(KaiceModel.TRANSACTION);
        }
    }
    
}
