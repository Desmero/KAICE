package fr.kaice.tools.generic;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by merkling on 18/09/16.
 */
public class FocusTextField extends JTextField {
    
    private final FocusListener listener = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            FocusTextField.this.select(0, getText().length());
        }
        @Override
        public void focusLost(FocusEvent e) {
            FocusTextField.this.select(0, 0);
        }
    };
    
    public FocusTextField(int columns) {
        super(columns);
        this.addFocusListener(listener);
    }
    
    public FocusTextField() {
        super();
        this.addFocusListener(listener);
    }
}
