package fr.kaice.tools;

public abstract class KFileParameter {
	
	/**
	 * {@link String} of the path of the repository.
	 */
	public static String REPOSITORY = "caisse_BDD";
	/**
	 * {@link String} of the files extension used.
	 */
	public static String EXTENSION = "cens";
	/**
	 * {@link String} of the main separator used in files;
	 */
	public static String SEPARATOR = ";";
	/**
	 * {@link String} of the secondary separator used in files;
	 */
	public static String SEPARATOR_SEC = "|";
	
	public static String RAW_FILE_NAME = "Stock";
	public static String PURCH_FILE_NAME = "Articles Achete";
	public static String SOLD_FILE_NAME = "Articles Vendus";
	public static String MEMBERS_FILE_NAME = "Member";
}
