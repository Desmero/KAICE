package fr.kaice.tools;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.hamcrest.core.IsSame;

import fr.kaice.model.KaiceModel;

public class IdSpinner extends JSpinner {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdSpinner(int value) {
		super(new SpinnerNumberModel(0, 0, null, 1));
		construct();
		setValue(value);
	}
	
	public IdSpinner() {
		super(new SpinnerNumberModel(0, 0, null, 1));
		construct();
	}
	
	private void construct() {
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor)this.getEditor();
		JTextField textField = editor.getTextField();
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

	@Override
	public Integer getValue() {
		int id = (int) super.getValue();
		
		if (id > 0 && id < 10000) {
			int add = KaiceModel.getActualYear();
			add *= 10000;
			id += add;
		}
		
		return id;
	}
	
}
