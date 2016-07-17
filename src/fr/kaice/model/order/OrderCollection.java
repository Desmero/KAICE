package fr.kaice.model.order;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.model.membre.Member;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.generic.DTableModel;

public class OrderCollection extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Order> list;

	public OrderCollection() {
		colNames = new String[] { "Ordre", "Client", "Article" };
		colClass = new Class[] { Integer.class, String.class, String.class };
		colEdit = new Boolean[] { false, false, false };
		list = new ArrayList<>();
	}

	public void addOrder(Order ordre) {
		list.add(ordre);
		KaiceModel.update();
	}

	public void removeOrdre(int row, int cashBack) {
		Order order = list.get(row);
		list.remove(row);
		SoldProduct prod = KaiceModel.getSoldProdCollection().getSoldProduct(order.getIdProd());
		Transaction tran = new Transaction(order.getIdMember(), transactionType.CANCEL, -prod.getSalePrice(), -cashBack,
				new Date());
		ArchivedProduct archProd = new ArchivedProduct(prod.getName(), 1, -prod.getSalePrice());
		tran.addArchivedProduct(archProd);
		KaiceModel.getHistoric().addTransaction(tran);
		KaiceModel.update();
	}

	public void validOrdre(int row) {
		list.remove(row);
		KaiceModel.update();
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order ord = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return rowIndex + 1;
		case 1:
			Member mem = KaiceModel.getMemberCollection().getMember(ord.getIdMember());
			if (mem == null) {
				return ("...");
			} else {
				return mem.getFullName();
			}
		case 2:
			return KaiceModel.getSoldProdCollection().getSoldProduct(ord.getIdProd()).getName();
		}
		return null;
	}

}
