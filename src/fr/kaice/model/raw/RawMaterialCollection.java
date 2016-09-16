package fr.kaice.model.raw;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.IColoredTableModel;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.tools.cells.CellRenderColoredRow;
import fr.kaice.tools.cells.CellRenderHiddenProduct;
import fr.kaice.tools.exeption.AlreadyUsedIdException;
import fr.kaice.tools.generic.*;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.kaice.tools.generic.DTerminal.GREEN;
import static fr.kaice.tools.generic.DTerminal.RED;
import static fr.kaice.tools.generic.DTerminal.RESET;

/**
 * This class store all {@link RawMaterial} the programme need to know.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 4 columns : <br/>
 * - "Nom", witch display {@link RawMaterial}'s names (editable {@link String});<br/>
 * - "Stock", witch display currents stocks (editable {@link Integer});<br/>
 * - "Prix", witch display unit prices (non editable {@link Double}).<br/>
 * - "Alert", witch display alert values(editable {@link Integer}).<br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 2.1
 * @see RawMaterial
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class RawMaterialCollection extends DTableModel implements IHiddenCollection, IColoredTableModel {
    
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_QTY = 1;
    private static final int COL_NUM_PRICE = 2;
    private static final int COL_NUM_ALERT = 3;
    private static final DTableColumnModel colName = new DTableColumnModel("Nom", String.class, true);
    private static final DTableColumnModel colQty = new DTableColumnModel("Stock", Integer.class, true);
    private static final DTableColumnModel colPrice = new DTableColumnModel("Prix", Double.class, false);
    private static final DTableColumnModel colAlert = new DTableColumnModel("Alert", Integer.class, true);

    private Map<Integer, RawMaterial> map;
    private List<RawMaterial> displayList;
    
    /**
     * Construct a {@link RawMaterialCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public RawMaterialCollection() {
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_ALERT] = colAlert;
        map = new HashMap<>();
        displayList = new ArrayList<>();
    }
    
    /**
     * Load a serialized historic and deserialize-it. Erase completely the current collection.
     */
    public void deserialize() {
        try {
            FileInputStream fileIn = new FileInputStream(KFilesParameters.pathRawMaterial);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(GREEN + KFilesParameters.pathRawMaterial + " read successful." + RESET);
            updateDisplayList();
        } catch (IOException i) {
            System.out.println(RED + KFilesParameters.pathRawMaterial + " read error : file not found." + RESET);
            map = new HashMap<>();
        } catch (ClassNotFoundException c) {
            System.out.println(RED + "HashMap<Integer, RawMaterial> class not found" + RESET);
            c.printStackTrace();
        }
    }
    
    /**
     * Update the alphabetical sorted list.
     */
    public void updateDisplayList() {
        ArrayList<RawMaterial> newList = new ArrayList<>(map.values());
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
     * Create and store a new {@link RawMaterial}, id auto-generate and stock,
     * alert and price initialize to 0.
     * This send an alert to the model about some data modifications.
     *
     * @param product {@link String} - The name of the new {@link RawMaterial}.
     */
    public void addNewRawMaterial(String product) {
        int id = getNewId();
        RawMaterial newMaterial = new RawMaterial(id, product);
        addRawMaterial(newMaterial);
    }
    
    /**
     * Generate a new free identification number for this collection of {@link RawMaterial}.
     *
     * @return A new identification number for a {@link RawMaterial}.
     */
    private int getNewId() {
        int newId = -1;
        for (Integer i : map.keySet()) {
            newId = Integer.max(i, newId);
        }
        return newId + 1;
    }
    
    /**
     * Store an existing {@link RawMaterial}.
     * This send an alert to the model about some data modifications.
     *
     * @param mat {@link RawMaterial} - The raw material to store in the collection.
     */
    private void addRawMaterial(RawMaterial mat) {
        int id = mat.getId();
        if (map.containsKey(id)) {
            throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
        }
        map.put(id, mat);
        serialize();
        updateDisplayList();
        KaiceModel.update(KaiceModel.RAW_MATERIAL);
    }
    
    /**
     * Serialize the historic, and save-it in a file.
     */
    public void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream(KFilesParameters.pathRawMaterial);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Use a given amount af a given {@link RawMaterial} by selling a
     * {@linkplain fr.kaice.model.sell.SoldProduct SoldProduct}.
     * This send an alert to the model about some data modifications.
     *
     * @param mat    {@link RawMaterial} - The material used in the product sold.
     * @param number int - The number of the raw material used.
     */
    public void sale(RawMaterial mat, int number) {
        mat.consumption(number);
        serialize();
        KaiceModel.update(KaiceModel.RAW_MATERIAL);
    }
    
    /**
     * Return a {@link HashMap} with in key, the {@link RawMaterial} needed to buy, and in value the quantity.
     * This method use {@link RawMaterial#getNumberToBuy()}.
     *
     * @return A {@link HashMap} contains all {@link RawMaterial} needed to buy, and their quantity.
     */
    public HashMap<RawMaterial, Integer> getShoppingList() {
        HashMap<RawMaterial, Integer> list = new HashMap<>();
        int buyNum;
        for (RawMaterial mat : displayList) {
            buyNum = mat.getNumberToBuy();
            if (buyNum > 0 && !mat.isHidden()) {
                list.put(mat, buyNum);
            }
        }
        return list;
    }
    
    /**
     * Return the {@link Color} of a {@link RawMaterial} choose by a row number, depending of his stock and alert value,
     * using {@link RawMaterial#getColor()}.
     *
     * @param row int - The row of the {@link RawMaterial}.
     * @return The color of the {@link RawMaterial} chosen by the row number.
     */
    @Override
    public Color getRowColor(int row) {
        return displayList.get(row).getColor();
    }
    
    /**
     * Return the {@link RawMaterial} corresponding to the given id.
     *
     * @param id int - The id number of a {@link RawMaterial}
     * @return The {@link RawMaterial} corresponding to the given id.
     */
    public RawMaterial getMat(int id) {
        return map.get(id);
    }
    
    /**
     * Return the {@link RawMaterial} at the given row.
     *
     * @param row int - The row number.
     * @return The {@link RawMaterial} at the given row.
     */
    public RawMaterial getMaterialAtRow(int row) {
        if (row < 0) {
            return null;
        }
        return displayList.get(row);
    }
    
    /**
     * Return an array contening all {@link RawMaterial}.
     *
     * @return An array contening all {@link RawMaterial}.
     */
    public RawMaterial[] getAllRawMaterial() {
        RawMaterial[] tab = new RawMaterial[displayList.size()];
        int i = 0;
        for (RawMaterial mat : displayList) {
            tab[i++] = mat;
        }
        return tab;
    }
    
    /**
     * Hide the {@link RawMaterial} at a specific row.
     *
     * @param row int - The row of the material.
     */
    public void hideRow(int row) {
        displayList.get(row).changeHiddenState();
        serialize();
        updateDisplayList();
        KaiceModel.update(KaiceModel.RAW_MATERIAL);
    }
    
    /**
     * Calculate the new stock and unit price for each {@link RawMaterial}.
     */
    public void validRestock() {
        for (RawMaterial mat :
                map.values()) {
            mat.validRestock();
        }
        serialize();
        KaiceModel.update(KaiceModel.RAW_MATERIAL);
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
        switch (columnIndex) {
            case COL_NUM_NAME:
                return displayList.get(rowIndex).getName();
            case COL_NUM_QTY:
                return displayList.get(rowIndex).getStock();
            case COL_NUM_PRICE:
                return DMonetarySpinner.intToDouble(displayList.get(rowIndex).getPrice());
            case COL_NUM_ALERT:
                return displayList.get(rowIndex).getAlert();
            default:
                return null;
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        RawMaterial mat = displayList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                mat.setName((String) aValue);
                break;
            case COL_NUM_QTY:
                WindowAskAdmin.generate(e -> mat.setStock((int) aValue));
                break;
            case COL_NUM_ALERT:
                mat.setAlert((int) aValue);
                break;
        }
        KaiceModel.update(KaiceModel.RAW_MATERIAL);
        serialize();
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_NAME) {
            return new CellRenderHiddenProduct(colModel[col].getColClass(), colModel[col].isEditable(), totalLine, this);
        } else if (col == COL_NUM_QTY) {
            return new CellRenderColoredRow(colModel[col].getColClass(), colModel[col].isEditable(), totalLine, this);
        }
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }
    
    @Override
    public boolean isHiddenRow(int row) {
        return displayList.get(row).isHidden();
    }
}
