package fr.kaice.tools;

/**
 * This class is a collection of static path for the serializer.
 */
public class KFilesParameters {
    
    /**
     * The path to the repositories of all files.
     */
    public static String globalPath = "/home/merkling/.KAICE/";
    public static final String pathSoldProductRep = globalPath + "SoldProducts/";
    public static String ext = ".ser";
    public static final String pathMembers = globalPath + "Members" + ext;
    public static final String pathHistoric = globalPath + "Historic" + ext;
    public static final String pathRawMaterial = globalPath + "RawMaterials" + ext;
    public static final String pathSoldProduct = globalPath + "SoldProducts" + ext;
    public static final String pathSoldProductCmp = globalPath + "SoldProductsCmp" + ext;
    public static final String pathPurchasedProduct = globalPath + "PurchasedProducts" + ext;
    
    public static void setGlobalPath(String globalPath) {
        KFilesParameters.globalPath = globalPath;
    }
}
