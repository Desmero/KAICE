package fr.kaice.tools;

/**
 * This class is a collection of static path for the serializer.
 */
public class KFilesParameters {
    
    /**
     * The path to the repositories of all files.
     */
    public static String globalPath = "/home/merkling/.KAICE/";
    public static final String pathMembers = globalPath + "Members.ser";
    public static final String pathHistoric = globalPath + "Historic.ser";
    public static final String pathRawMaterial = globalPath + "RawMaterials.ser";
    public static final String pathSoldProduct = globalPath + "SoldProducts.ser";
    public static final String pathPurchasedProduct = globalPath + "PurchasedProducts.ser";
    
    public static void setGlobalPath(String globalPath) {
        KFilesParameters.globalPath = globalPath;
    }
}
