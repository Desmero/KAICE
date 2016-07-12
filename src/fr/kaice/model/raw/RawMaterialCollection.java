package fr.kaice.model.raw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.kaice.model.KaiceModel;
import fr.kaice.tools.exeption.AlreadyUsedIdException;
import fr.kaice.tools.generic.DCellRender;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;

/**
 * This class represent the collection of all {@link RawMaterial}. It is also
 * implements {@link DTableModel}.
 * 
 * @author Raph
 * @version 2.0
 *
 */
public class RawMaterialCollection extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7226207712847437213L;
	protected Map<Integer, RawMaterial> map;
	protected List<RawMaterial> alphabeticList;

	/**
	 * Construct a {@link RawMaterialCollection}.
	 */
	public RawMaterialCollection() {
		colNames = new String[] { "Id", "Nom", "Stock", "Prix", "Alert" };
		colClass = new Class[] { Integer.class, String.class, Integer.class, Double.class, Integer.class };
		colEdit = new Boolean[] { false, true, true, false, true };
		map = new HashMap<>();
		alphabeticList = new ArrayList<>();
	}

	/**
	 * Store an existing {@link RawMaterial}.
	 * 
	 * @param mat
	 *            {@link RawMaterial} - The raw material to store in the
	 *            collection.
	 */
	public void addRawMaterial(RawMaterial mat) {
		int id = mat.getId();
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
		}
		map.put(id, mat);
		updateAlphabeticalList();
	}

	/**
	 * Create and store a new {@link RawMaterial}, id auto-generate and stock,
	 * alert and price initialize to 0.
	 * 
	 * @param product
	 *            {@link String} - The name of the new {@link RawMaterial}.
	 */
	public void addNewRawMaterial(String product) {
		int id = getNewId();
		RawMaterial newMaterial = new RawMaterial(id, product);
		addRawMaterial(newMaterial);
		KaiceModel.update();
	}

	/**
	 * Create and store a {@link RawMaterial} read on files.
	 * 
	 * @param id
	 *            int - The identification number of the {@link RawMaterial}.
	 * @param product
	 *            {@link String} - The name of the {@link RawMaterial}.
	 * @param stock
	 *            int - The quantity of the {@link RawMaterial}.
	 * @param price
	 *            int - The price of the {@link RawMaterial}.
	 * @param alert
	 *            int - The quantity alert of the {@link RawMaterial}.
	 */
	public void addReadRawMaterial(int id, String product, int stock, int price, int alert) {
		if (map.containsKey(id)) {
			throw new AlreadyUsedIdException("RawMaterial Id " + id + " is already used.");
		}
		RawMaterial newMaterial = new RawMaterial(id, product, stock, alert, price);
		addRawMaterial(newMaterial);
	}

	/**
	 * Auto-generate a new free identification number for this collection of
	 * {@link RawMaterial}.
	 * 
	 * @return A new identification number for a {@link RawMaterial}.
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
		ArrayList<RawMaterial> newList = new ArrayList<>(map.values());
		newList.sort(new Comparator<RawMaterial>() {
			@Override
			public int compare(RawMaterial arg0, RawMaterial arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});
		alphabeticList = newList;
	}

	public Color getRowColor(int row) {
		return alphabeticList.get(row).getColor();
	}

	public RawMaterial getMat(int id) {
		return map.get(id);
	}

	public RawMaterial getMat(String name) {
		for (RawMaterial mat : alphabeticList) {
			if (mat.getName().equals(name)) {
				return mat;
			}
		}
		return null;
	}

	public RawMaterial[] getAllRawMaterial() {
		RawMaterial[] tab = new RawMaterial[alphabeticList.size()];
		int i = 0;
		for (RawMaterial mat : alphabeticList) {
			tab[i++] = mat;
		}
		return tab;
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
			return alphabeticList.get(rowIndex).getStock();
		case 3:
			return DMonetarySpinner.intToDouble(alphabeticList.get(rowIndex).getPurchasedPrice());
		case 4:
			return alphabeticList.get(rowIndex).getAlert();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		RawMaterial mat = alphabeticList.get(rowIndex);
		switch (columnIndex) {
		case 1:
			mat.setName((String) aValue);
			break;
		case 2:
			mat.setStock((int) aValue);
			break;
		case 4:
			mat.setAlert((int) aValue);
			break;
		}
	}

	@Override
	public DCellRender getColumnModel(int col) {
		if (col == 2) {
			return new CellRenderRawMaterial(colClass[col], colEdit[col], totalLine);
		}
		return new DCellRender(colClass[col], colEdit[col], totalLine);
	}

}
