package fr.kaice.model.membre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.exeption.AlreadyUsedIdException;
import fr.kaice.tools.generic.DTableModel;

public class MemberCollection extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6044679184177698933L;
	private Map<Integer, Member> map;
	private List<Member> orderedList;
	private Member selectedMember;
	private int sortCol;
	private String searchName;
	private String searchFirstname;

	public static final int ID = 0;
	public static final int NAME = 1;
	public static final int FIRSTNAME = 2;

	/**
	 * Construct a {@link MemberCollection}.
	 */
	public MemberCollection() {
		colNames = new String[] { "Id", "Nom", "Prénom" };
		colClass = new Class[] { Integer.class, String.class, String.class };
		colEdit = new Boolean[] { false, false, false };
		map = new HashMap<>();
		orderedList = new ArrayList<>();
		selectedMember = null;
		sortCol = 0;
		searchName = "";
		searchFirstname = "";
	}

	/**
	 * Store an existing {@link Member}.
	 * 
	 * @param pord
	 *            {@link Member} - The raw material to store in the collection.
	 */
	public void addMember(Member membre) {
		int id = membre.getUserId();
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
		}
		map.put(id, membre);
		updateOrderedList();
	}

	public void addReadMember(Member membre) {
		int id = membre.getUserId();
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
		}
		map.put(id, membre);
	}

	/**
	 * Create and store a {@link Member}.
	 * 
	 * @param userId
	 * @param name
	 * @param firstname
	 * @param gender
	 * @param birthDate
	 * @param phoneNumber
	 * @param studies
	 * @param mailStreet
	 * @param mailPostalCode
	 * @param mailTown
	 * @param eMail
	 * @param newsLetter
	 */
	public void addReadMember(int userId, String name, String firstname, boolean gender, Date birthDate,
			String phoneNumber, String studies, String mailStreet, String mailPostalCode, String mailTown, String eMail,
			boolean newsLetter) {
		if (map.containsKey(userId)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + userId + " is already used.");
		}
		Member newProduct = new Member(userId, name, firstname, gender, birthDate, phoneNumber, studies, mailStreet,
				mailPostalCode, mailTown, eMail, newsLetter);
		map.put(userId, newProduct);
		updateOrderedList();
	}

	/**
	 * Auto-generate a new free identification number for this collection of
	 * {@link Member}.
	 * 
	 * @return A new identification number for a {@link Member}.
	 */
	public int getNewId() {
		int id = 1;
		int add = KaiceModel.getActualYear();
		add *= 10000;
		id += add;

		for (Member u : orderedList) {
			id = Integer.max(id, u.getUserId());
		}
		return id + 1;
	}

	public void setSortColl(String sortColName) {
		for (int i = 0; i < colNames.length; i++) {
			if (colNames[i].equals(sortColName)) {
				sortCol = i;
				break;
			}
		}
		updateOrderedList();
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
		updateOrderedList();
	}

	public void setSearchFirstname(String searchFirstname) {
		this.searchFirstname = searchFirstname;
		updateOrderedList();
	}

	/**
	 * Update the sorted list.
	 */
	public void updateOrderedList() {
		orderedList = getOrderedList(searchName, searchFirstname, sortCol);
		KaiceModel.update();
	}

	private ArrayList<Member> getOrderedList(String name, String firstname, int select) {
		ArrayList<Member> newList = new ArrayList<>();
		for (Member mem : map.values()) {
			if (mem.getName().toLowerCase().contains(name.toLowerCase())
					&& mem.getFirstname().toLowerCase().contains(firstname.toLowerCase())) {
				newList.add(mem);
			}
		}
		newList.sort(new Comparator<Member>() {
			@Override
			public int compare(Member arg0, Member arg1) {
				switch (sortCol) {
				case NAME:
					return arg0.getName().compareTo(arg1.getName());
				case FIRSTNAME:
					return arg0.getFirstname().compareTo(arg1.getFirstname());
				case ID:
				default:
					return arg0.getUserId() - arg1.getUserId();
				}
			}
		});
		return newList;
	}
	
	public String[] getPartialArray(String name, String firstname, int select) {
		ArrayList<Member> newList = getOrderedList(name, firstname, select);
		String[] newArray = new String[newList.size()];
		for (int i = 0; i < newList.size(); i++) {
			if (select == FIRSTNAME) {
				newArray[i] = newList.get(i).getFirstname();
			} else {
			newArray[i] = newList.get(i).getName();
			}
		}
		return newArray;
	}

	public Member getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(Member mem) {
		selectedMember = mem;
	}

	public void setSelectedMember(int row) {
		selectedMember = getRow(row);
		KaiceModel.update();
	}

	public void setSelectedMemberById(int id) {
		selectedMember = map.get(id);
		KaiceModel.update();
	}

	public void setSelectedMemberByName(String name, String firstname) {
		ArrayList<Member> list = getOrderedList(name, firstname, ID);
		if (list.size() == 1) {
			selectedMember = list.get(0);
			KaiceModel.update();
		} else {
			selectedMember = null;
		}
	}

	public Member getRow(int row) {
		return orderedList.get(row);
	}

	public Member getProd(int id) {
		return map.get(id);
	}

	@Override
	public int getRowCount() {
		return orderedList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return orderedList.get(rowIndex).getUserId();
		case 1:
			return orderedList.get(rowIndex).getName();
		case 2:
			return orderedList.get(rowIndex).getFirstname();
		default:
			return null;
		}
	}

	public String getEMailList() {
		StringBuilder sb = new StringBuilder();
		for (Member u : orderedList) {
			if (u.isNewsLetter() && u.isValidEmailAddress()) {
				String eMail = u.getEMail();
				sb.append(eMail + "\n");
			}
		}
		return sb.toString();
	}

	public Member getMember(int id) {
		/*
		 * Member mem = null; for (Member m : orderedList) { if (m.getUserId()
		 * == id) { mem = m; break; } }
		 */
		return map.get(id);
	}

	public boolean isIdUsed(int id) {
		/*
		 * boolean used = false; for (Member m : orderedList) { if
		 * (m.getUserId() == id) { used = true; break; } }
		 */
		return map.containsKey(id);
	}

	public int getMemberIdAtRow(int selectedRow) {
		return orderedList.get(selectedRow).getUserId();
	}

}
