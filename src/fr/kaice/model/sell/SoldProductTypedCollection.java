package fr.kaice.model.sell;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

import static fr.kaice.tools.local.French.COL_ARTICLE;
import static fr.kaice.tools.local.French.COL_DISP;
import static fr.kaice.tools.local.French.COL_PRICE;

/**
 * This class repertories all {@link SoldProduct} of one {@linkplain SoldProductCollection.prodType type}.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 3 columns : <br/>
 * - "{@value fr.kaice.tools.local.French#COL_ARTICLE}", witch display {@link SoldProduct}'s names (non editable
 * {@link String});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_PRICE}", witch display total sold prices (non editable {@link Double}).<br/>
 * - "{@value fr.kaice.tools.local.French#COL_DISP}", witch display the available quantities (non editable
 * {@link Integer});<br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 2.1
 * @see SoldProduct
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class SoldProductTypedCollection extends DTableModel {
    
    private static final int COL_NUM_ARTICLE = 0;
    private static final int COL_NUM_PRICE = 1;
    private static final int COL_NUM_QTY = 2;
    private static final DTableColumnModel colArticle = new DTableColumnModel(COL_ARTICLE, String.class, false);
    private static final DTableColumnModel colPrice = new DTableColumnModel(COL_PRICE, Double.class, false);
    private static final DTableColumnModel colQty = new DTableColumnModel(COL_DISP, Integer.class, false);
    private final SoldProductCollection.prodType type;
    private ArrayList<SoldProduct> collection;
    
    /**
     * Construct a {@link SoldProductTypedCollection} of a given type.
     *
     * @param type {@link SoldProductCollection.prodType} - The type of the collection.
     */
    public SoldProductTypedCollection(SoldProductCollection.prodType type) {
        this.type = type;
        colModel = new DTableColumnModel[3];
        colModel[COL_NUM_ARTICLE] = colArticle;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_QTY] = colQty;
        collection = new ArrayList<>();
        updateCollection();
    }
    
    /**
     * Update the collection with only available product.
     */
    public void updateCollection() {
        collection = KaiceModel.getSoldProdCollection().getAvailableProduct(type);
    }
    
    /**
     * Return the {@link SoldProduct} at the given row.
     *
     * @param row int - The row number of the {@link SoldProduct}
     * @return The {@link SoldProduct} at the given row.
     */
    public SoldProduct getSoldProduct(int row) {
        return collection.get(row);
    }
    
    @Override
    public int getRowCount() {
        return collection.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SoldProduct prod = collection.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_ARTICLE:
                return prod.getName();
            case COL_NUM_PRICE:
                return DMonetarySpinner.intToDouble(prod.getPrice());
            case COL_NUM_QTY:
                return prod.getQuantity();
        }
        return null;
    }
}
