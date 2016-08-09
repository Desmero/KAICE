package fr.kaice.tools.generic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is an {@link ActionListener} modified to only close a window.
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
public class CloseListener implements ActionListener {
    
    private final Window window;
    
    /**
     * Create a new {@link CloseListener}. The listener close the window when an action occurs.
     *
     * @param window {@link Window} - The window to close.
     */
    public CloseListener(Window window) {
        this.window = window;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        window.dispose();
    }
    
}
