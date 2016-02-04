package fr.kaice.model.sell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JTable;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.model.sell.SoldProduct.prodType;
import fr.kaice.tools.DTableModel;
import fr.kaice.tools.exeption.AlreadyUsedIdException;

/**
 * This class represent the collection of all {@link SoldProduct}. It is also
 * implements {@link DTableModel}.
 * 
 * @author Raph
 * @version 2.0
 *
 */
public class SoldProductCollection extends DTableModel {

	private Map<Integer, SoldProduct> map;
	private List<SoldProduct> alphabeticList;

	/**
	 * Construct a {@link SoldProductCollection}.
	 */
	public SoldProductCollection() {
		colNames = new String[] { "Id", "Nom", "Prix de vente", "Prix d'achat", "Bénéfice", "Quantité disponible" };
		colClass = new Class[] { Integer.class, String.class, Double.class, Double.class, Double.class, Integer.class };
		colEdit = new Boolean[] { false, true, true, false, false, false };
		map = new HashMap<>();
		alphabeticList = new ArrayList<>();
	}

	/**
	 * Store an existing {@link SoldProduct}.
	 * 
	 * @param prod
	 *            {@link SoldProduct} - The raw material to store in the
	 *            collection.
	 */
	public void addSoldProduct(SoldProduct prod) {
		int id = prod.getId();
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
		}
		map.put(id, prod);
		updateAlphabeticalList();
	}

	/**
	 * Create and store a new {@link SoldProduct}, id auto-generate.
	 * 
	 * @param product
	 *            {@link String} - The name of the new {@link SoldProduct}.
	 * @param price
	 *            int - The price of the new {@link SoldProduct}.
	 * @param type
	 *            {@link prodType} - The type of the new {@link SoldProduct}.
	 */
	public void addNewSoldProduct(String product, int price, prodType type) {
		int id = getNewId();
		SoldProduct newMaterial = new SoldProduct(id, product, price, type);
		map.put(id, newMaterial);
		updateAlphabeticalList();
	}

	/**
	 * Create and store a {@link SoldProduct} read on files.
	 * 
	 * @param id
	 *            int - The identification number of the {@link SoldProduct}.
	 * @param product
	 *            {@link String} - The name of the {@link SoldProduct}.
	 * @param price
	 *            int - The price of the {@link SoldProduct}.
	 * @param type
	 *            {@link prodType} - The type of the {@link SoldProduct}.
	 */
	public void addReadRawMaterial(int id, String product, int price, prodType type) {
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("SoldProduct Id " + id + " is already used.");
		}
		SoldProduct newMaterial = new SoldProduct(id, product, price, type);
		map.put(getNewId(), newMaterial);
		updateAlphabeticalList();
	}

	/**
	 * Auto-generate a new free identification number for this collection of
	 * {@link SoldProduct}.
	 * 
	 * @return A new identification number for a {@link SoldProduct}.
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
	private void updateAlphabeticalList() {
		ArrayList<SoldProduct> newList = new ArrayList<>(map.values());
		newList.sort(new Comparator<SoldProduct>() {
			@Override
			public int compare(SoldProduct arg0, SoldProduct arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});
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
			return alphabeticList.get(rowIndex).getSalePrice();
		case 3:
			return alphabeticList.get(rowIndex).getBuyPrice();
		case 4:
			return alphabeticList.get(rowIndex).getProfit();
		case 5:
			return alphabeticList.get(rowIndex).getQuantity();
		default:
			return null;
		}
	}

}
