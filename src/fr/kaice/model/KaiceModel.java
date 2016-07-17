package fr.kaice.model;

import java.util.Calendar;
import java.util.Observable;

import fr.kaice.model.buy.PurchasedProductCollection;
import fr.kaice.model.historic.Historic;
import fr.kaice.model.membre.MemberCollection;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.model.sell.CurrentTransaction;
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

	private static RawMaterialCollection rawMatColl = new RawMaterialCollection();
	private static SoldProductCollection soldProdColl = new SoldProductCollection();
	private static PurchasedProductCollection purProdColl = new PurchasedProductCollection();
	
	private static CurrentTransaction curTran = new CurrentTransaction();
	
	private static MemberCollection memColl = new MemberCollection();
	private static Historic hist = new Historic();
	
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

	public static RawMaterialCollection getRawMatCollection() {
		return rawMatColl;
	}

	public static SoldProductCollection getSoldProdCollection() {
		return soldProdColl;
	}

	public static PurchasedProductCollection getPurchasedProdCollection() {
		return purProdColl;
	}

	public static CurrentTransaction getCurrentTransaction() {
		return curTran;
	}

	public static MemberCollection getMemberCollection() {
		return memColl;
	}
	
	public static Historic getHistoric() {
		return hist;
	}
	
	public static String getEMailList() {
		return memColl.getEMailList();
	}
	
	public static int getActualYear() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR) - 2000;
		if (month < 8) {
			year--;
		}
		return year;
	}
	
	// TODO tenter de mettre cette fonction private
	public static void update() {
		model.setChanged();
		model.notifyObservers();
	}
	
}
