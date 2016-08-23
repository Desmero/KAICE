package fr.kaice.tools.generic;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * This class is a {@link JSpinner} create for monetary value. It use a {@link DMonetarySpinnerModel}. The format is
 * imposed as the English one, with a dote '.' decimal separator. And this class have methods for converting common
 * (double) values into cents (integer) values. The double value is ONLY a display value, use the integer value for
 * operations.
 *
 * @author Raphaël Merkling
 * @version 2.0
 */
public class DMonetarySpinner extends JSpinner {
    
    /**
     * Create a new {@link DMonetarySpinner} with a {@link DMonetarySpinnerModel}.
     *
     * @param stepSize double - The difference between elements of the sequence.
     */
    public DMonetarySpinner(double stepSize) {
        super(new DMonetarySpinnerModel(stepSize));
        JSpinner.NumberEditor numEditor = new NumberEditor(this, "0.00");
        DecimalFormat format = numEditor.getFormat();
        Locale loc = new Locale("en");
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(loc));
        this.setEditor(numEditor);
        JSpinner.DefaultEditor textEditor = (JSpinner.DefaultEditor) this.getEditor();
        JTextField textField = textEditor.getTextField();
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    JTextField tf = (JTextField) e.getSource();
                    tf.selectAll();
                });
            }
        });
    }
    
    public static String intToString(int value) {
        return "" + DFormat.MONEY_FORMAT.format(intToDouble(value)) + " " + DFormat.EURO;
    }
    
    /**
     * Convert a cents value in integer into a common monetary value in double.
     *
     * @param value int - The integer cents value to convert in double common monetary value.
     * @return The common value in a double.
     */
    public static double intToDouble(int value) {
        return (double) value / 100;
    }
    
    /**
     * Return the cents value with a integer.
     *
     * @return The integer value in cents.
     */
    public int getIntValue() {
        return doubleToInt((double) super.getValue());
    }
    
    /**
     * Convert a common monetary value in double into a cents value in integer.
     *
     * @param value double - The double common value to convert in integer cents value.
     * @return The cents value in a integer.
     */
    public static int doubleToInt(double value) {
        return (int) Math.round(value * 100);
    }
    
}
