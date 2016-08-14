package fr.kaice.view.window;

import fr.kaice.tools.generic.CloseListener;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a generic {@link JDialog} with a "ok" button who close it.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see JDialog
 */
class WindowInform extends JDialog {
    
    /**
     * Create a new {@link WindowInform} witch display the windowName on top, the mainPanel in the center, and can block
     * or not the focus on that window.
     *
     * @param windowName {@link String} - The name of the window.
     * @param block      boolean - True if the focus must stay on this window.
     * @param mainPanel  {@link JPanel} - The panel to display in the window.
     */
    public WindowInform(String windowName, boolean block, JPanel mainPanel) {
        super((JFrame) null, windowName, block);
        this.setResizable(false);
        
        JPanel ctrl = new JPanel(new BorderLayout());
        JPanel ctrlButtons = new JPanel();
        
        JButton ok = new JButton("ok");
        ok.addActionListener(new CloseListener(this));
        
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        
        ctrl.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        ctrl.add(ctrlButtons, BorderLayout.CENTER);
        ctrlButtons.add(ok);
        
        pack();
        int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2);
        int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
                - (this.getSize().getHeight() / 2));
        this.setLocation(x, y);
        setVisible(true);
    }
}
