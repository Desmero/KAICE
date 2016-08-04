package fr.kaice.model.historic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.cells.CellRenderTransaction;
import fr.kaice.tools.generic.DCellRender;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;

public class Historic extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7463943181952132052L;
	private List<Transaction> orderedList;
	private List<Transaction> displayList;
	private Date start;
	private Date end;
	
	public Historic() {
		totalLine = true;
		colNames = new String[] { "Date", "Client", "Transaction", "Prix", "Espece" };
		colClass = new Class[] { String.class, String.class, String.class, Double.class, Double.class };
		colEdit = new Boolean[] { false, false, false, false, false };
		orderedList = new ArrayList<Transaction>();
		displayList = new ArrayList<Transaction>();
		Calendar cal = Calendar.getInstance();
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		start = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		end = cal.getTime();
	}
	
	public Transaction getTransaction(int idrow) {
		return displayList.get(idrow);
	}
	
	public void addTransaction(Transaction trans) {
		orderedList.add(trans);
		updateDisplayList();
	}
	
	public void setDateSelect(Date start, Date end) {
		this.start = start;
		this.end = end;
		updateDisplayList();
	}
	
	private void updateDisplayList() {
		displayList.clear();
		Date dTran;
		for (Transaction tran : orderedList) {
			dTran = tran.getDate();
			if (dTran.after(start) && dTran.before(end)) {
				displayList.add(tran);
			}
		}
		KaiceModel.update();
	}
	
	@Override
	public int getRowCount() {
		return displayList.size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex == displayList.size()) {
			return null;
		}
		switch (columnIndex) {
		case 0:
			return DFormat.format(displayList.get(rowIndex).getDate());
		case 1:
			return displayList.get(rowIndex).getClient();
		case 2:
			return displayList.get(rowIndex).toString();
		case 3:
			return DMonetarySpinner.intToDouble(displayList.get(rowIndex).getPrice());
		case 4:
			return DMonetarySpinner.intToDouble(displayList.get(rowIndex).getPaid());
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
