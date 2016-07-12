package fr.kaice.model.sell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.tools.DMonetarySpinner;
import fr.kaice.tools.DTableModel;
import fr.kaice.tools.GenericProduct;
import fr.kaice.tools.KFileParameter;

/**
 * This class represent one kind of sold product
 * 
 * @author Raph
 * @version 2.0
 *
 */
public class SoldProduct extends DTableModel implements GenericProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422533674812971388L;

	/**
	 * This define the type of a {@link SoldProduct}. This could be FOOD, DRINK
	 * or MISC.
	 * 
	 * @author Raph
	 *
	 */
	public enum prodType {
		FOOD("Nourriture"), DRINK("Boisson"), MISC("Autre");

		private String name;

		private prodType(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	};

	private int id;
	private String name;
	private int salePrice;
	private Map<Integer, Integer> listRawMat;
	private prodType type;

	/**
	 * SoldProduct constructor.
	 * 
	 * @param id
	 *            int - The id of the product.
	 * @param name
	 *            {@link String} - The name of the product.
	 * @param salePrice
	 *            int - The sale price of the product.
	 * @param type
	 *            {@link prodType} - The type of the product.
	 */
	public SoldProduct(int id, String name, int salePrice, prodType type) {
		colNames = new String[] { "Id", "Nom", "Quentité utilisée", "Stock", "Prix" };
		colClass = new Class[] { Integer.class, String.class, Integer.class, Integer.class, Double.class };
		colEdit = new Boolean[] { false, false, true, false, false };
		this.id = id;
		this.name = name;
		this.salePrice = salePrice;
		this.listRawMat = new HashMap<>();
		this.type = type;
	}

	/**
	 * Return the quantity of a {@link RawMaterial} that need this product.
	 * Return 0 if the {@link RawMaterial} don't exist.
	 * 
	 * @param idRaw
	 *            int - The id of the {@link RawMaterial}.
	 * @return int - The quantity of a {@link RawMaterial}.
	 */
	public int getRawMaterial(int idRaw) {
		Integer quantity = listRawMat.get(idRaw);
		if (quantity == null) {
			quantity = 0;
		}
		return quantity;
	}

	/**
	 * Set the quantity of a {@link RawMaterial} that need this product. The
	 * quantity must be positive. If the quantity is equals to 0, the
	 * {@link RawMaterial} is remove to collection.
	 * 
	 * @param rawId
	 *            int - The id of the {@link RawMaterial}.
	 * @param quantity
	 *            - The quantity, must be positive or equals to 0.
	 */
	public void setRawMaterial(int rawId, int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity equals to " + quantity + " must be positive.");
		} else if (quantity == 0) {
			listRawMat.remove(rawId);
		} else {
			listRawMat.put(rawId, quantity);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getId() {
		return id;
	}

	public prodType getType() {
		return type;
	}

	public int getBuyPrice() {
		int price = 0;
		int qtt;
		RawMaterialCollection rmColl = KaiceModel.getRawMatCollection();
		RawMaterial mat;
		for (Integer id : listRawMat.keySet()) {
			qtt = listRawMat.get(id);
			mat = rmColl.getMat(id);
			price += qtt * mat.getPrice();
		}
		return price;
	}

	public int getProfit() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getQuantity() {
		int qtty = Integer.MAX_VALUE;
		for (Integer id : listRawMat.keySet()) {
			qtty = Integer.min(qtty, (KaiceModel.getRawMatCollection().getMat(id).getStock() / listRawMat.get(id)));
		}
		return qtty;
	}

	public static prodType parstType(String arg0) {
		prodType type;
		if (arg0.equals("FOOD")) {
			type = SoldProduct.prodType.FOOD;
		} else if (arg0.equals("DRINK")) {
			type = SoldProduct.prodType.DRINK;
		} else {
			type = SoldProduct.prodType.MISC;
		}
		return type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(salePrice);
		sb.append(KFileParameter.SEPARATOR);
		sb.append(name);
		// TODO gérer la liste de matieres premieres
		sb.append(KFileParameter.SEPARATOR);
		return sb.toString();
	}

	@Override
	public int getRowCount() {
		return listRawMat.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<Integer> list = new ArrayList<>(listRawMat.keySet());
		int id = list.get(rowIndex);
		RawMaterial mat = KaiceModel.getRawMatCollection().getMat(id);
		switch (columnIndex) {
		case 0:
			return id;
		case 1:
			return mat.getName();
		case 2:
			return listRawMat.get(id);
		case 3:
			return mat.getStock();
		case 4:
			return DMonetarySpinner.intToDouble(mat.getPrice());
		default:
			return null;
		}
	}

}
