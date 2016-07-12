package fr.kaice.tools.generic;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner.NumberEditor;

public class DMonetarySpinner extends JSpinner {

	public DMonetarySpinner(double decal) {
		super(new DMonetarySpinnerModel(decal));
		JSpinner.NumberEditor numEditor = new NumberEditor(this, "0.00");
		DecimalFormat format = numEditor.getFormat();
		Locale loc = new Locale("en");
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(loc));
		this.setEditor(numEditor);
		JSpinner.DefaultEditor textEditor = (JSpinner.DefaultEditor)this.getEditor();
		JTextField textField = textEditor.getTextField();
		textField.addFocusListener( new FocusAdapter()
		{
		    public void focusGained(final FocusEvent e)
		    {
		        SwingUtilities.invokeLater(new Runnable()
		        {
		            @Override
		            public void run()
		            {
		                JTextField tf = (JTextField)e.getSource();
		                tf.selectAll();
		            }
		        });
		    }
		});
	}

	public int getIntValue() {
		return doubleToInt((double) super.getValue());
	}
	
	public static int doubleToInt(double value) {
		return (int) Math.round((double) value*100);
	}
	
	public static double intToDouble(int value) {
		return (double) value / 100;
	}
	
}
