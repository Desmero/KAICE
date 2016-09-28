package fr.kaice.model.member;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.IColoredTableModel;
import fr.kaice.tools.Converter;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.tools.cells.CellRenderColoredRow;
import fr.kaice.tools.exeption.AlreadyUsedIdException;
import fr.kaice.tools.generic.*;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.kaice.tools.generic.DTerminal.*;
import static fr.kaice.tools.local.French.COL_FIRST_NAME;
import static fr.kaice.tools.local.French.COL_NAME;
import static fr.kaice.tools.local.French.COL_NUM;

/**
 * This class store all {@link Member} of the current year.
 * This should be construct only by {@link KaiceModel}, and one time.
 * It extends {@link DTableModel}, a custom {@link AbstractTableModel}.<br/><br/>
 * In a table, it display 3 columns : <br/>
 * - "{@value fr.kaice.tools.local.French#COL_NUM}", witch display membership numbers (non editable {@link Integer});
 * <br/>
 * - "{@value fr.kaice.tools.local.French#COL_NAME}", witch display names (non editable {@link String});<br/>
 * - "{@value fr.kaice.tools.local.French#COL_FIRST_NAME}", witch display first names(non editable {@link String}).<br/>
 * The table entries can ne sorted by dates, names or firs names.
 *
 * @author Raphaël Merkling
 * @version 2.2
 * @see Member
 * @see DTableModel
 * @see AbstractTableModel
 * @see KaiceModel
 */
public class MemberCollection extends DTableModel implements IColoredTableModel {
    
    private static final int COL_NUM_ID = 0;
    private static final int COL_NUM_NAME = 1;
    private static final int COL_NUM_FIRST_NAME = 2;
    private static final DTableColumnModel colId = new DTableColumnModel(COL_NUM, Integer.class, false);
    private static final DTableColumnModel colName = new DTableColumnModel(COL_NAME, String.class, false);
    private static final DTableColumnModel colFirstName = new DTableColumnModel(COL_FIRST_NAME, String.class, false);
    private Map<Integer, Member> map;
    private List<Member> displayList;
    private Member selectedMember;
    private Member selectedAdmin;
    private int sortCol;
    private String searchName;
    private String searchFirstName;
    private int displayYear;
    
    /**
     * Construct a {@link MemberCollection}. This should be only call one time, and by {@link KaiceModel}.
     */
    public MemberCollection() {
        colModel = new DTableColumnModel[3];
        colModel[COL_NUM_ID] = colId;
        colModel[COL_NUM_NAME] = colName;
        colModel[COL_NUM_FIRST_NAME] = colFirstName;
        map = new HashMap<>();
        selectedMember = null;
        sortCol = 0;
        searchName = "";
        searchFirstName = "";
        displayYear = KaiceModel.getActualYear();
        silentUpdateDisplayList();
    }
    
    /**
     * Load a serialized members collection and deserialize-it. Erase completely the current collection.
     */
    public void deserialize(int yearCode) {
        String filePath = KFilesParameters.getMemberFile(yearCode);
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Member> list = (ArrayList<Member>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(GREEN + filePath + " read successful." + RESET);
            for (Member member : list) {
                map.put(member.getMemberId(), member);
            }
            silentUpdateDisplayList();
        } catch (IOException i) {
            System.out.println(RED + filePath + " read error : file not found." + RESET);
        } catch (ClassNotFoundException c) {
            System.out.println(RED + "ArrayList<Member> class not found" + RESET);
            c.printStackTrace();
        }
    }
    
    /**
     * Update the display list with a new generated one (generated with
     * {@link MemberCollection#getDisplayList(String, String, int, int)}). But not update the model.
     */
    public void silentUpdateDisplayList() {
        displayList = getDisplayList(searchName, searchFirstName, sortCol, displayYear);
    }
    
    public void setDisplayYear(int year) {
        this.displayYear = year;
        updateDisplayList();
        if (getYearList(year).size() == 0) {
            deserialize(year);
            updateDisplayList();
        }
    }
    
    /**
     * Generate a new display list, with a name filter, a first name filter and a sort method (by membership number,
     * name or first name)
     *
     * @param name      {@link String} - The name filter sub-string.
     * @param firstName {@link String} - The first name filter sub-string.
     * @param sotMethod int - the sort method ({@value COL_NUM_ID} for id, {@value COL_NUM_NAME} for name, {@value
     *                  COL_NUM_FIRST_NAME} for first name).
     * @return A new list of {@link Member}, corresponding to the filter, and sort parameters.
     */
    private ArrayList<Member> getDisplayList(String name, String firstName, int sotMethod, int yearCode) {
        ArrayList<Member> newList = map.values().stream().filter(mem ->
                mem.getName().toLowerCase().contains(name.toLowerCase())
                        && mem.getFirstName().toLowerCase().contains(firstName.toLowerCase())
                        && mem.getMemberId()/10000 == yearCode)
                .collect(Collectors.toCollection(ArrayList::new));
        newList.sort((arg0, arg1) -> {
            switch (sotMethod) {
                case COL_NUM_NAME:
                    return arg0.getName().toLowerCase().compareTo(arg1.getName().toLowerCase());
                case COL_NUM_FIRST_NAME:
                    return arg0.getFirstName().toLowerCase().compareTo(arg1.getFirstName().toLowerCase());
                case COL_NUM_ID:
                default:
                    return arg0.getMemberId() - arg1.getMemberId();
            }
        });
        return newList;
    }
    
