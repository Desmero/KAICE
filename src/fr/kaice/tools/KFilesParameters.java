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
    public static String fileMembers = "Members";
    public static String fileRawMaterial = "RawMaterials";
    
    public static String pathHistoric = globalPath + "/Historic";
    public static String pathSoldProduct = globalPath + "/SoldProducts" + ext;
    public static String pathPurchasedProduct = globalPath + "/PurchasedProducts" + ext;
    
    public static void setGlobalPath(String globalPath) {
        KFilesParameters.globalPath = globalPath;
        pathHistoric = globalPath + "/Historic";
        pathSoldProduct = globalPath + "/SoldProducts" + ext;
        pathPurchasedProduct = globalPath + "/PurchasedProducts" + ext;
    }
    
    public static String getMemberFile(int yearCode) {
        return new StringBuilder(globalPath).append('/').append(fileMembers).append(yearCode).append(ext).toString();
    }
    
    public static String getRawMaterialFile() {
        return new StringBuilder(globalPath).append('/').append(fileRawMaterial).append(ext).toString();
    }
}
