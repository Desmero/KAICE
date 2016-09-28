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
    public static String fileSoldProduct = "SoldProducts";
    public static String filePurchasedProduct = "PurchasedProducts";
    public static String folderProductIcon = "logo";
    
    public static String pathHistoric = globalPath + "/Historic";
    
    public static void setGlobalPath(String globalPath) {
        KFilesParameters.globalPath = globalPath;
        pathHistoric = globalPath + "/Historic";
    }
    
    public static String getMemberFile(int yearCode) {
        return new StringBuilder(globalPath).append('/').append(fileMembers).append(yearCode).append(ext).toString();
    }
    
    public static String getRawMaterialFile() {
        return new StringBuilder(globalPath).append('/').append(fileRawMaterial).append(ext).toString();
    }

    public static String getSoldProductFile() {
        return new StringBuilder(globalPath).append('/').append(fileSoldProduct).append(ext).toString();
    }

    public static String getPurchasedProductFile() {
        return new StringBuilder(globalPath).append('/').append(filePurchasedProduct).append(ext).toString();
    }
    
    public static String getSoldProductIconFolder() {
        return new StringBuilder(globalPath).append('/').append(folderProductIcon).toString();
    }
}
