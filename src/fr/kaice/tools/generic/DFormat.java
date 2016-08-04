package fr.kaice.tools.generic;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 
 * @author Raph
 * @version 1.0
 */
public abstract class DFormat {

	/**
	 * {@link SimpleDateFormat}, only date, French representation :
	 * "dd/MM/yyyy".
	 */
	public static SimpleDateFormat DATE_ONLY = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * {@link SimpleDateFormat}, only date without year, French representation :
	 * "E dd/MM".
	 */
	public static SimpleDateFormat DATE_YEARLESS = new SimpleDateFormat("E dd/MM");

	public static SimpleDateFormat DAY_OF_WEEK = new SimpleDateFormat("EEEE");
	
	/**
	 * {@link SimpleDateFormat}, only hour, French representation : "HH:mm".
	 */
	public static SimpleDateFormat HOUR_ONLY = new SimpleDateFormat("HH:mm");
	/**
	 * {@link SimpleDateFormat}, date + hour, French representation :
	 * "dd/MM/yyyy". HH:mm:ss
	 */
	public static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/**
	 * {@link DecimalFormat} with 2 decimal digits.
	 */
	public static DecimalFormat MONEY_FORMAT = new DecimalFormat("#0.00");

	public static String format(Date date) {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		if (date.before(today.getTime())) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
				if (cal.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR)) {
					return DAY_OF_WEEK.format(date);
				} else {
					return DATE_YEARLESS.format(date);
				}
			} else {
				return DATE_ONLY.format(date);
			}
		} else {
			return HOUR_ONLY.format(date);
		}
	}

}
