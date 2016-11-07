package fr.kaice.model.sell;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.tools.GenericProduct;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;
import fr.kaice.view.panel.PanelSoldProductDetails;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

import static fr.kaice.model.KaiceModel.RAW_MATERIAL;
import static fr.kaice.model.KaiceModel.getRawMatCollection;
import static fr.kaice.model.sell.SoldProductTableModel.*;

/**
 * This class represent one kind of sold product. <br/>
 * It is composed by : <br/>
 * - An id; <br/>
 * - A name; <br/>
 * - A sale price; <br/>
 * - A type; <br/>
 * - And a collection of {@link RawMaterial}.
 *
 * @author Raphaël Merkling
 * @version 2.1
 */
public class SoldProduct implements GenericProduct, Serializable {

    private static final long serialVersionUID = 1464945659775641259L;
    private int id;
    private SoldProductCollection.prodType type;
    private CompositionAdapter listRawMat;
    private String name;
    private int salePrice;
    private boolean hidden;
    private transient PanelSoldProductDetails details;

    /**
     * SoldProduct constructor.
     *
     * @param id        int - The id of the product.
     * @param name      {@link String} - The name of the product.
     * @param salePrice int - The sale price of the product.
     * @param type      {@link SoldProductCollection.prodType} - The type of the product.
     */
    public SoldProduct(int id, String name, int salePrice, SoldProductCollection.prodType type) {
        this.id = id;
        this.name = name;
        this.salePrice = salePrice;
        this.listRawMat = new CompositionAdapter();
        this.type = type;
    }

    /**
     * Reduce the stock a the {@link RawMaterial} that compose the {@link SoldProduct}.
     *
     * @param number int - The number of the {@link SoldProduct} sold.
     */
    void sale(int number) {
        RawMaterialCollection coll = getRawMatCollection();
        for (CompositionAdapter.Element entry : listRawMat.getAll()) {
            RawMaterial mat = getRawMatCollection().getMat(entry.getId());
            coll.sale(mat, entry.getQty() * number);
        }
    }
    
    public CompositionAdapter getListRawMat() {
        return listRawMat;
    }
    
    /**
     * Create an {@link ArchivedProduct} for the historic.
     *
     * @param number int - The number of the {@link SoldProduct} sold.
     * @return A new {@link ArchivedProduct}.
     */
    ArchivedProduct archivedProduct(int number) {
        return new ArchivedProduct(name, number, salePrice, id);
    }

    /**
     * Set the quantity of {@link RawMaterial} that need this product.
     * The quantity must be positive. If the quantity is equals to 0, the
     * {@link RawMaterial} is remove to collection.
     *
     * @param mat      /!\ {@link RawMaterial} - The raw material.
     * @param quantity int - The quantity, must be positive or equals to 0.
     */
    public void setRawMaterial(RawMaterial mat, int quantity) {
        setRawMaterial(mat.getId(), quantity);
    }

