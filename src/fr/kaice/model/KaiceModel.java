package fr.kaice.model;

import fr.kaice.model.buy.PurchasedProductCollection;
import fr.kaice.model.historic.Historic;
import fr.kaice.model.member.MemberCollection;
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
    
    public static final int ALL = 0;
    public static final int RAW_MATERIAL = 1;
    public static final int PURCHASED_PRODUCT = 2;
    public static final int SOLD_PRODUCT = 3;
    public static final int MEMBER = 4;
    public static final int HISTORIC = 5;
    public static final int DETAILS = 6;
    public static final int ORDER = 7;
    public static final int TRANSACTION = 8;
    public static final int RESTOCK = 9;
    private static final RawMaterialCollection rawMatColl = new RawMaterialCollection();
    private static final SoldProductCollection soldProdColl = new SoldProductCollection();
    private static final PurchasedProductCollection purProdColl = new PurchasedProductCollection();
    private static final CurrentTransaction curTran = new CurrentTransaction();
    private static final OrderCollection ordColl = new OrderCollection();
    private static final MemberCollection memColl = new MemberCollection();
    private static final Historic hist = new Historic();
    private static final boolean[] change = new boolean[10];
    
    private static final KaiceModel model = new KaiceModel();
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
     * Return true if the asked part has been modified.
     *
     * @param part int - The part of the programe.
     * @return True if the asked part has been modified.
     */
    public static boolean isPartModified(int part) {
        return change[part] || change[ALL];
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
     * Warning ! This method call {@link this#update(int...)}.
     *
     * @param details {@link JPanel} - The new {@link JPanel} to display in the details section.
     */
    public void setDetails(JPanel details) {
        this.details = details;
        update(DETAILS);
    }
    
    /**
     * Call {@link Observable#setChanged()} and {@link Observable#notifyObservers()} methods.
     * Warning ! Call this function with caution, nothing prevent a infinite loop of update.
     * Update only concerned part.
     *
     *@param part int - The graphical part to update.
     * @see Observable
     */
    public static void update(int... part) {
        boolean update = false;
        for (int i :
                part) {
            if (!change[i]) {
                update = true;
                change[i] = true;
                System.out.println("Model update " + i);
            } else {
                System.out.printf("  OUT update " + i);
            }
        }
        if (update) {
            model.setChanged();
            model.notifyObservers();
        } else {
            System.out.printf("   NO update ");
            return;
        }
        for (int i :
                part) {
            change[i] = false;
        }
    }
}
