package fr.kaice.view;

import fr.kaice.model.KaiceModel;
import fr.kaice.view.panel.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is the main window of the programme KAICE. <br/>
 * All the mains components a fixed, except a {@link JPanel} in the center down of the window who is selected by the
 * {@link Observable}.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JFrame
 * @see Observer
 */
public class MainWindow extends JFrame implements Observer{
    
    private final JPanel centerSouth;
    
    /**
     * Create a new {@link MainWindow}.
     */
    public MainWindow() {
        super("KAICE v2.0");
        
        KaiceModel.getInstance().addObserver(this);
        
        Dimension d;
        
        JPanel center = new JPanel(new BorderLayout());
        JPanel east = new JPanel(new BorderLayout());
        JPanel west = new JPanel(new BorderLayout());
        
        JSplitPane splitEastOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, center, east);
        splitEastOut.setResizeWeight(1);
        JSplitPane splitWestOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, west, splitEastOut);
        splitWestOut.setResizeWeight(0);
        
        this.setLayout(new BorderLayout());
        this.add(splitWestOut);
        
        JPanel centerNorth = new JPanel(new BorderLayout());
        centerSouth = new JPanel(new BorderLayout());
        JPanel westNorth = new JPanel(new BorderLayout());
        JPanel westSouth = new JPanel(new BorderLayout());
        
        JSplitPane splitCenterIn = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, centerNorth, centerSouth);
        splitCenterIn.setResizeWeight(1);
        JSplitPane splitWestIn = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, westNorth, westSouth);
        splitWestIn.setResizeWeight(0.5);
        
        center.add(splitCenterIn, BorderLayout.CENTER);
        west.add(splitWestIn, BorderLayout.CENTER);
        
        JTabbedPane tablePaneNorth = new JTabbedPane();
        tablePaneNorth.add("Ventes", new PanelCurrentTransaction());
        tablePaneNorth.add("Achats", new PanelPurchasedProduct());
        JTabbedPane tablePaneSouth = new JTabbedPane();
        tablePaneSouth.add("Produits bruts", new PanelRawMaterial());
        tablePaneSouth.add("Articles en vente", new PanelSoldProduct());
        tablePaneSouth.add("Articles achetés", new PanelPurchasedProductLight());
        tablePaneSouth.add("Historique", new PanelHistoric());
        PanelMember memberColl = new PanelMember();
        PanelOrder order = new PanelOrder();
        JPanel details = KaiceModel.getInstance().getDetails();
        
        centerNorth.add(tablePaneNorth, BorderLayout.CENTER);
        centerNorth.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
        centerSouth.add(details, BorderLayout.CENTER);
        westNorth.add(order, BorderLayout.CENTER);
        westNorth.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
        westSouth.add(tablePaneSouth, BorderLayout.CENTER);
        east.add(memberColl, BorderLayout.CENTER);
        
        Dimension dim = centerSouth.getPreferredSize();
        dim.setSize(dim.getWidth(), 300);
        centerSouth.setPreferredSize(dim);
        
        
        d = new Dimension(1600, 900);
        setPreferredSize(d);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        pack();
        setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        setPreferredSize(getSize());
        JPanel details = KaiceModel.getInstance().getDetails();
        centerSouth.removeAll();
        centerSouth.add(details, BorderLayout.CENTER);
        centerSouth.repaint();
        pack();
    }
    
}
