package fr.kaice.model.raw;

import java.awt.Color;

import fr.kaice.tools.GenericProduct;
import fr.kaice.tools.KFileParameter;
import fr.kaice.tools.generic.DColor;

/**
 * This class represent one kind of basic item.
 * 
 * @author Raph
 * @version 2.0
 * 
 */
public class RawMaterial implements GenericProduct {

	private final int id;
	private String name;
	private int stock;
	private int unitPrice;
	private int alert;
	private int restockNum; // Used for average cost calculation
	private int restockCost; // Used for average cost calculation

	/**
	 * Simple constructor. Take only the name, auto-generate the id, and
	 * initialize everything else to 0.
	 * 
	 * @param name
	 *            {@link String} - The name of the material.
	 * 
	 * @see String
	 */
	public RawMaterial(int id, String name) {
		this.id = id;
		this.name = name;
		this.stock = 0;
		this.unitPrice = 0;
		this.alert = 0;
		this.restockNum = 0;
		this.restockCost = 0;
	}

	/**
	 * Full constructor. This should only used for reading items.
	 * 
	 * @param id
	 *            int - The id of this material.
	 * @param name
	 *            {@link String} - The name of the material.
	 * @param quantity
	 *            int - The current amount of this material.
	 * @param alert
	 *            int - The amount above witch the color turn to orange, and
	 *            determine the amount to bye for the shopping list.
	 * @param unitPrice
	 *            int - The last unit price calculate.
	 * 
	 * @see String
	 */
	public RawMaterial(int id, String name, int quantity, int alert, int unitPrice) {
		this.id = id;
		this.name = name;
		this.stock = quantity;
		this.unitPrice = unitPrice;
		this.alert = alert;
		this.restockNum = 0;
		this.restockCost = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getAlert() {
		return alert;
	}

	public void setAlert(int alert) {
		this.alert = alert;
	}

	public int getId() {
		return id;
	}

	public int getSalePrice() {
		return unitPrice;
	}

	public String toSaveString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(stock);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(alert);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(unitPrice);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(name);
		sb.append(KFileParameter.SEPARATOR);
		return sb.toString();
	}

	public String toString() {
		return name;
	}
	
	public Color getColor() {
		Color col = DColor.WHITE;
		if (stock < 0) {
			col = DColor.GRAY;
		} else if (stock == 0) {
			col = DColor.RED;
		} else if (stock < alert){
			col = DColor.ORANGE;
		}
		return col;
	}

	public void consumption(int number) {
		stock -= number;
	}

}
