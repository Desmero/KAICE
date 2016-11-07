package fr.kaice.model.buy;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.Converter;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableColumnModel;
import fr.kaice.tools.generic.DTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.*;

import static fr.kaice.tools.generic.DTerminal.*;
import static fr.kaice.tools.local.French.*;

/**
 * This class store all {@link PurchasedProduct} the programme need to know.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 4 columns : <br/>
 * - "{@value fr.kaice.tools.local.French#COL_NAME}", witch display names (editable {@link String});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_UNIT_PRICE}", witch display unitary prices (editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_QUANTITY}", witch display the bought quantities (editable {@link Integer});
 * <br/>
 * - "{@value fr.kaice.tools.local.French#COL_TOTAL_PRICE}", witch display total bought prices (non editable
 * {@link Double}).<br/>
 * The table entries are sorted by names.
 *
 * @author Raphaël Merkling
 * @version 2.1
 * @see PurchasedProduct
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class PurchasedProductCollection extends DTableModel {
    
    private static final int COL_NUM_NAME = 0;
    private static final int COL_NUM_QTY = 2;
    private static final int COL_NUM_RAW_MATERIAL = 4;
    private static final int COL_NUM_RAW_QTY = 5;
    private static final int COL_NUM_TOTAL_PRICE = 3;
    private static final int COL_NUM_UNIT_PRICE = 1;
    private static final DTableColumnModel colName = new DTableColumnModel(COL_NAME, String.class, true);
    private static final DTableColumnModel colQty = new DTableColumnModel(COL_QUANTITY, Integer.class, true);
    private static final DTableColumnModel colTotalPrice = new DTableColumnModel(COL_TOTAL_PRICE, Double.class, false);
    private static final DTableColumnModel colUnitPrice = new DTableColumnModel(COL_UNIT_PRICE, Double.class, true);
    private List<PurchasedProduct> displayList;
    private Map<Integer, PurchasedProduct> map;
    private List<PurchasedProduct> variantList;
    
    /**
     * Construct a {@link PurchasedProductCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public PurchasedProductCollection() {
        colModel = new DTableColumnModel[4];
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_UNIT_PRICE] = colUnitPrice;
        colModel[COL_NUM_QTY] = colQty;
        colModel[COL_NUM_TOTAL_PRICE] = colTotalPrice;
        map = new HashMap<>();
        displayList = new ArrayList<>();
        variantList = new ArrayList<>();
    }
    
    @Override
    public void actionCell(int row, int column) {
        if (!colModel[column].isEditable()) {
            KaiceModel.getInstance().setDetails(displayList.get(row).getDetails());
        }
    }
    
    /**
     * Create and store a new {@link PurchasedProduct}. The Id is auto-generated, and the quantity is set to 0.
     * This send an alert to the model about some data modifications.
     *
     * @param name
     *         {@link String} - The name of the product.
     * @param purchasedPrice
     *         int - The price in cents of the product.
     * @param matId
     *         int - The raw material's id that compose the product.
     * @param quantity
     *         int - The quantity of {@link RawMaterial} contains in the product.
     */
    public void addNewPurchasedProduct(String name, int purchasedPrice, int matId, int quantity) {
        int id = getNewId();
        RawMaterial mat = KaiceModel.getRawMatCollection().getMat(matId);
        PurchasedProduct newProduct = new PurchasedProduct(id, name, purchasedPrice, mat, quantity);
        map.put(id, newProduct);
        serialize();
        updateLists();
        KaiceModel.update(KaiceModel.PURCHASED_PRODUCT);
    }
    
    public void readPurchasedProduct(int id, String name, int purchasedPrice, int matId, int quantity) {
        RawMaterial mat = KaiceModel.getRawMatCollection().getMat(matId);
        PurchasedProduct product = new PurchasedProduct(id, name, purchasedPrice, mat, quantity);
        map.put(id, product);
    }
    
    /**
     * Auto-generate a new free identification number for this collection of
     * {@link PurchasedProduct}.
     *
     * @return A new identification number for a {@link PurchasedProduct}.
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
            FileOutputStream fileOut = new FileOutputStream(KFilesParameters.getPurchasedProductFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Load a serialized historic and deserialize-it. Erase completely the current collection.
     */
    public void deserialize() {
        String fileName = KFilesParameters.getPurchasedProductFile();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (HashMap) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(GREEN + fileName + " read successful." + RESET);
            updateLists();
        } catch (IOException i) {
            System.out.println(RED + fileName + " read error : file not found." + RESET);
            map = new HashMap<>();
        } catch (ClassNotFoundException c) {
            System.out.println(RED + "HashMap<Integer, PurchasedProduct> class not found" + RESET);
            c.printStackTrace();
        }
    }
    
    /**
     * Update the alphabetical sorted list.
     */
    public void updateLists() {
        ArrayList<PurchasedProduct> newDisplayList = new ArrayList<>(map.values());
        newDisplayList.sort((arg0, arg1) -> arg0.getName().compareTo(arg1.getName()));
        for (int i = newDisplayList.size() - 1; i >= 0; i--) {
            if (newDisplayList.get(i).isHidden()) {
                newDisplayList.remove(i);
            }
        }
        if (KaiceModel.getInstance().isShowHidden()) {
            ArrayList<PurchasedProduct> newVariantList = new ArrayList<>(map.values());
            newVariantList.sort((arg0, arg1) -> arg0.getName().compareTo(arg1.getName()));
            variantList = newVariantList;
        } else {
            variantList = newDisplayList;
        }
        displayList = newDisplayList;
    }
    
    /**
     * Return all {@link PurchasedProduct} that contains the specified {@link RawMaterial}.
     *
     * @param material
     *         {@link RawMaterial} - The specified material.
     * @return All {@link PurchasedProduct} that contains the specified {@link RawMaterial}.
     */
    public ArrayList<PurchasedProduct> getContainers(RawMaterial material) {
        ArrayList<PurchasedProduct> list = new ArrayList<>();
        for (PurchasedProduct product : map.values()) {
            if (product.getRawMat() != null && product.getRawMat().equals(material) && !product.isHidden()) {
                list.add(product);
            }
        }
        return list;
    }
    
    /**
     * Return the {@link PurchasedProduct} corresponding to the given id. If the id is not used, return null.
     *
     * @param id
     *         int - The id of the wanted {@link PurchasedProduct}.
     * @return The {@link PurchasedProduct} corresponding to the given id, or null if the id is not used.
     */
    public PurchasedProduct getProd(int id) {
        return map.get(id);
    }
    
    public PurchasedProduct getProductAtRow(int row) {
        return displayList.get(row);
    }
    
    @Override
    public int getRowCount() {
        return displayList.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PurchasedProduct prod = displayList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                return prod.getName();
            case COL_NUM_UNIT_PRICE:
                return DMonetarySpinner.intToDouble(prod.getPrice());
            case COL_NUM_QTY:
                return prod.getNumberBought();
            case COL_NUM_TOTAL_PRICE:
                return DMonetarySpinner.intToDouble(prod.getTotalPrice());
            case COL_NUM_RAW_MATERIAL:
                return prod.getRawMat().getName();
            case COL_NUM_RAW_QTY:
                return prod.getQuantity();
            default:
                return null;
        }
    }
    
    /**
     * Return a variant of the display list. this one can contains hidden products.
     *
     * @return A variant display list of purchased products.
     */
    public List<PurchasedProduct> getVariantList() {
        return variantList;
    }
    
    /**
     * Hide the {@link PurchasedProduct} at a specific row.
     *
     * @param row
     *         int - The row of the material.
     */
    public void hideRow(int row) {
        variantList.get(row).changeHiddenState();
        serialize();
        updateLists();
        KaiceModel.update(KaiceModel.PURCHASED_PRODUCT);
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PurchasedProduct prod = displayList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_NAME:
                prod.setName((String) aValue);
                break;
            case COL_NUM_UNIT_PRICE:
                prod.setPurchasedPrice(DMonetarySpinner.doubleToInt((double) aValue));
                break;
            case COL_NUM_QTY:
                prod.setNumberBought((int) aValue);
                break;
            case COL_NUM_RAW_QTY:
                prod.setQuantity((int) aValue);
                break;
        }
        serialize();
        KaiceModel.update(KaiceModel.PURCHASED_PRODUCT);
    }
    
    /**
     * Valid the bought of {@link PurchasedProduct}. Return a boolean who check the success of the method.
     *
     * @param cost
     *         int - The value paid.
     * @return True if the validation was successful.
     */
    public boolean validBought(int cost, boolean cash) {
        if (cost == getTotalPrice()) {
            int paid = 0;
            if (cash) {
                paid = cost;
            }
            Transaction tran = new Transaction(-1, Transaction.transactionType.BUY, -cost, -paid, new Date());
            for (PurchasedProduct prod :
                    map.values()) {
                if (prod.getNumberBought() > 0) {
                    RawMaterial mat = prod.getRawMat();
                    if (mat != null) {
                        mat.addRestockNum(prod.getNumberBought() * prod.getQuantity());
                        mat.addRestockCost(prod.getTotalPrice());
                    }
                    ArchivedProduct arProd = new ArchivedProduct(prod.getName(), prod.getNumberBought(),
                            prod.getPrice(), prod.getId());
                    tran.addArchivedProduct(arProd);
                }
            }
            KaiceModel.getRawMatCollection().validRestock();
            KaiceModel.getHistoric().addTransaction(tran);
            
            resetBought();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, DIALOG_TEXT_SHOPPING_PRICE, DIALOG_NAME_SHOPPING_PRICE, JOptionPane
                    .WARNING_MESSAGE);
            return false;
        }
    }
    
    /**
     * Return the price in cents of all bought product. This calls {@link PurchasedProduct#getTotalPrice()}.
     *
     * @return The price in cents of all bought product.
     */
    public int getTotalPrice() {
        int price = 0;
        for (PurchasedProduct prod : displayList) {
            price += prod.getTotalPrice();
        }
        return price;
    }
    
    /**
     * Reset for each {@link PurchasedProduct} the number bought to 0.
     */
    public void resetBought() {
        for (PurchasedProduct prod :
                map.values()) {
            prod.setNumberBought(0);
        }
        KaiceModel.update(KaiceModel.PURCHASED_PRODUCT);
    }

    public void saveText() {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (PurchasedProduct product : map.values()) {
            stringBuilder.append(product.getId()).append(';');
            stringBuilder.append(product.getName()).append(';');
            stringBuilder.append(product.getPrice()).append(';');
            stringBuilder.append(product.getRawMat().getId()).append(';');
            stringBuilder.append(product.getQuantity()).append(';');
            stringBuilder.append(product.isHidden()).append('\n');
        }
        Converter.save(KFilesParameters.getPurchasedProductFile() + ".txt", stringBuilder.toString());
    }
}
