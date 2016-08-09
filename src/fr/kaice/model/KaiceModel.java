package fr.kaice.model;

import fr.kaice.model.buy.PurchasedProductCollection;
import fr.kaice.model.historic.Historic;
import fr.kaice.model.membre.MemberCollection;
import fr.kaice.model.order.OrderCollection;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.model.sell.CurrentTransaction;
import fr.kaice.model.sell.SoldProductCollection;

import javax.swing.*;
import java.util.Calendar;
import java.util.Observable;

/**
 * This class is the center of all data of the program. This class is a
 * singleton, use the function getInstance().
 *
 * @author Raphaël Merkling
 * @version 2.0
 */
public class KaiceModel extends Observable {
    
    private static RawMaterialCollection rawMatColl = new RawMaterialCollection();
    private static SoldProductCollection soldProdColl = new SoldProductCollection();
    private static PurchasedProductCollection purProdColl = new PurchasedProductCollection();
    
    private static CurrentTransaction curTran = new CurrentTransaction();
    private static OrderCollection ordColl = new OrderCollection();
    
    private static MemberCollection memColl = new MemberCollection();
    private static Historic hist = new Historic();
    
    private static KaiceModel model = new KaiceModel();
    private JPanel details;
    
    private KaiceModel() {
        details = new JPanel();
    }
    
    /**
     * Return the instance of the class {@link KaiceModel}.
     *
     * @return The instance of the class {@link KaiceModel}.
     * @see KaiceModel
     */
    public static KaiceModel getInstance() {
        return model;
    }
    
    /**
     * Return the main {@link RawMaterialCollection} use by the programme.
     *
     * @return The main {@link RawMaterialCollection} use by the programme.
     */
    public static RawMaterialCollection getRawMatCollection() {
        return rawMatColl;
    }
    
    /**
     * Return the main {@link SoldProductCollection} use by the programme.
     *
     * @return The main {@link SoldProductCollection} use by the programme.
     */
    public static SoldProductCollection getSoldProdCollection() {
        return soldProdColl;
    }
    
    /**
     * Return the main {@link PurchasedProductCollection} use by the programme.
     *
     * @return The main {@link PurchasedProductCollection} use by the programme.
     */
    public static PurchasedProductCollection getPurchasedProdCollection() {
        return purProdColl;
    }
    
    /**
     * Return the main {@link CurrentTransaction} use by the programme.
     *
     * @return The main {@link CurrentTransaction} use by the programme.
     */
    public static CurrentTransaction getCurrentTransaction() {
        return curTran;
    }
    
    /**
     * Return the main {@link OrderCollection} use by the programme.
     *
     * @return The main {@link OrderCollection} use by the programme.
     */
    public static OrderCollection getOrderCollection() {
        return ordColl;
    }
    
    /**
     * Return the main {@link MemberCollection} use by the programme.
     *
     * @return The main {@link MemberCollection} use by the programme.
     */
    public static MemberCollection getMemberCollection() {
        return memColl;
    }
    
    /**
     * Return the main {@link Historic} use by the programme.
     *
     * @return The main {@link Historic} use by the programme.
     */
    public static Historic getHistoric() {
        return hist;
    }
    
    /**
     * Built and return a {@link String} witch contains e-Mail address of members who subscribes to the newsletters.
     * Every address are separate by a ';'.
     *
     * @return A list of e-Mail address.
     */
    public static String getEMailList() {
        return memColl.getEMailList();
    }
    
    /**
     * Return a number corresponding to the current administrative year (September to August of the next year).
     * A new year start the first September, and get the number of the first half year.
     * For example :
     * - in November 2015, it will return 15 (year 2015-2016);
     * - in February 2017, it will return 16 (year 2016-2017).
     *
     * @return A number corresponding to the current administrative year.
     */
    public static int getActualYear() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR) - 2000;
        if (month < 8) {
            year--;
        }
        return year;
    }
    
    /**
     * Return the current {@link JPanel} to display in the details section.
     *
     * @return The current {@link JPanel} to display in the details section.
     */
    public JPanel getDetails() {
        return details;
    }
    
    /**
     * Set the current {@link JPanel} to display in the details section.
     * Warning ! This method call {@link this#update()}.
     *
     * @param details {@link JPanel} - The new {@link JPanel} to display in the details section.
     */
    public void setDetails(JPanel details) {
        this.details = details;
        update();
    }
    
    /**
     * Call {@link Observable#setChanged()} and {@link Observable#notifyObservers()} methods.
     * Warning ! Call this function with caution, nothing prevent a infinite loop of update.
     *
     * @see Observable
     */
    public static void update() {
        model.setChanged();
        model.notifyObservers();
    }

}
