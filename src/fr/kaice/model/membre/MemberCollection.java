package fr.kaice.model.membre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.buy.PurchasedProduct;
import fr.kaice.model.buy.PurchasedProductCollection;
import fr.kaice.tools.DTableModel;
import fr.kaice.tools.exeption.AlreadyUsedIdException;

public class MemberCollection extends DTableModel {

	private Map<Integer, Member> map;
	private List<Member> ordredList;

	/**
	 * Construct a {@link MemberCollection}.
	 */
	public MemberCollection() {
		colNames = new String[] { "Id", "Nom", "Prénom" };
		colClass = new Class[] { Integer.class, String.class, String.class };
		colEdit = new Boolean[] { false, false, false };
		map = new HashMap<>();
		ordredList = new ArrayList<>();
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

		for (Member u : ordredList) {
			id = Integer.max(id, u.getUserId());
		}
		return id + 1;
	}
	/**
	 * Update the alphabetical sorted list.
	 */
	private void updateOrderedList() {
		ArrayList<Member> newList = new ArrayList<>(map.values());
		newList.sort(new Comparator<Member>() {
			@Override
			public int compare(Member arg0, Member arg1) {
				return Integer.min(arg0.getUserId(), arg1.getUserId());
			}
		});
		ordredList = newList;
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
			return ordredList.get(rowIndex).getUserId();
		case 1:
			return ordredList.get(rowIndex).getName();
		case 2:
			return ordredList.get(rowIndex).getFirstname();
		default:
			return null;
		}
	}

}
