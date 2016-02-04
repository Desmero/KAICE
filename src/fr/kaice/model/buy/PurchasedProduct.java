package fr.kaice.model.buy;

import java.util.PrimitiveIterator;

import fr.kaice.model.raw.RawMaterial;
import fr.kaice.tools.KFileParameter;

/**
 * This class represent one kind of purchased product
 * 
 * @author Raph
 * @version 2.0
 *
 */
public class PurchasedProduct {

	private int id;
	private String name;
	private int purchasedPrice;
	private RawMaterial rawMat;
	private int quantity;
	private int numberBought;

	/**
	 * Simple constructor. This use a auto-generate id.
	 * 
	 * @param name
	 *            {@link StringBuilder} - The name of the product.
	 * @param purchasedPrice
	 *            int - The purchase price of the product.
	 * @param rawId
	 *            int - The id of the {@link RawMaterial} that corresponds to
	 *            the product.
	 * @param quantity
	 *            int - The number of the {@link RawMaterial} that contains the
	 *            product.
	 */
	public PurchasedProduct(String name, int purchasedPrice, int rawId, int quantity, String store) {
		// TODO auto-generate id.
		this.id = 0;
		this.name = name;
		this.purchasedPrice = purchasedPrice;
		// TODO getRawMaterial(int id);
		// this.rawMat =
		this.quantity = quantity;
		this.numberBought = 0;
	}

	/**
	 * Full constructor. This should only used for reading items.
	 * 
	 * @param id
	 *            int - Id of the product.
	 * @param name
	 *            {@link StringBuilder} - The name of the product.
	 * @param purchasedPrice
	 *            int - The purchase price of the product.
	 * @param rawId
	 *            int - The id of the {@link RawMaterial} that corresponds to
	 *            the product.
	 * @param quantity
	 *            int - The number of the {@link RawMaterial} that contains the
	 *            product.
	 */
	public PurchasedProduct(int id, String name, int purchasedPrice, int rawId, int quantity, String store) {
		this.id = id;
		this.name = name;
		this.purchasedPrice = purchasedPrice;
		// TODO getRawMaterial(int id);
		// this.rawMat =
		this.quantity = quantity;
		this.numberBought = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(rawMat.getId());
		sb.append(KFileParameter.SEPARATOR);
		sb.append(quantity);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(purchasedPrice);
		sb.append(KFileParameter.SEPARATOR);
		// TODO store
		// sb.append(store);
		// sb.append(FileParameter.SEPARATOR);
		sb.append(name);
		sb.append(KFileParameter.SEPARATOR);
		return sb.toString();
	}

}
