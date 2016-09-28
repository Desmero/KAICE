package fr.kaice.model.historic;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.Converter;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.tools.PeriodGetter;
import fr.kaice.tools.cells.CellRenderColoredRow;
import fr.kaice.tools.generic.*;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static fr.kaice.tools.generic.DTerminal.*;
import static fr.kaice.tools.local.French.*;

/**
 * This class store all past {@link Transaction}.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/> In a table, it display 5 columns :
 * <br/>
 * - "{@value fr.kaice.tools.local.French#COL_DATE}", witch display dates (non editable {@link Date});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_CLIENT}", witch display clients's name (non editable {@link String});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_TRANSACTION}", witch display the names of all products concerned, the color of the
 * cell depends of the {@link Transaction#type} (non editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_PRICE}", witch display prices (editable {@link Double});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_CASH}", witch display given cash (non editable {@link Double}).<br/>
 * - "{@value fr.kaice.tools.local.French#COL_CASHIER}", witch display the cashier's name (non editable {@link String})
 * .<br/>
 * And a summary of all {@link Transaction} on the last line. The table entries are sorted by dates.
 *
 * @author Raphaël Merkling
 * @version 2.2
 * @see Transaction
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class Historic extends DTableModel implements PeriodGetter, IColoredTableModel {
    
    private static final int COL_NUM_DATE = 0;
    private static final int COL_NUM_CLIENT = 1;
    private static final int COL_NUM_TRAN = 2;
    private static final int COL_NUM_PRICE = 3;
    private static final int COL_NUM_CASH = 4;
    private static final int COL_NUM_ADMIN = 5;
    private static final DTableColumnModel colDate = new DTableColumnModel(COL_DATE, String.class, false);
    private static final DTableColumnModel colClient = new DTableColumnModel(COL_CLIENT, String.class, false);
    private static final DTableColumnModel colTran = new DTableColumnModel(COL_TRANSACTION, String.class, false);
    private static final DTableColumnModel colPrice = new DTableColumnModel(COL_PRICE, Double.class, false);
    private static final DTableColumnModel colCash = new DTableColumnModel(COL_CASH, Double.class, false);
    private static final DTableColumnModel colAdmin = new DTableColumnModel(COL_CASHIER, String.class, false);
    private final List<Transaction> displayList;
    private final List<Transaction> displayPartialList;
    private List<Transaction> fullList;
    private boolean displayTypeNames;
    private boolean displayAdmin;
    private Date start;
    private Date end;
    
    /**
     * Construct a {@link Historic}. This should be only call one time, and by {@link KaiceModel}.
     */
    public Historic() {
        totalLine = true;
        colModel = new DTableColumnModel[6];
        colModel[COL_NUM_DATE] = colDate;
        colModel[COL_NUM_CLIENT] = colClient;
        colModel[COL_NUM_TRAN] = colTran;
        colModel[COL_NUM_PRICE] = colPrice;
        colModel[COL_NUM_CASH] = colCash;
        colModel[COL_NUM_ADMIN] = colAdmin;
    
        displayTypeNames = false;
        displayAdmin = true;

        fullList = new ArrayList<>();
        displayList = new ArrayList<>();
        displayPartialList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        start = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        end = cal.getTime();
    
        silentUpdateDisplayList();
    }
    
    /**
     * Load a serialized historic and deserialize-it. Erase completely the current collection.
     */
    public void deserialize(int year) {
        String path = KFilesParameters.pathHistoric + year + KFilesParameters.ext;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Transaction> list = (List<Transaction>) in.readObject();
            in.close();
            fileIn.close();
            fullList.addAll(list);
            System.out.println(GREEN + path + " read successful." + RESET);
        } catch (FileNotFoundException f) {
            System.out.println(RED + path + " read error : file not found." + RESET);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println(RED + "List<Transaction> class not found" + RESET);
            c.printStackTrace();
        }
    }
    
    /**
     * Update the display list by checking the date of every {@link Transaction}. But not update the model.
     */
    private void silentUpdateDisplayList() {
        displayList.clear();
        displayPartialList.clear();
        Date dTran;
        for (Transaction tran : fullList) {
            dTran = tran.getDate();
            if (dTran.after(start) && dTran.before(end)) {
                displayList.add(tran);
                displayPartialList.add(tran);
            }
        }
    }
    
    private ArrayList<Transaction> getYearList() {
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.clear(Calendar.MINUTE);
        calStart.clear(Calendar.SECOND);
        if (calStart.get(Calendar.MONTH) < 7) {
            calStart.add(Calendar.YEAR, -1);
        }
        calStart.set(Calendar.MONTH, 7);
        Calendar calEnd = (Calendar) calStart.clone();
        calEnd.add(Calendar.YEAR, 1);
        
        Date start = calStart.getTime(), end = calEnd.getTime();
        
        ArrayList<Transaction> list = fullList.stream().filter(transaction -> transaction.getDate().after(start) &&
                transaction.getDate().before(end)).collect(Collectors.toCollection(ArrayList::new));
        return list;
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
        fullList.add(trans);
        serialize();
        updateDisplayList();
    }
    
    public Transaction readTransaction(Integer clientId, Transaction.transactionType type, int price, int paid, Date
            date, int adminId) {
        Transaction transaction = new Transaction(clientId, type, price, paid, date, adminId);
        fullList.add(transaction);
        return transaction;
    }
    
    /**
     * Serialize the historic, and save-it in a file.
     */
    public void serialize() {
        String path = KFilesParameters.pathHistoric + KaiceModel.getActualYear() + KFilesParameters.ext;
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(getYearList());
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public void serializeAll() {
    
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 7);
        cal.set(Calendar.DAY_OF_MONTH, 30);
    
        System.out.println(DFormat.FULL_DATE_FORMAT.format(cal.getTime()));
    
        Calendar toDay = Calendar.getInstance();
        System.out.println(DFormat.FULL_DATE_FORMAT.format(toDay.getTime()));
    
        System.out.println(toDay.after(cal));
        System.out.println(toDay.before(cal));
        
        while (toDay.after(cal)) {
            String rep = KFilesParameters.pathHistoric + "/" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar
                    .MONTH) + 1);
            String fileName = cal.get(Calendar.DAY_OF_MONTH) + KFilesParameters.ext;
            System.out.println(rep);
            File saveRep = new File(rep);
            if (!saveRep.exists()) {
                saveRep.mkdir();
            }
            try {
                File saveFile = new File(rep + "/" + fileName);
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                FileOutputStream fileOut = new FileOutputStream(rep + "/" + fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(getDayHistoric(cal.getTime()));
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
    }
    
    private ArrayList<Transaction> getDayHistoric(Date date) {
        Calendar calStart = Calendar.getInstance(), calEnd = Calendar.getInstance();
        calStart.setTime(date);
        calStart.set(Calendar.HOUR, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calEnd.setTime(calStart.getTime());
        calEnd.add(Calendar.DAY_OF_YEAR, 1);
        calEnd.add(Calendar.SECOND, -1);
        return new ArrayList<>(fullList.stream().filter(tran -> tran.getDate().after(calStart.getTime()) && tran
                .getDate().before(calEnd.getTime())).collect(Collectors.toList()));
    }
    
    /**
     * Update the display list by checking the date of every {@link Transaction}.
     */
    public void updateDisplayList() {
        displayList.clear();
        displayPartialList.clear();
        Date dTran;
        for (Transaction tran : fullList) {
            dTran = tran.getDate();
            if (dTran.after(start) && dTran.before(end)) {
                if (tran.getType().isDisplay()) {
                    displayList.add(tran);
                }
                displayPartialList.add(tran);
            }
        }
        KaiceModel.update(KaiceModel.HISTORIC);
    }

    public void removeRow(int row) {
        Transaction transaction = displayList.get(row);
        fullList.remove(transaction);
        serialize();
    }
    
    public void changeDisplayAdmin() {
        displayAdmin = !displayAdmin;
    }
    
    private boolean isDisplayAdmin() {
        return displayAdmin;
    }
    
    @Override
    public int getColumnCount() {
        int column = super.getColumnCount();
        if (!isDisplayAdmin()) {
            column--;
        }
        return column;
    }
    
    @Override
    public void actionCell(int row, int column) {
        KaiceModel.getInstance().setDetails(displayList.get(row).getDetails());
    }

    /**
     * Set the period of time to display.
     *
     * @param start {@link Date} - Beginning of the period of time.
     * @param end   {@link Date} - End of the period of time.
     */
    @Override
    public void setPeriod(Date start, Date end) {
        this.start = start;
        this.end = end;
        updateDisplayList();
        KaiceModel.update(KaiceModel.HISTORIC_PERIOD);
    }
    
    public ArrayList<Transaction> getPartialHistoric(PartialHistoric.historicType type, int id) {
        ArrayList<Transaction> newList = new ArrayList<>();
        switch (type) {
            case MEMBER:
                newList.addAll(displayPartialList.stream().filter(tran -> tran.getClientId() == id)
                        .collect(Collectors.toList()));
                break;
            case RAW:
                newList.addAll(displayPartialList.stream().filter(tran ->
                        (tran.getType() == Transaction.transactionType.ADD ||
                                tran.getType() == Transaction.transactionType.SUB) && tran.containsProdId(id))
                        .collect(Collectors.toList()));
                break;
            case BUY:
                newList.addAll(displayPartialList.stream()
                        .filter(tran -> tran.getType() == Transaction.transactionType.BUY && tran.containsProdId(id))
                        .collect(Collectors.toList()));
                break;
            case SOLD:
                newList.addAll(displayPartialList.stream().filter(tran ->
                        (tran.getType() == Transaction.transactionType.SELL ||
                                tran.getType() == Transaction.transactionType.CANCEL) && tran.containsProdId(id))
                        .collect(Collectors.toList()));
                break;
        }
        return newList;
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
                    return TOTAL_LINE;
                case COL_NUM_TRAN:
                    return "" + rowIndex + _TRANSACTION;
                case COL_NUM_PRICE:
                    return DMonetarySpinner.intToDouble(getTotalDisplayPrice());
                case COL_NUM_CASH:
                    return DMonetarySpinner.intToDouble(getTotalDisplayCash());
                default:
                    return null;
            }
        }
        Transaction transaction = displayList.get(rowIndex);
        switch (columnIndex) {
            case COL_NUM_DATE:
                return DFormat.format(transaction.getDate());
            case COL_NUM_CLIENT:
                return transaction.getClient();
            case COL_NUM_TRAN:
                if (isDisplayTypeNames()) {
                    switch (transaction.getType()) {
                        case ADD:
                            return TR_ADD + displayList.get(rowIndex).toString();
                        case SUB:
                            return TR_SUB + displayList.get(rowIndex).toString();
                        case CANCEL:
                            return TR_CANCEL + displayList.get(rowIndex).toString();
                        case SELL:
                            return TR_SELL + displayList.get(rowIndex).toString();
                        case BUY:
                            return TR_BUY + displayList.get(rowIndex).toString();
                        case ENR:
                            return TR_ENR + displayList.get(rowIndex).toString();
                        case MISC:
                            return TR_MISC + displayList.get(rowIndex).toString();
                        default:
                            return displayList.get(rowIndex).toString();
                    }
                } else {
                    return displayList.get(rowIndex).toString();
                }
            case COL_NUM_PRICE:
                return DMonetarySpinner.intToDouble(transaction.getPrice());
            case COL_NUM_CASH:
                return DMonetarySpinner.intToDouble(transaction.getPaid());
            case COL_NUM_ADMIN:
                Member admin = KaiceModel.getMemberCollection().getMember(transaction.getAdminId());
                if (admin != null) {
                    return admin.getFullName();
                }
            default:
                return null;
        }
    }
    
    /**
     * Return the total of the price of all display operations.
     *
     * @return The total of the price of all display operations.
     */
    public int getTotalDisplayPrice() {
        int priceTotal = 0;
        for (Transaction tran :
                displayList) {
            priceTotal += tran.getPrice();
        }
        return priceTotal;
    }
    
    /**
     * Return the total of the cash exchange of all display operations.
     *
     * @return The total of the cash exchange of all display operations.
     */
    public int getTotalDisplayCash() {
        int cashTotal = 0;
        for (Transaction tran :
                displayList) {
            cashTotal += tran.getPaid();
        }
        return cashTotal;
    }
    
    public boolean isDisplayTypeNames() {
        return displayTypeNames;
    }
    
    public void changeDisplayTypeNames() {
        displayTypeNames = !displayTypeNames;
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_TRAN) {
            return new CellRenderColoredRow(colModel[col].getColClass(), colModel[col].isEditable(), totalLine, this);
        }
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }
    
    public void saveText() {
        StringBuilder stringBuilder = new StringBuilder();
    
        for (Transaction transaction : fullList) {
            stringBuilder.append(transaction.getClientId()).append(';');
            stringBuilder.append(transaction.getType()).append(';');
            stringBuilder.append(transaction.getPrice()).append(';');
            stringBuilder.append(transaction.getPaid()).append(';');
            stringBuilder.append(DFormat.FULL_DATE_FORMAT.format(transaction.getDate())).append(';');
            stringBuilder.append(transaction.getAdminId());
            for (ArchivedProduct product : transaction.getProductList()) {
                stringBuilder.append(';').append(product.getName());
                stringBuilder.append(';').append(product.getQuantity());
                stringBuilder.append(';').append(product.getPrice());
                stringBuilder.append(';').append(product.getId());
            }
            stringBuilder.append('\n');
        }
        
        Converter.save(KFilesParameters.pathHistoric + ".ser.txt", stringBuilder.toString());
    }
}
