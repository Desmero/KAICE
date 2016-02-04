package fr.kaice.tools;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * 
 * @author Raph
 * @version 1.0
 */
public abstract class KFormat {

	/**
	 * {@link SimpleDateFormat}, only date, French representation :
	 * "dd/MM/yyyy".
	 */
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy");
	/**
	 * {@link SimpleDateFormat}, date + hour, French representation :
	 * "dd/MM/yyyy". HH:mm:ss
	 */
	public static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	/**
	 * {@link DecimalFormat} with 2 decimal digits.
	 */
	public static DecimalFormat MONEY_FORMAT = new DecimalFormat("#0.00");

}
