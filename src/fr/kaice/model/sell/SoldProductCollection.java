package fr.kaice.model.sell;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.tools.Converter;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.tools.cells.CellRenderHiddenProduct;
import fr.kaice.tools.cells.CellRenderSoldProduct;
import fr.kaice.tools.generic.*;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static fr.kaice.tools.generic.DTerminal.*;
import static fr.kaice.tools.local.French.*;

/**
 * This class store all {@link SoldProduct} the programme need to know.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 4 columns : <br/>
 * - "{@value fr.kaice.tools.local.French#COL_NAME}", witch display {@link SoldProduct}'s names (editable
 * {@link String});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_SELL_PRICE}", witch display sell prices (editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_BUY_PRICE}", witch display buy prices (editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_PROFIT}", witch display benefits (editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_QUANTITY}", witch display the available quantities (editable
 * {@link Integer});<br/>
 * The table entries are sorted by names.
 *
 * @author Rapha�l Merkling
 * @version 2.1
 * @see SoldProduct
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class SoldProductCollection extends DTableModel implements IHiddenCollection {

    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_SELL_PRICE = 1;
    private static final int COL_NUM_BUY_PRICE = 2;
    private static final int COL_NUM_PROFIT = 3;
    private static final int COL_NUM_QTY = 4;
    private static final DTableColumnModel colName = new DTableColumnModel(COL_NAME, String.class, true);
    private static final DTableColumnModel colSellPrice = new DTableColumnModel(COL_SELL_PRICE, Double.class, true);
    private static final DTableColumnModel colBuyPrice = new DTableColumnModel(COL_BUY_PRICE, Double.class, false);
    private static final DTableColumnModel colProfit = new DTableColumnModel(COL_PROFIT, Double.class, false);
    private static final DTableColumnModel colQty = new DTableColumnModel(COL_QUANTITY, Integer.class, false);
    private Map<Integer, SoldProduct> map;
    private List<SoldProduct> displayList;

    /**
     * Construct a {@link SoldProductCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public SoldProductCollection() {
        colModel = new DTableColumnModel[5];
        colModel[COL_NUM_PROFIT] = colProfit;
        colModel[COL_NUM_BUY_PRICE] = colBuyPrice;
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_SELL_PRICE] = colSellPrice;
        map = new HashMap<>();
        displayList = new ArrayList<>();
    }

    /**
     * Update the alphabetical sorted list.
     */
    public void updateDisplayList() {
        ArrayList<SoldProduct> newList = new ArrayList<>(map.values());
        if (!KaiceModel.getInstance().isShowHidden()) {
            for (int i = newList.size() - 1; i > 0; i--) {
                if (newList.get(i).isHidden()) {
                    newList.remove(i);
                }
            }
        }
        newList.sort((arg0, arg1) -> arg0.getName().compareTo(arg1.getName()));
        displayList = newList;
    }

    /**
     * Load a serialized historic and deserialize-it. Erase completely the current collection.
     */
    public void deserialize() {
        String fileName = KFilesParameters.getSoldProductFile();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(GREEN + fileName + " read successful." + RESET);
            updateDisplayList();
        } catch (IOException i) {
            System.out.println(RED + fileName + " read error : file not found." + RESET);
            map = new HashMap<>();
        } catch (ClassNotFoundException c) {
            System.out.println(RED + "HashMap<Integer, SoldProduct> class not found" + RESET);
            c.printStackTrace();
        }
    }
    
    public List<SoldProduct> getList() {
        return displayList;
    }
    
    public SoldProduct readSoldProduct(int id, String name, int salePrice, SoldProductCollection.prodType type, boolean
            hidden) {
        SoldProduct product = new SoldProduct(id, name, salePrice, type);
        product.setHidden(hidden);
        map.put(id, product);
        return product;
    }

    /**
     * Hide the {@link SoldProduct} at a specific row.
     *
     * @param row int - The row of the material.
     */
    public void hideRow(int row) {
        displayList.get(row).changeHiddenState();
        serialize();
        updateDisplayList();
        KaiceModel.update(KaiceModel.SOLD_PRODUCT);
    }

    public void removeRow(int row) {
        SoldProduct product = displayList.get(row);
        map.remove(product.getId());
        serialize();
    }
    
    /**
     * Create and store a new {@link SoldProduct}, id auto-generate.
     * This send an alert to the model about some data modifications.
     *
     * @param product {@link String} - The name of the new {@link SoldProduct}.
     * @param price   int - The price of the new {@link SoldProduct}.
     * @param type    {@link prodType} - The type of the new {@link SoldProduct}.
     * @return The new {@link SoldProduct}.
     */
    public SoldProduct addNewSoldProduct(String product, int price, prodType type) {
        int id = getNewId();
        SoldProduct soldProduct = new SoldProduct(id, product, price, type);
        map.put(id, soldProduct);
        serialize();
        updateDisplayList();
        return soldProduct;
    }

    /**
     * Generate a new free identification number for this collection of
     * {@link SoldProduct}.
     *
     * @return A new identification number for a {@link SoldProduct}.
     */
    private int getNewId() {
        int newId = -1;
        for (Integer i : map.keySet()) {
            newId = Integer.max(i, newId);
        }
        return newId + 1;
    }

    /**
     * Serialize the historic, and save-it in a file.
     */
    public void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream(KFilesParameters.getSoldProductFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Return the {@link SoldProduct} corresponding to the given id.
     *
     * @param id int - The id of the product.
     * @return The {@link SoldProduct} corresponding to the given id.
     */
    public SoldProduct getSoldProduct(int id) {
        return map.get(id);
    }

    /**
     * Return the {@link SoldProduct} corresponding to the given row.
     *
     * @param row int - The row of the product.
     * @return The {@link SoldProduct} corresponding to the given row.
     */
    public SoldProduct getSoldProductAtRow(int row) {
        return displayList.get(row);
    }

    /**
     * Return all {@link SoldProduct} of a certain type, with a positive available quantity value, in a {@link
     * ArrayList}.
     *
     * @param type {@link prodType} - The type of the product.
     * @return All available product of the given type in a {@link ArrayList}.
     */
    ArrayList<SoldProduct> getAvailableProduct(prodType type) {
        return displayList.stream().filter(prod -> prod.getType() == type && !prod.isHidden() && prod.isInStock())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Return a red color if the product of a given row is unavailable, null if not.
     *
     * @param row int - The row of a {@link SoldProduct}.
     * @return {@link DColor#RED} if the product is unavailable.
     */
    public Color getRowColor(int row) {
        Integer qty = displayList.get(row).getQuantity();
        if (qty != null && qty == 0) {
            return DColor.RED;
        } else {
            return null;
        }
    }

    @Override
    public void actionCell(int row, int column) {
        if (!colModel[column].isEditable()) {
            KaiceModel.getInstance().setDetails(displayList.get(row).getDetails());
        }
    }

    @Override
    public int getRowCount() {
        return displayList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SoldProduct prod = displayList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                return prod.getName();
            case COL_NUM_SELL_PRICE:
                return DMonetarySpinner.intToDouble(prod.getPrice());
            case COL_NUM_BUY_PRICE:
                return DMonetarySpinner.intToDouble(prod.getBuyPrice());
            case COL_NUM_PROFIT:
                return DMonetarySpinner.intToDouble(prod.getProfit());
            case COL_NUM_QTY:
                return prod.getQuantity();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        SoldProduct prod = displayList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                prod.setName((String) aValue);
                break;
            case COL_NUM_SELL_PRICE:
                WindowAskAdmin.generate(e -> {
                    prod.setSalePrice(DMonetarySpinner.doubleToInt((double) aValue));
                    Transaction tran = new Transaction(-1, Transaction.transactionType.SELL_CHANGE, 0, 0, new Date());
                    // TODO string
                    tran.addArchivedProduct(new ArchivedProduct(prod.getName() + " " + aValue + " " + DFormat.EURO, 0, 0, prod.getId()));
                    KaiceModel.getHistoric().addTransaction(tran);
                    KaiceModel.update(KaiceModel.HISTORIC);
                });
                break;
            default:
                break;
        }
        serialize();
        KaiceModel.update(KaiceModel.SOLD_PRODUCT);
    }

    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_NAME) {
            return new CellRenderHiddenProduct(colModel[col].getColClass(), colModel[col].isEditable(), totalLine, this);
        } else if (col == COL_NUM_QTY) {
            return new CellRenderSoldProduct(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
        }
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }

    @Override
    public boolean isHiddenRow(int row) {
        return displayList.get(row).isHidden();
    }

    /**
     * This define the type of a {@link SoldProduct}. This could be FOOD, DRINK
     * or MISC.
     *
     * @author Raph
     */
    public enum prodType {
        FOOD(TYPE_FOOD, DColor.RED), CANDY(TYPE_CANDY, DColor.YELLOW), DRINK(TYPE_DRINK, DColor.BLUE), MISC
                (TYPE_MISC, DColor.GREEN);

        private final String name;
        private final Color color;
        
        /**
         * Create a new element of the enumeration {@link prodType} with a display name.
         *
         * @param name {@link String} - The display name of the type.
         */
        prodType(String name, Color color) {
            this.color = color;
            this.name = name;
        }
        
        public Color getColor() {
            return color;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }

    public void saveText() {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (SoldProduct product : map.values()) {
            stringBuilder.append(product.getId()).append(';');
            stringBuilder.append(product.getName()).append(';');
            stringBuilder.append(product.getPrice()).append(';');
            stringBuilder.append(product.getType().name()).append(';');
            stringBuilder.append(product.isHidden());
            CompositionAdapter compo = product.getListRawMat();
            for (CompositionAdapter.Element element : compo) {
                stringBuilder.append(';').append(element.getId());
                stringBuilder.append(';').append(element.getQty());
                
            }
            stringBuilder.append('\n');
        }
        Converter.save(KFilesParameters.getSoldProductFile() + ".txt", stringBuilder.toString());
    }
    
}
