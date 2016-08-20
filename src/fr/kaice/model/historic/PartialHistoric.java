package fr.kaice.model.historic;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProduct;
import fr.kaice.model.member.Member;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.cells.CellRenderColoredRow;
import fr.kaice.tools.generic.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by merkling on 14/08/16.
 */
public class PartialHistoric extends DTableModel implements IColoredTableModel {
    
    private static final int COL_NUM_DATE = 0;
    private static final int COL_NUM_CLIENT = 1;
    private static final int COL_NUM_TRAN = 2;
    private static final int COL_NUM_PRICE = 3;
    private static final int COL_NUM_CASH = 4;
    private static final DTableColumnModel colDate = new DTableColumnModel("Date", String.class, false);
    private static final DTableColumnModel colClient = new DTableColumnModel("Client", String.class, false);
    private static final DTableColumnModel colTran = new DTableColumnModel("Transaction", String.class, false);
    private static final DTableColumnModel colPrice = new DTableColumnModel("Prix", Double.class, false);
    private static final DTableColumnModel colCash = new DTableColumnModel("Espece", Double.class, false);
    private ArrayList<Transaction> displayList;
    private historicType type;
    private int price;
    private int paid;
    private int id;

    public PartialHistoric(Member member) {
        this(member.getMemberId(), historicType.MEMBER);
    }
    
    private PartialHistoric(int id, historicType type) {
        this.type = type;
        this.id = id;// TODO
        this.displayList = KaiceModel.getHistoric().getPartialHistoric(type, id, new Date(), new Date());
        setPrices();
        totalLine = true;
        colModel = new DTableColumnModel[5];
        colModel[COL_NUM_DATE] = colDate;
        colModel[COL_NUM_CLIENT] = colClient;
        colModel[COL_NUM_TRAN] = colTran;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_CASH] = colCash;
    }
    
    public PartialHistoric(SoldProduct product) {
        this(product.getId(), historicType.SOLD);
    }
    
    public PartialHistoric(PurchasedProduct product) {
        this(product.getId(), historicType.BUY);
    }
    
    public PartialHistoric(RawMaterial material) {
        this(material.getId(), historicType.RAW);
    }
    
    public void update(Date start, Date end) {
        displayList = KaiceModel.getHistoric().getPartialHistoric(type, id, start, end);
    }

    private void setPrices() {
        int price = 0, paid = 0;
        for (Transaction transaction :
                displayList) {
            price += transaction.getPrice();
            paid += transaction.getPaid();
        }
        this.price = price;
        this.paid = paid;
    }

    @Override
    public Color getRowColor(int row) {
        return displayList.get(row).getColor();
    }

    @Override
    public int getRowCount() {
        return displayList.size() + 1;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == displayList.size()) {
            switch (columnIndex) {
                case COL_NUM_CLIENT:
                    return "Total :";
                case COL_NUM_TRAN:
                    return "" + rowIndex + " opérations";
                case COL_NUM_PRICE:
                    return DMonetarySpinner.intToDouble(price);
                case COL_NUM_CASH:
                    return DMonetarySpinner.intToDouble(paid);
                default:
                    return null;
            }
        } else {
            Transaction transaction = displayList.get(rowIndex);
            switch (columnIndex) {
                case COL_NUM_DATE:
                    return DFormat.format(transaction.getDate());
                case COL_NUM_CLIENT:
                    return transaction.getClient();
                case COL_NUM_TRAN:
                    return transaction.toString();
                case COL_NUM_PRICE:
                    return DMonetarySpinner.intToDouble(transaction.getPrice());
                case COL_NUM_CASH:
                    return DMonetarySpinner.intToDouble(transaction.getPaid());
            }
        }
        return null;
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_TRAN) {
            return new CellRenderColoredRow(colModel[col].getColClass(), colModel[col].isEditable(), totalLine, this);
        }
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }
    
    enum historicType {MEMBER, SOLD, BUY, RAW}
}
