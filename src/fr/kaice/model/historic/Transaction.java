package fr.kaice.model.historic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.membre.Member;
import fr.kaice.tools.DPriceConvert;
import fr.kaice.tools.DTableModel;
import fr.kaice.tools.GenericProduct;

public class Transaction extends DTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ArchivedProduct> productList;

	private static enum transactionType {
		SELL, BUY, ADD, SUB, CANCEL, INS
	};

	private transactionType type;
	private int clientId;
	private int price;
	private int paid;
	private Date date;

	public Transaction(int clientId, transactionType type, int price, int paid, Date date) {
		this.clientId = clientId;
		this.type = type;
		this.price = price;
		this.paid = paid;
		this.date = date;
		colNames = new String[] { "Id", "Nom", "Quantité", "Prix unitaire", "Prix" };
		colClass = new Class[] { Integer.class, String.class, Integer.class, Double.class, Double.class };
		colEdit = new Boolean[] { false, false, false, false, false };
		productList = new ArrayList<ArchivedProduct>();
	}

	public void addArchivedProduct(ArchivedProduct prod) {
		productList.add(prod);
	}
	
	public String getClient() {
		Member m = KaiceModel.getMemberCollection().getMember(clientId);
		if (m == null) {
			return "...";
		}
		return m.getName() + " " + m.getFirstname();
	}
	
	public int getPrice() {
		return price;
	}

	public int getPaid() {
		return paid;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public int getRowCount() {
		return productList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = null;
		if (rowIndex == productList.size()) {
			// TODO faire la ligne du total
		} else {
			ArchivedProduct prod = productList.get(rowIndex);

			switch (columnIndex) {
			case 0:
				return prod.getId();
			case 1:
				return prod.getName();
			case 2:
				return prod.getQuantity();
			case 3:
				return DPriceConvert.intToDouble(prod.getPrice());
			case 4:
				return DPriceConvert.intToDouble(prod.getQuantity() * prod.getPrice());
			default:
				break;
			}
		}
		return obj;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (ArchivedProduct prod : productList) {
			sb.append(prod.getName());
			sb.append(" x");
			sb.append(prod.getQuantity());
			sb.append(";");
		}
		return sb.toString();
	}
	
}