    /**
     * Set the quantity of {@link RawMaterial} that need this product.
     * The quantity must be positive. If the quantity is equals to 0, the
     * {@link RawMaterial} is remove to collection.
     *
     * @param matId    int - The raw material's id.
     * @param quantity int - The quantity, must be positive or equals to 0.
     */
    public void setRawMaterial(int matId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity equals to " + quantity + " must be positive.");
        } else if (quantity == 0) {
            listRawMat.remove(matId);
        } else {
            listRawMat.add(matId, quantity);
        }
        KaiceModel.getSoldProdCollection().serialize();
    }

    /**
     * Set the sale price in cents of the {@link SoldProduct}.
     *
     * @param salePrice int - The new sale price in cents of the {@link SoldProduct}.
     */
    void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }
    
    public void backStock() {
        RawMaterialCollection coll =  KaiceModel.getRawMatCollection();
        for (CompositionAdapter.Element element :
                listRawMat) {
            int matId = element.getId();
            RawMaterial material = coll.getMat(matId);
            material.rebackStock(element.getQty());
        }
        RawMaterialCollection rawColl = KaiceModel.getRawMatCollection();
        // TODO verify this is not useless
        rawColl.updateDisplayList();
        rawColl.serialize();
        KaiceModel.update(RAW_MATERIAL);
    }
    
    /**
     * Return the id of the {@link SoldProduct}.
     *
     * @return The id of the {@link SoldProduct}.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Return the name of the {@link SoldProduct}.
     *
     * @return The name of the {@link SoldProduct}.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of the {@link SoldProduct}.
     *
     * @param name The new name of the {@link SoldProduct}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the sale price in cents of the {@link SoldProduct}.
     *
     * @return The sale price in cents of the {@link SoldProduct}.
     */
    @Override
    public int getPrice() {
        return salePrice;
    }

    /**
     * Return the {@link SoldProductCollection.prodType} of the {@link SoldProduct}.
     *
     * @return The {@link SoldProductCollection.prodType} of the {@link SoldProduct}.
     */
    public SoldProductCollection.prodType getType() {
        return type;
    }

    /**
     * Return the profit in cents of the {@link SoldProduct}. Calculate by subtracting the sell and buy prices.
     *
     * @return The profit in cents of the {@link SoldProduct}.
     */
    public int getProfit() {
        return getPrice() - getBuyPrice();
    }

    /**
     * return the buy price in cents of the {@link SoldProduct}. Calculate with the price of the composing
     * {@link RawMaterial}.
     *
     * @return The buy price in cents of the {@link SoldProduct}.
     */
    public int getBuyPrice() {
        int price = 0;
        for (CompositionAdapter.Element s : listRawMat.getAll()) {
            RawMaterial mat = getRawMatCollection().getMat(s.getId());
            price += s.getQty() * mat.getPrice();
        }
        return price;
    }
    
    void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    public boolean isHidden() {
        return hidden;
    }

    public void changeHiddenState() {
        hidden = !hidden;
    }

    /**
     * Calculate the available quantity of the {@link SoldProduct} with the stock of each composing {@link RawMaterial}.
     * Return null if the quantity is infinite. (equals to {@link Integer#MAX_VALUE})
     *
     * @return The available quantity of the {@link SoldProduct}.
     */
    public Integer getQuantity() {
        int qty = Integer.MAX_VALUE;
        for (CompositionAdapter.Element s : listRawMat.getAll()) {
            RawMaterial mat = getRawMatCollection().getMat(s.getId());
            qty = Integer.min(qty, (mat.getStock() / s.getQty()));
        }
        if (qty == Integer.MAX_VALUE) {
            return null;
        }
        return qty;
    }

    public boolean isInStock() {
        Integer qty = getQuantity();
        if (qty != null && qty <= 0) {
            return false;
        }
        return true;
    }

    public PanelSoldProductDetails getDetails() {
        return this.getDetails(null);
    }

    public PanelSoldProductDetails getDetailsWithoutChange() {
        if (details == null) {
            details = new PanelSoldProductDetails(this);
        }
        return details;
    }

    public PanelSoldProductDetails getDetails(JPanel back) {
        if (details == null) {
            details = new PanelSoldProductDetails(this);
        }
        details.setBackPanel(back);
        return details;
    }

    public int getNumberCompo() {
        return listRawMat.size();
    }
    
    public DTableModel createTableModel() {
        return new SoldProductTableModel(this);
    }
    
    // TODO remove this function
    public void actionCell(int row, int column) {
        //if (!colModel[column].isEditable()) {
        if (true) {
            int id = listRawMat.getAll().get(row).getId();
            KaiceModel.getInstance().setDetails(getRawMatCollection().getMat(id).getDetails(getDetailsWithoutChange()));
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<CompositionAdapter.Element> list = listRawMat.getAll();
        RawMaterial mat = getRawMatCollection().getMat(list.get(rowIndex).getId());
        switch (columnIndex) {
            case COL_NUM_ID:
                return mat.getId();
            case COL_NUM_NAME:
                return mat.getName();
            case COL_NUM_USED:
                return list.get(rowIndex).getQty();
            case COL_NUM_STOCK:
                return mat.getStock();
            case COL_NUM_PRICE:
                return DMonetarySpinner.intToDouble(mat.getPrice());
            default:
                return null;
        }
    }
}
