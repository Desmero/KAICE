package fr.kaice.model.buy;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTableModel;

public class PurchasedProductCollectionLight extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PurchasedProductCollectionLight() {
		colNames = new String[] { "Nom", "Prix unitaire"};
		colClass = new Class[] { String.class, Double.class};
		colEdit = new Boolean[] { true, true};
	}

	@Override
	public int getRowCount() {
		return KaiceModel.getPurchasedProdCollection().getRowCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return KaiceModel.getPurchasedProdCollection().getValueAt(rowIndex, columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		KaiceModel.getPurchasedProdCollection().setValueAt(aValue, rowIndex, columnIndex);
	}
	
}
