package fr.kaice.model.sell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.tools.cells.CellRenderRawMaterial;
import fr.kaice.tools.generic.DCellRender;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;

public class CompoCollection extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Integer> composition;

	public CompoCollection() {
		super();
		colNames = new String[] { "Id", "Nom", "Prix", "Quantité" };
		colClass = new Class[] { Integer.class, String.class, Double.class, Integer.class };
		colEdit = new Boolean[] { false, false, false, true };
		totalLine = true;
		composition = new HashMap<>();
	}

	public void addRawMaterial(RawMaterial mat) {
		Integer oldNum = composition.get(mat.getId());
		if (oldNum != null) {
			composition.put(mat.getId(), oldNum + 1);
		} else {
			composition.put(mat.getId(), 1);
		}
		KaiceModel.update();
	}

	public void setRawMaterial(RawMaterial mat, int number) {
		setRawMaterial(mat.getId(), number);
	}

	public void setRawMaterial(int id, int number) {
		composition.put(id, number);
	}

	public int getQuentity(int id) {
		return composition.get(id);
	}

	public void removeSelectedRows(int select) {
		ArrayList<Integer> list = new ArrayList<>(composition.keySet());
		composition.remove(list.get(select));
	}

	public int getTotalPrice() {
		int price = 0;
		ArrayList<Integer> list = new ArrayList<>(composition.keySet());
		for (int id : list) {
			RawMaterial mat = KaiceModel.getRawMatCollection().getMat(id);
			price += mat.getSalePrice() * composition.get(id);
		}
		return price;
	}

	public int getNumberCompo() {
		int qttytotal = 0;
		ArrayList<Integer> list = new ArrayList<>(composition.values());
		for (int qtty : list) {
			qttytotal += qtty;
		}
		return qttytotal;
	}

	@Override
	public int getRowCount() {
		return composition.size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<Integer> list = new ArrayList<>(composition.keySet());
		if (rowIndex != composition.size()) {
			int id = list.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return id;
			case 1:
				return KaiceModel.getRawMatCollection().getMat(id).getName();
			case 2:
				int price = KaiceModel.getRawMatCollection().getMat(id).getSalePrice();
				return DMonetarySpinner.intToDouble(price);
			case 3:
				return composition.get(id);
			default:
				return null;
			}
		} else {
			switch (columnIndex) {
			case 0:
				return null;
			case 1:
				return "Total :";
			case 2:
				int price = getTotalPrice();
				return DMonetarySpinner.intToDouble(price);
			case 3:
				return getNumberCompo();
			default:
				return null;
			}
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex != getColumnCount() - 1) {
			ArrayList<Integer> list = new ArrayList<>(composition.keySet());
			int id = list.get(rowIndex);
			composition.put(id, (Integer) aValue);
		}
		KaiceModel.update();
	}

	public RawMaterial[] getAllRawMaterial() {
		RawMaterial[] tab = new RawMaterial[composition.size()];
		int i = 0;
		RawMaterialCollection coll = KaiceModel.getRawMatCollection();
		for (Integer id : composition.keySet()) {
			tab[i++] = coll.getMat(id);
		}
		return tab;
	}

}
