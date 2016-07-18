package fr.kaice.model.sell;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.model.order.Order;
import fr.kaice.model.order.OrderCollection;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;

public class CurrentTransaction extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Integer> listArticles;

	public CurrentTransaction() {
		super();
		colNames = new String[] { "Article", "Prix unitaire", "Quantité", "Prix" };
		colClass = new Class[] { String.class, Double.class, Integer.class, Double.class };
		colEdit = new Boolean[] { false, false, true, false };
		totalLine = true;
		listArticles = new HashMap<>();
	}

	public void addSoldProduct(int id, int quantity) {
		listArticles.put(id, quantity);
	}

	public void removeSolldProduct(int id) {
		listArticles.remove(id);
	}

	public void validTransaction(int cashIn) {
		OrderCollection ordColl = KaiceModel.getOrderCollection();
		int idMember = KaiceModel.getMemberCollection().getSelectedMember().getUserId();
		Transaction tran = new Transaction(idMember, transactionType.SELL, getPrice(), cashIn, new Date());
		SoldProductCollection prodColl = KaiceModel.getSoldProdCollection();
		for (Entry<Integer, Integer> article : listArticles.entrySet()) {
			int idProd = article.getKey();
			SoldProduct prod = prodColl.getSoldProduct(idProd);
			prod.sale(article.getValue());
			ArchivedProduct archProd = prod.archivedProduct(article.getValue()); 
			tran.addArchivedProduct(archProd);
			Order ord = new Order(idMember, idProd);
			ordColl.addOrder(ord, article.getValue());
		}
		KaiceModel.getHistoric().addTransaction(tran);
		KaiceModel.update();
	}

	public void reset() {
		listArticles.clear();
		KaiceModel.update();
	}

	public int getPrice() {
		int price = 0;
		for (Integer id : listArticles.keySet()) {
			SoldProduct prod = KaiceModel.getSoldProdCollection().getSoldProduct(id);
			price += prod.getPurchasedPrice() * listArticles.get(id);
		}
		return price;
	}

	public int getNumberArticle() {
		int number = 0;
		for (Integer id : listArticles.keySet()) {
			number += listArticles.get(id);
		}
		return number;
	}

	public ArrayList<Integer> getAllProduct() {
		return new ArrayList<Integer>(listArticles.keySet());
	}

	@Override
	public int getRowCount() {
		return listArticles.size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex == listArticles.size()) {
			switch (columnIndex) {
			case 0:
				return "Total :";
			case 2:
				return getNumberArticle();
			case 3:
				return DMonetarySpinner.intToDouble(getPrice());
			default:
				return null;
			}
		} else {
			ArrayList<Integer> list = new ArrayList<>(listArticles.keySet());
			int id = list.get(rowIndex);
			SoldProduct prod = KaiceModel.getSoldProdCollection().getSoldProduct(id);
			switch (columnIndex) {
			case 0:
				return prod.getName();
			case 1:
				return DMonetarySpinner.intToDouble(prod.getPurchasedPrice());
			case 2:
				return listArticles.get(id);
			case 3:
				return DMonetarySpinner.intToDouble(prod.getPurchasedPrice() * listArticles.get(id));
			default:
				return null;
			}
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex != listArticles.size() && columnIndex == 2) {
			ArrayList<Integer> list = new ArrayList<>(listArticles.keySet());
			int id = list.get(rowIndex);
			listArticles.put(id, (Integer) aValue);
			KaiceModel.update();
		}
	}

}
