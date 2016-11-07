package fr.kaice.model.buy;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.cells.CellRenderHiddenProduct;
import fr.kaice.tools.generic.*;

import javax.swing.table.AbstractTableModel;

import static fr.kaice.tools.local.French.*;


/**
 * This class is NOT a real collection, just an variant way to display data of {@link PurchasedProductCollection}
 * <br/><br/>
 * <p>
 * In a table, it display 2 columns : <br/>
 * - "{@value fr.kaice.tools.local.French#COL_NAME}", witch display names (editable {@link String});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_UNIT_PRICE}", witch display unitary price (editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_RAW_MATERIAL}", witch display corresponding {@link fr.kaice.model.raw.RawMaterial}
 * (not editable {@link String}); <br/>
 * - "{@value fr.kaice.tools.local.French#COL_QUANTITY}", witch display raw material's quantity(editable {@link Integer}).
 * <br/> The table entries are sorted by names.
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
    private static final DTableColumnModel colName = new DTableColumnModel(COL_ITEM_NAME, String.class, true);
    private static final DTableColumnModel colUnitPrice = new DTableColumnModel(COL_UNIT_PRICE, Double.class, true);
    private static final DTableColumnModel colRawMaterial = new DTableColumnModel(COL_RAW_MATERIAL, String.class, false);
    private static final DTableColumnModel colRawQty = new DTableColumnModel(COL_QUANTITY, Integer.class, true);
    
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
    public void actionCell(int row, int column) {
        if (!colModel[column].isEditable()) {
            KaiceModel.getInstance().setDetails(KaiceModel.getPurchasedProdCollection().getVariantList().get(row).getDetails());
        }
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
                RawMaterial material = prod.getRawMat();
                if (material == null) {
                    return null;
                } else {
                    return material.getName();
                }
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
