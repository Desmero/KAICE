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
    public static String pathMembers = globalPath + "/Members";
    public static String pathHistoric = globalPath + "/Historic";
    public static String pathRawMaterial = globalPath + "/RawMaterials" + ext;
    public static String pathSoldProduct = globalPath + "/SoldProducts" + ext;
    public static String pathPurchasedProduct = globalPath + "/PurchasedProducts" + ext;
    
    public static void setGlobalPath(String globalPath) {
        KFilesParameters.globalPath = globalPath;
        pathMembers = globalPath + "/Members";
        pathHistoric = globalPath + "/Historic";
        pathRawMaterial = globalPath + "/RawMaterials" + ext;
        pathSoldProduct = globalPath + "/SoldProducts" + ext;
        pathPurchasedProduct = globalPath + "/PurchasedProducts" + ext;
    }
}
