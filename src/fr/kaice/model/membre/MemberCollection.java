package fr.kaice.model.membre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTableModel;
import fr.kaice.tools.exeption.AlreadyUsedIdException;

public class MemberCollection extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6044679184177698933L;
	private Map<Integer, Member> map;
	private List<Member> orderedList;

	/**
	 * Construct a {@link MemberCollection}.
	 */
	public MemberCollection() {
		colNames = new String[] { "Id", "Nom", "Prénom" };
		colClass = new Class[] { Integer.class, String.class, String.class };
		colEdit = new Boolean[] { false, false, false };
		map = new HashMap<>();
		orderedList = new ArrayList<>();
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
			throw new AlreadyUsedIdException("RawMaterial Id " + id
					+ " is already used.");
		}
		map.put(id, membre);
		updateOrderedList();
	}

	public void addReadMember(Member membre) {
		int id = membre.getUserId();
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id
					+ " is already used.");
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
	public void addReadMember(int userId, String name, String firstname,
			boolean gender, Date birthDate, String phoneNumber, String studies,
			String mailStreet, String mailPostalCode, String mailTown,
			String eMail, boolean newsLetter) {
		if (map.containsKey(userId)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + userId
					+ " is already used.");
		}
		Member newProduct = new Member(userId, name, firstname, gender,
				birthDate, phoneNumber, studies, mailStreet, mailPostalCode,
				mailTown, eMail, newsLetter);
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
	
	/**
	 * Update the sorted list.
	 */
	public void updateOrderedList() {
		ArrayList<Member> newList = new ArrayList<>(map.values());
		newList.sort(new Comparator<Member>() {
			@Override
			public int compare(Member arg0, Member arg1) {
				return arg0.getUserId() - arg1.getUserId();
			}
		});
		orderedList = newList;
	}

	public Member getProd(int id) {
		return map.get(id);
	}

	@Override
	public int getRowCount() {
		return map.size();
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
		Member mem = null;
		for (Member m : orderedList) {
			if (m.getUserId() == id) {
				mem = m;
				break;
			}
		}
		return mem;
	}
	
	public boolean isIdUsed(int id) {
		boolean used = false;
		for (Member m : orderedList) {
			if (m.getUserId() == id) {
				used = true;
				break;
			}
		}
		return used;
	}

	public int getMemberIdAtRow(int selectedRow) {
		return orderedList.get(selectedRow).getUserId();
	}
	
}