    /**
     * Store an existing {@link Member}.
     *
     * @param member {@link Member} - The raw material to store in the collection.
     */
    public void addMember(Member member) {
        int id = member.getMemberId();
        if (map.containsKey(id)) {
            throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
        }
        map.put(id, member);
        serialize();
        updateDisplayList();
    }
    
    public void addReadMember(Member member) {
        int id = member.getMemberId();
        if (map.containsKey(id)) {
            throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
        }
        map.put(id, member);
    }
    
    private ArrayList<Member> getYearList(int yearCode) {
        ArrayList<Member> list = new ArrayList<>();
        for (Member member : map.values()) {
            if (member.getMemberId() >= yearCode * 10000 && member.getMemberId() < (yearCode + 1) * 10000) {
                list.add(member);
            }
        }
        return list;
    }
    
    /**
     * Serialize the members collection, and save-it in a file.
     */
    public void serialize() {
        serialize(KaiceModel.getActualYear());
    }
        /**
         * Serialize the members collection, and save-it in a file.
         */
        public void serialize(int yearCode) {
        try {
            FileOutputStream fileOut = new FileOutputStream(KFilesParameters.getMemberFile(yearCode));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(getYearList(yearCode));
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Update the display list with a new generated one (generated with
     * {@link MemberCollection#getDisplayList(String, String, int, int)}).
     * This send an alert to the model about some data modifications.
     */
    public void updateDisplayList() {
        displayList = getDisplayList(searchName, searchFirstName, sortCol, displayYear);
        KaiceModel.update(KaiceModel.MEMBER);
    }
    
    /**
     * Auto-generate a new free membership number for this collection of {@link Member}.
     * A membership number is composed of 6 digits, the firsts 2 are thr current year code given by
     * {@link KaiceModel#getActualYear()}, the others 4 are equals to the higher used number corresponding of this year,
     * plus 1.
     *
     * @return A new membership number for a {@link Member}.
     */
    public int getNewId() {
        int id = 0;
        int add = KaiceModel.getActualYear();
        add *= 10000;
        id += add;
        
        for (Member u : getYearList(KaiceModel.getActualYear())) {
            id = Integer.max(id, u.getMemberId());
        }
        return id + 1;
    }
    
    /**
     * Sort the display list of {@link MemberCollection} following the column given in parameter.
     *
     * @param sortColName {@link String} - The name of the column to sort.
     */
    public void setSortColl(String sortColName) {
        for (int i = 0; i < colModel.length; i++) {
            if (colModel[i].getName().equals(sortColName)) {
                sortCol = i;
                break;
            }
        }
        updateDisplayList();
    }
    
    /**
     * Set the filter by name.
     * Update the display list in order to only keep {@link Member}'s names who contains the searchName sub-string.
     *
     * @param searchName {@link String} - A sub-string filter for name.
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
        updateDisplayList();
    }
    
    /**
     * Set the filter by first name. Update the display list in order to only keep {@link Member}'s first names who
     * contains the searchName sub-string.
     *
     * @param searchFirstName {@link String} - A sub-string filter for first name.
     */
    public void setSearchFirstName(String searchFirstName) {
        this.searchFirstName = searchFirstName;
        updateDisplayList();
    }
    
    /**
     * Return the selected {@link Member} for the current transaction.
     *
     * @return The selected {@link Member}.
     */
    public Member getSelectedMember() {
        return selectedMember;
    }
    
    /**
     * Set the selected {@link Member} for the current transaction.
     *
     * @param row int - The row of the new selected {@link Member}.
     */
    public void setSelectedMember(int row) {
        if (row >= 0) {
            selectedMember = getRow(row);
            KaiceModel.update(KaiceModel.TRANSACTION);
        }
    }
    
    public Member getSelectedAdmin() {
        return selectedAdmin;
    }
    
    public Member consumeSelectedAdmin() {
        Member admin = selectedAdmin;
        selectedAdmin = null;
        return admin;
    }
    
    /**
     * Return the {@link Member} at the given row.
     *
     * @param row int - The number of the row.
     * @return The {@link Member} at the given row.
     */
    private Member getRow(int row) {
        return displayList.get(row);
    }
    
    /**
     *
     */
    public void clearSelectedMember() {
        selectedMember = null;
    }
    
    /**
     * Set the selected {@link Member} for the current transaction.
     *
     * @param id int - The membership number of the new selected {@link Member}.
     */
    public void setSelectedMemberById(int id) {
        selectedMember = map.get(id);
        KaiceModel.update(KaiceModel.TRANSACTION);
    }
    
    /**
     * Set the selected {@link Member} for the current transaction.
     *
     * @param name      {@link String} The name of the new selected {@link Member}.
     * @param firstName {@link String} The first name of the new selected {@link Member}.
     */
    public void setSelectedMemberByName(String name, String firstName) {
        ArrayList<Member> list = getDisplayList(name, firstName, COL_NUM_ID, displayYear);
        if (list.size() == 1) {
            selectedMember = list.get(0);
            KaiceModel.update(KaiceModel.TRANSACTION);
        } else {
            selectedMember = null;
        }
    }
    
    public void setSelectedAdminById(int id) {
        Member member = map.get(id);
        if (member != null && member.isAdmin()){
            selectedAdmin = map.get(id);
        } else {
            selectedAdmin = null;
        }
        KaiceModel.update(KaiceModel.ADMIN);
    }
    
    public boolean isAdminSelected() {
        return selectedAdmin != null;
    }
    
    
    public boolean isAdminSet() {
        for (Member member : map.values()) {
            if (member.isAdmin()) {
                return true;
            }
        }
        return false;
    }
    
    public Member getMemberByName(String name, String firstName) {
        ArrayList<Member> list = getDisplayList(name, firstName, COL_NUM_ID, displayYear);
        Member member = null;
        if (list.size() == 1) {
            member = list.get(0);
        } else if (list.size() == 0) {
            member = new Member(0);
        }
        return member;
    }
    
    @Override
    public int getRowCount() {
        return displayList.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COL_NUM_ID:
                return displayList.get(rowIndex).getMemberId();
            case COL_NUM_NAME:
                return displayList.get(rowIndex).getName().toUpperCase();
            case COL_NUM_FIRST_NAME:
                String firstName = displayList.get(rowIndex).getFirstName().toLowerCase();
                return firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            default:
                return null;
        }
    }
    
    @Override
    public DCellRender getColumnModel(int col) {
        if (col == COL_NUM_ID) {
            return new CellRenderColoredRow(colModel[col].getColClass(), colModel[col].isEditable(), totalLine, this);
        }
        return super.getColumnModel(col);
    }
    
    @Override
    public Color getRowColor(int row) {
        if (KaiceModel.editor && displayList.get(row).isAdmin()) {
            return DColor.BLUE;
        }
        if (!displayList.get(row).isAdult()) {
            return DColor.RED;
        }
        return null;
    }
    
    /**
     * Create one {@link String} with the correct e-mail address of every {@link Member} who subscribe to the
     * newsletter. Each address is separated by a semicolon ';'.
     *
     * @return A {@link String} containing e-mail address.
     */
    public String getEMailList(boolean newsLetterOnly) {
        StringBuilder sb = new StringBuilder();
        map.values().stream().filter(u -> (!newsLetterOnly || u.isNewsLetter()) && u.isValidEmailAddress())
        .forEachOrdered(u -> {
            String eMail = u.getEMail();
            sb.append(eMail).append("\n");
        });
        return sb.toString();
    }
    
    /**
     * Return the {@link Member} with the corresponding membership number.
     *
     * @param id int - The membership number.
     * @return The {@link Member} with the corresponding membership number.
     */
    public Member getMember(int id) {
        return map.get(id);
    }
    
    /**
     * Return true if the given membership number is already used.
     *
     * @param id int - The membership number.
     * @return True if the given membership number is already used.
     */
    public boolean isIdUsed(int id) {
        return map.containsKey(id);
    }
    
    /**
     * Return the {@link Member} at the corresponding row.
     *
     * @param selectedRow int - The row's number.
     * @return The {@link Member} at the corresponding row.
     */
    public int getMemberIdAtRow(int selectedRow) {
        return displayList.get(selectedRow).getMemberId();
    }
    
    public void saveText(int yearCode) {
        StringBuilder stringBuilder = new StringBuilder();
    
        for (Member member : getYearList(yearCode)) {
            stringBuilder.append(member.getMemberId()).append(';');
            stringBuilder.append(member.getName()).append(';');
            stringBuilder.append(member.getFirstName()).append(';');
            stringBuilder.append(member.isMale()).append(';');
            stringBuilder.append(DFormat.DATE_ONLY.format(member.getBirthDate())).append(';');
            stringBuilder.append(member.getPhoneNumber()).append(';');
            stringBuilder.append(member.getStudies()).append(';');
            stringBuilder.append(member.getMailStreet()).append(';');
            stringBuilder.append(member.getMailPostalCode()).append(';');
            stringBuilder.append(member.getMailTown()).append(';');
            stringBuilder.append(member.getEMail()).append(';');
            stringBuilder.append(member.isNewsLetter()).append(';');
            stringBuilder.append(member.isAdmin()).append('\n');
        }
        Converter.save(KFilesParameters.getMemberFile(yearCode) + ".txt", stringBuilder.toString());
    }
    
}