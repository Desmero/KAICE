package fr.kaice.model.sell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.CellRenderRawMaterial;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.tools.DCellRender;
import fr.kaice.tools.DMonetarySpinner;
import fr.kaice.tools.DTableModel;

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
		composition.put(mat.getId(), 1);
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
				int prive = KaiceModel.getRawMatCollection().getMat(id).getPrice();
				return DMonetarySpinner.intToDouble(prive);
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
				return 0;
			case 3:
				return 0;
			default:
				return null;
			}
		}
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
