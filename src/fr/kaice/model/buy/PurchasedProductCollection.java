package fr.kaice.model.buy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.kaice.tools.DPriceConvert;
import fr.kaice.tools.DTableModel;
import fr.kaice.tools.exeption.AlreadyUsedIdException;

public class PurchasedProductCollection extends DTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2451097704186666326L;
	private Map<Integer, PurchasedProduct> map;
	private List<PurchasedProduct> alphabeticList;

	/**
	 * Construct a {@link PurchasedProductCollection}.
	 */
	public PurchasedProductCollection() {
		colNames = new String[] { "Id", "Nom", "Prix unitaire", "Quantité", "Prix total" };
		colClass = new Class[] { Integer.class, String.class, Double.class, Integer.class, Double.class };
		colEdit = new Boolean[] { false, true, true, true, false };
		map = new HashMap<>();
		alphabeticList = new ArrayList<>();
	}

	/**
	 * Store an existing {@link PurchasedProduct}.
	 * 
	 * @param pord
	 *            {@link PurchasedProduct} - The raw material to store in the
	 *            collection.
	 */
	public void addPurchasedProduct(PurchasedProduct prod) {
		int id = prod.getId();
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
		}
		map.put(id, prod);
		updateAlphabeticalList();
	}

	/**
	 * Create and store a new {@link PurchasedProduct}, id auto-generate and stock,
	 * alert and price initialize to 0.
	 * 
	 * @param product
	 *            {@link String} - The name of the new {@link PurchasedProduct}.
	 */
	public void addNewPurchasedProduct(String name, int purchasedPrice, int rawId, int quantity) {
		int id = getNewId();
		PurchasedProduct newProduct = new PurchasedProduct(id, name, purchasedPrice, rawId, quantity);
		map.put(id, newProduct);
		updateAlphabeticalList();
	}

	/**
	 * Create and store a {@link PurchasedProduct} read on files.
	 * 
	 * @param id
	 *            int - The identification number of the {@link PurchasedProduct}.
	 * @param product
	 *            {@link String} - The name of the {@link PurchasedProduct}.
	 * @param stock
	 *            int - The quantity of the {@link PurchasedProduct}.
	 * @param price
	 *            int - The price of the {@link PurchasedProduct}.
	 * @param alert
	 *            int - The quantity alert of the {@link PurchasedProduct}.
	 */
	public void addReadPurchasedProduct(int id, String name, int purchasedPrice, int rawId, int quantity) {
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
		}
		PurchasedProduct newProduct = new PurchasedProduct(id, name, purchasedPrice, rawId, quantity);
		map.put(id, newProduct);
	}

	/**
	 * Auto-generate a new free identification number for this collection of
	 * {@link PurchasedProduct}.
	 * 
	 * @return A new identification number for a {@link PurchasedProduct}.
	 */
	public int getNewId() {
		int newId = -1;
		for (Integer i : map.keySet()) {
			newId = Integer.max(i, newId);
		}
		return newId + 1;
	}

	/**
	 * Update the alphabetical sorted list.
	 */
	public void updateAlphabeticalList() {
		ArrayList<PurchasedProduct> newList = new ArrayList<>(map.values());
		newList.sort(new Comparator<PurchasedProduct>() {
			@Override
			public int compare(PurchasedProduct arg0, PurchasedProduct arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});
		alphabeticList = newList;
	}

	public PurchasedProduct getProd(int id) {
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
			return alphabeticList.get(rowIndex).getId();
		case 1:
			return alphabeticList.get(rowIndex).getName();
		case 2:
			return DPriceConvert.intToDouble(alphabeticList.get(rowIndex).getPrice());
		case 3:
			return alphabeticList.get(rowIndex).getNumberBought();
		case 4:
			return DPriceConvert.intToDouble(alphabeticList.get(rowIndex).getTotalPrice());
		default:
			return null;
		}
	}

}
