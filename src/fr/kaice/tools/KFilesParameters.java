package fr.kaice.tools;

/**
 * This class is a collection of static path for the serializer.
 */
public class KFilesParameters {
    
    /**
     * The path to the repositories of all files.
     */
    public static String globalPath = "/home/cens/.KAICE";
    public static String ext = ".ser";
    public static String extSec = ".cens";
    public static String pathMembers = globalPath + "/Members";
    public static String pathHistoric = globalPath + "/Historic" + ext;
    public static String pathRawMaterial = globalPath + "/RawMaterials" + ext;
    public static String pathSoldProduct = globalPath + "/SoldProducts" + ext;
    public static String pathSoldProductCmp = globalPath + "/SoldProducts" + extSec;
    public static String pathPurchasedProduct = globalPath + "/PurchasedProducts" + ext;
    
    public static void setGlobalPath(String globalPath) {
        KFilesParameters.globalPath = globalPath;
        pathMembers = globalPath + "/Members";
        pathHistoric = globalPath + "/Historic" + ext;
        pathRawMaterial = globalPath + "/RawMaterials" + ext;
        pathSoldProduct = globalPath + "/SoldProducts" + ext;
        pathSoldProductCmp = globalPath + "/SoldProducts" + extSec;
        pathPurchasedProduct = globalPath + "/PurchasedProducts" + ext;
    }
}
