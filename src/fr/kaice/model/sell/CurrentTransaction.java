package fr.kaice.model.sell;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
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
		// TODO get Member ID
		Transaction tran = new Transaction(0, transactionType.SELL, getPrice(), cashIn, new Date());
		SoldProductCollection prodColl = KaiceModel.getSoldProdCollection();
		for (Entry<Integer, Integer> article : listArticles.entrySet()) {
			int id = article.getKey();
			SoldProduct prod = prodColl.getSoldProduct(id);
			// TODO finirr la fonction
			
		}
	}

	public void reset() {
		listArticles.clear();
		KaiceModel.update();
	}

	public int getPrice() {
		int price = 0;
		for (Integer id : listArticles.keySet()) {
			SoldProduct prod = KaiceModel.getSoldProdCollection().getSoldProduct(id);
			price += prod.getSalePrice() * listArticles.get(id);
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
				return DMonetarySpinner.intToDouble(prod.getSalePrice());
			case 2:
				return listArticles.get(id);
			case 3:
				return DMonetarySpinner.intToDouble(prod.getSalePrice() * listArticles.get(id));
			default:
				return null;
			}
		}
	}

}
