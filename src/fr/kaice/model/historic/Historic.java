package fr.kaice.model.historic;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.cells.CellRenderTransaction;
import fr.kaice.tools.generic.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class store all past {@link Transaction}.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 5 columns : <br/>
 * - "Date", witch display dates (non editable {@link Date});<br/>
 * - "Client", witch display clients's name (non editable {@link String});<br/>
 * - "Transaction", witch display the names of all products concerned, the color of the cell depends of the {@link Transaction#type} (non editable {@link Double});<br/>
 * - "Prix", witch display prices (editable {@link Double});<br/>
 * - "Espece", witch display given cash (nne editable {@link Double}).<br/>
 * And a summary of all {@link Transaction} on the last line.
 * The table entries are sorted by dates.
 *
 * @author Raphaël Merkling
 * @version 2.1
 * @see Transaction
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class Historic extends DTableModel {
    
    private static final int COL_NUM_DATE = 0;
    private static final int COL_NUM_CLIENT = 1;
    private static final int COL_NUM_TRAN = 2;
    private static final int COL_NUM_PRICE = 3;
    private static final int COL_NUM_CASH = 4;
    private static DTableColumnModel colDate = new DTableColumnModel("Date", String.class, false);
    private static DTableColumnModel colClient = new DTableColumnModel("Client", String.class, false);
    private static DTableColumnModel colTran = new DTableColumnModel("Transaction", String.class, false);
    private static DTableColumnModel colPrice = new DTableColumnModel("Prix", Double.class, false);
    private static DTableColumnModel colCash = new DTableColumnModel("Espece", Double.class, false);
    private List<Transaction> orderedList;
    private List<Transaction> displayList;
    private Date start;
    private Date end;
    
    /**
     * Construct a {@link Historic}. This should be only call one time, and by {@link KaiceModel}.
     */
    public Historic() {
        totalLine = true;
        colModel = new DTableColumnModel[5];
        colModel[COL_NUM_DATE] = colDate;
        colModel[COL_NUM_CLIENT] = colClient;
        colModel[COL_NUM_TRAN] = colTran;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_CASH] = colCash;
        
        orderedList = new ArrayList<>();
        displayList = new ArrayList<>();
        
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        start = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        end = cal.getTime();
    }
    
    /**
     * Return the {@link Transaction} of a given row.
     *
     * @param idRow int - The row number of the wanted transaction.
     * @return The {@link Transaction} of the given row.
     */
    public Transaction getTransaction(int idRow) {
        return displayList.get(idRow);
    }
    
    /**
     * Add an existing {@link Transaction} to the collection.
     *
     * @param trans {@link Transaction} - The transaction to add to the collection.
     */
    public void addTransaction(Transaction trans) {
        orderedList.add(trans);
        updateDisplayList();
    }
    
    /**
     * Update the display list by checking the date of every {@link Transaction}.
     */
    private void updateDisplayList() {
        displayList.clear();
        Date dTran;
        for (Transaction tran : orderedList) {
            dTran = tran.getDate();
            if (dTran.after(start) && dTran.before(end)) {
                displayList.add(tran);
            }
        }
        KaiceModel.update();
    }
    
    /**
     * Set the period of time to display.
     *
     * @param start {@link Date} - Beginning of the period of time.
     * @param end   {@link Date} - End of the period of time.
     */
    public void setDateSelect(Date start, Date end) {
        this.start = start;
        this.end = end;
        updateDisplayList();
    }
    
    @Override
    public int getRowCount() {
        return displayList.size() + 1;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == displayList.size()) {
            return null;
        }
        switch (columnIndex) {
            case COL_NUM_DATE:
                return DFormat.format(displayList.get(rowIndex).getDate());
            case COL_NUM_CLIENT:
                return displayList.get(rowIndex).getClient();
            case COL_NUM_TRAN:
                return displayList.get(rowIndex).toString();
            case COL_NUM_PRICE:
                return DMonetarySpinner.intToDouble(displayList.get(rowIndex).getPrice());
            case COL_NUM_CASH:
                return DMonetarySpinner.intToDouble(displayList.get(rowIndex).getPaid());
            default:
                return null;
        }
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_TRAN) {
            return new CellRenderTransaction(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
        }
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }
    
}
