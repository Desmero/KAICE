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
        this(title, null, null);
    }

    /**
     * Creat a {@link PanelTitle} with a title string and a button to the right
     *
     * @param title           {@link String} - The title to display.
     * @param redButtonAction {@link ActionListener} - The action listener of the button.
     */
    public PanelTitle(String title, ActionListener redButtonAction) {
        this(title, redButtonAction, null);
    }

    public PanelTitle(String title, ActionListener redButtonAction, ActionListener greenButtonAction) {
        this.setLayout(new BorderLayout());
        this.setBackground(DColor.BLUE_TITLE);

        JPanel buttons = new JPanel(new BorderLayout());
        
        lTitle = new JLabel(title);
        lTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lTitle, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.EAST);

        JSeparator sep = new JSeparator();
        sep.setBackground(DColor.BLUE_TITLE);
        buttons.setBackground(DColor.BLUE_TITLE);
        this.add(sep, BorderLayout.SOUTH);

        if (greenButtonAction != null) {
            JButton greenButton = new JButton();
            greenButton.setBackground(DColor.BLUE_SELECTION);
            greenButton.setIcon(new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    int w = getIconWidth(), h = getIconHeight();
                    g.setColor(DColor.GREEN);
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
            greenButton.addActionListener(greenButtonAction);
            greenButton.setPreferredSize(new Dimension(15, 15));
            buttons.add(greenButton, BorderLayout.WEST);
        }

        if (redButtonAction != null) {
            JButton redButton = new JButton();
            redButton.setBackground(DColor.BLUE_SELECTION);
            redButton.setIcon(new Icon() {
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
            redButton.addActionListener(redButtonAction);
            redButton.setPreferredSize(new Dimension(15, 15));
            buttons.add(redButton, BorderLayout.EAST);
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
