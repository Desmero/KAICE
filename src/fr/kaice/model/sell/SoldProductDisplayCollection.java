package fr.kaice.model.sell;

import java.util.ArrayList;
import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;

public class SoldProductDisplayCollection extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> collection;
	private SoldProduct.prodType type;
	
	public SoldProductDisplayCollection(SoldProduct.prodType type) {
		this.type = type;
		colNames = new String[] { "Article", "Prix", "Disp." };
		colClass = new Class[] { String.class, Double.class, Integer.class};
		colEdit = new Boolean[] { false, false, false};
		collection = new ArrayList<>();
		updateCollection();
	}

	public void updateCollection() {
		collection = KaiceModel.getSoldProdCollection().getAvilableProduct(type);
	}
	
	public int getSoldProduct(int row) {
		return collection.get(row);
	}
	
	@Override
	public int getRowCount() {
		return collection.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int id = collection.get(rowIndex);
		SoldProduct prod = KaiceModel.getSoldProdCollection().getSoldProduct(id);
		switch (columnIndex) {
		case 0:
			return prod.getName();
		case 1:
			return DMonetarySpinner.intToDouble(prod.getPurchasedPrice());
		case 2:
			return prod.getQuantity();
		}
		return null;
	}

}
