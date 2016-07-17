package fr.kaice.model.historic;

import fr.kaice.tools.GenericProduct;
import fr.kaice.tools.KFileParameter;

public class ArchivedProduct implements GenericProduct {

	private String name;
	private int quantity;
	private int price;
	
	public ArchivedProduct(String name, int quantity, int price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return -1;
	}
	
	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getSalePrice() {
		return price;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(KFileParameter.SEPARATOR_SEC);
		sb.append(quantity);
		sb.append(KFileParameter.SEPARATOR_SEC);
		sb.append(price);
		return sb.toString();
	}
	
}
