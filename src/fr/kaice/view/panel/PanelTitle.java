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
    private final JButton greenButton, redButton;
    private final JPanel buttons;

    /**
     * Create a {@link PanelTitle} with a title string.
     *
     * @param title {@link String} - The title to display.
     */
    public PanelTitle(String title) {
        this(title, null, null);
    }

    /**
     * Create a {@link PanelTitle} with a title string and a button to the right
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

        buttons = new JPanel(new BorderLayout());
        
        lTitle = new JLabel(title);
        lTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lTitle, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.EAST);

        JSeparator sep = new JSeparator();
        sep.setBackground(DColor.BLUE_TITLE);
        buttons.setBackground(DColor.BLUE_TITLE);
        this.add(sep, BorderLayout.SOUTH);

        greenButton = new JButton();
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
        greenButton.setPreferredSize(new Dimension(15, 15));
        buttons.add(greenButton, BorderLayout.WEST);
        if (greenButtonAction == null) {
            greenButton.setVisible(false);
        } else {
            greenButton.addActionListener(greenButtonAction);
        }

        redButton = new JButton();
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
        redButton.setPreferredSize(new Dimension(15, 15));
        buttons.add(redButton, BorderLayout.EAST);
        if (redButtonAction == null) {
            redButton.setVisible(false);
        } else {
            redButton.addActionListener(redButtonAction);
        }
    }

    public void setRedAction(ActionListener listener) {
        System.out.println(redButton.getActionListeners().length);
        redButton.removeActionListener(redButton.getActionListeners()[0]);
        if (listener == null) {
            redButton.setVisible(false);
        } else {
            redButton.addActionListener(listener);
            redButton.setVisible(true);
        }
    }

    public void setGreenAction(ActionListener listener) {
        if (greenButton.getActionListeners().length > 0) {
            greenButton.removeActionListener(greenButton.getActionListeners()[0]);
        }
        if (listener == null) {
            greenButton.setVisible(false);
        } else {
            greenButton.addActionListener(listener);
            greenButton.setVisible(true);
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
