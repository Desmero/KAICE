package fr.kaice.model.historic;

import java.util.ArrayList;
import java.util.List;

import fr.kaice.tools.DCellRender;
import fr.kaice.tools.DFormat;
import fr.kaice.tools.DMonetarySpinner;
import fr.kaice.tools.DTableModel;

public class Historic extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7463943181952132052L;
	private List<Transaction> orderedList;
	
	public Historic() {
		totalLine = true;
		colNames = new String[] { "Date", "Client", "Transaction", "Prix", "Espece" };
		colClass = new Class[] { String.class, String.class, String.class, Double.class, Double.class };
		colEdit = new Boolean[] { false, false, false, false, false };
		orderedList = new ArrayList<Transaction>();
	}
	
	public Transaction getTransaction(int idrow) {
		return orderedList.get(idrow);
	}
	
	public void addTransaction(Transaction trans) {
		orderedList.add(trans);
		// TODO updateDisplayList pour ne pas tout afficher
	}
	
	@Override
	public int getRowCount() {
		return orderedList.size();
		// TODO faire la ligne de fin
		// TODO ne pas tout afficher
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return DFormat.FULL_DATE_FORMAT.format(orderedList.get(rowIndex).getDate());
		case 1:
			return orderedList.get(rowIndex).getClient();
		case 2:
			return orderedList.get(rowIndex).toString();
		case 3:
			return DMonetarySpinner.intToDouble(orderedList.get(rowIndex).getPrice());
		case 4:
			return DMonetarySpinner.intToDouble(orderedList.get(rowIndex).getPaid());
		default:
			return null;
		}
	}

	@Override
	public DCellRender getColumnModel(int col) {
		if (col ==2) {
			return new CellRenderTransaction(colClass[col], colEdit[col], totalLine);
		}
		return new DCellRender(colClass[col], colEdit[col], totalLine);
	}

}
