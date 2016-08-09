package fr.kaice.tools;

import fr.kaice.model.KaiceModel;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * This class is a custom {@link JSpinner}, create for membership number.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JSpinner
 */
public class IdSpinner extends JSpinner {
    
    /**
     * Create a new {@link IdSpinner}.
     * The super {@link JSpinner} is initialize with : <code>new SpinnerNumberModel(0, 0, null, 1)</code>
     */
    public IdSpinner() {
        super(new SpinnerNumberModel(0, 0, null, 1));
        construct();
    }
    
    /**
     * Private method used to initialize a new {@link IdSpinner}.
     */
    private void construct() {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) this.getEditor();
        JTextField textField = editor.getTextField();
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JTextField tf = (JTextField) e.getSource();
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
