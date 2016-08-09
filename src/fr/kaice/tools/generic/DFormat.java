package fr.kaice.tools.generic;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is a collection of pre-define {@link SimpleDateFormat} and {@link DecimalFormat} with a static method for
 * select the better date format.
 *
 * @author Raph
 * @version 1.0
 */
public abstract class DFormat {
    
    /**
     * {@link SimpleDateFormat}, only date, French representation : "dd/MM/yyyy". <br/>
     * Example : "26/08/2016"
     */
    public static SimpleDateFormat DATE_ONLY = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * {@link SimpleDateFormat}, only date without year, French representation : "E dd/MM". <br/>
     * Example : "ven. 26/08"
     */
    public static SimpleDateFormat DATE_YEAR_LESS = new SimpleDateFormat("E dd/MM");
    
    /**
     * {@link SimpleDateFormat}, only the ful name of the day of the week : "EEEE". <br/>
     * Example : "Vendredi"
     */
    public static SimpleDateFormat DAY_OF_WEEK = new SimpleDateFormat("EEEE");
    
    /**
     * {@link SimpleDateFormat}, only hour (24 hours format), French representation : "HH:mm". <br/>
     * Example : "14:48"
     */
    public static SimpleDateFormat HOUR_ONLY = new SimpleDateFormat("HH:mm");
    /**
     * {@link SimpleDateFormat}, date + hour, French representation : "dd/MM/yyyy". HH:mm:ss. <br/>
     * Example : "26/08/2016 14:48:36"
     */
    public static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    /**
     * {@link DecimalFormat} with 2 decimal digits : "#0.00". <br/>
     * Example : "2.75"
     */
    public static DecimalFormat MONEY_FORMAT = new DecimalFormat("#0.00");
    
    /**
     * Description partially copied from {@link SimpleDateFormat#format(Date)}. <br/>
     * Formats a {@link Date} into date/time string with auto-selected date format. Do not work with future date.
     *
     * @param date {@link Date} - The time value to be formatted to time string
     * @return The formatted time string
     */
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
                    return DATE_YEAR_LESS.format(date);
                }
            } else {
                return DATE_ONLY.format(date);
            }
        } else {
            return HOUR_ONLY.format(date);
        }
    }
    
}
