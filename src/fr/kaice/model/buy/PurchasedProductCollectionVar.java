package fr.kaice.model.buy;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.cells.CellRenderHiddenProduct;
import fr.kaice.tools.generic.*;

import javax.swing.table.AbstractTableModel;


/**
 * This class is NOT a real collection, just an variant way to display data of
 * {@link PurchasedProductCollection} <br/><br/>
 * <p>
 * In a table, it display 2 columns : <br/>
 * - "Nom", witch display names (editable {@link String});<br/>
 * - "Prix unitaire", witch display unitary price (editable {@link Double});<br/>
 * - "Produit brute", witch display corresponding {@link fr.kaice.model.raw.RawMaterial} (not editable {@link String}); <br/>
 * - "Quantité", witch display raw material's quantity(editable {@link Integer}). <br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see AbstractTableModel;
 * @see DTableModel
 * @see PurchasedProductCollection
 */
public class PurchasedProductCollectionVar extends DTableModel implements IHiddenCollection {
    
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_UNIT_PRICE = 1;
    private static final int COL_NUM_RAW_MATERIAL = 2;
    private static final int COL_NUM_RAW_QTY = 3;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, true);
    private static final DTableColumnModel colUnitPrice = new DTableColumnModel("Prix unitaire", Double.class, true);
    private static final DTableColumnModel colRawMaterial = new DTableColumnModel("Produit brute", String.class, false);
    private static final DTableColumnModel colRawQty = new DTableColumnModel("Quantité", Integer.class, true);
    
    /**
     * Construct a {@link PurchasedProductCollectionVar}.
     */
    public PurchasedProductCollectionVar() {
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_UNIT_PRICE] = colUnitPrice;
        colModel[COL_NUM_RAW_QTY] = colRawQty;
        colModel[COL_NUM_RAW_MATERIAL] = colRawMaterial;
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_NAME) {
            return new CellRenderHiddenProduct(colName.getColClass(), colName.isEditable(), totalLine, this);
        }
        return super.getColumnModel(col);
    }
    
    @Override
    public int getRowCount() {
        return KaiceModel.getPurchasedProdCollection().getVariantList().size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PurchasedProduct prod = KaiceModel.getPurchasedProdCollection().getVariantList().get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                return prod.getName();
            case COL_NUM_UNIT_PRICE:
                return DMonetarySpinner.intToDouble(prod.getPrice());
            case COL_NUM_RAW_MATERIAL:
                return prod.getRawMat().getName();
            case COL_NUM_RAW_QTY:
                return prod.getQuantity();
            default:
                return null;
        }
    }
    
    @Override
    public boolean isHiddenRow(int row) {
        return KaiceModel.getPurchasedProdCollection().getVariantList().get(row).isHidden();
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PurchasedProduct prod = KaiceModel.getPurchasedProdCollection().getVariantList().get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                prod.setName((String) aValue);
                break;
            case COL_NUM_UNIT_PRICE:
                prod.setPurchasedPrice(DMonetarySpinner.doubleToInt((double) aValue));
                break;
            case COL_NUM_RAW_QTY:
                prod.setQuantity((int) aValue);
                break;
        }
        KaiceModel.getPurchasedProdCollection().serialize();
        KaiceModel.update(KaiceModel.PURCHASED_PRODUCT);
    }
    
}
