package fr.kaice.model;

import java.util.Observable;
import fr.kaice.model.buy.PurchasedProductCollection;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.model.sell.SoldProductCollection;

/**
 * This class is the center of all data of the program. This class is a
 * singleton, use the function getInstance().
 * 
 * @author Raph
 * @version 2.0
 *
 */
public class KaiceModel extends Observable {

	private RawMaterialCollection rawMatColl = new RawMaterialCollection();
	private SoldProductCollection soldProdColl = new SoldProductCollection();
	private PurchasedProductCollection purProdColl = new PurchasedProductCollection();

	private static KaiceModel model = new KaiceModel();

	/**
	 * Model constructor. Create all {@link AbstractTableModel} needed.
	 * 
	 */
	private KaiceModel() {
	}

	/**
	 * Return the instance of the class {@link KaiceModel}.
	 * 
	 * @return The instance of the class {@link KaiceModel}
	 * @see KaiceModel
	 */
	public static KaiceModel getInstance() {
		return model;
	}

	public RawMaterialCollection getRawMatCollection() {
		return rawMatColl;
	}

	public SoldProductCollection getSoldProdCollection() {
		return soldProdColl;
	}

	public PurchasedProductCollection getPurchasedProdCollection() {
		return purProdColl;
	}

}
