package fr.kaice.view.panel;

import fr.kaice.tools.generic.DColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class is a {@link JPanel} how only display a string, and can have a button used to "close" another panel.
 */
public class PanelTitle extends JPanel {
    
    private final JLabel lTitle;
    
    /**
     * Create a {@link PanelTitle} with a title string.
     *
     * @param title {@link String} - The title to display.
     */
    public PanelTitle(String title) {
        this(title, null);
    }
    
    /**
     * Creat a {@link PanelTitle} with a title string and a button to the right
     *
     * @param title        {@link String} - The title to display.
     * @param buttonAction {@link ActionListener} - The action listener of the button.
     */
    public PanelTitle(String title, ActionListener buttonAction) {
        this.setLayout(new BorderLayout());
        this.setBackground(DColor.BLUE_TITLE);
        
        lTitle = new JLabel(title);
        lTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lTitle, BorderLayout.CENTER);
        
        JSeparator sep = new JSeparator();
        sep.setBackground(DColor.BLUE_TITLE);
        this.add(sep, BorderLayout.SOUTH);
        
        if (buttonAction != null) {
            JButton close = new JButton();
            close.setBackground(DColor.BLUE_SELECTION);
            close.setIcon(new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    int w = getIconWidth(), h = getIconHeight();
                    g.setColor(DColor.RED);
                    g.fillRect(x, y, w, h);
                }
                
                @Override
                public int getIconWidth() {
                    return 25;
                }
                
                @Override
                public int getIconHeight() {
                    return 25;
                }
            });
            close.addActionListener(buttonAction);
            close.setPreferredSize(new Dimension(15, 15));
            this.add(close, BorderLayout.EAST);
        }
    }
    
    /**
     * Change the title.
     *
     * @param newTitle {@link String} - The new title.
     */
    public void setTitle(String newTitle) {
        lTitle.setText(newTitle);
    }
}
