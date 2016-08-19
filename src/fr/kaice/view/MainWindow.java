package fr.kaice.view;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.CloseListener;
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
public class MainWindow extends JFrame implements Observer {
    
    private final JPanel details;
    private final JSplitPane splitCenterIn;
    
    /**
     * Create a new {@link MainWindow}.
     */
    public MainWindow() {
        super("KAICE v2.0");
        
        KaiceModel.getInstance().addObserver(this);
    
        JPanel center = new JPanel(new BorderLayout());
        JPanel members = new JPanel(new BorderLayout());
        JPanel west = new JPanel(new BorderLayout());
    
        JSplitPane splitEastOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, center, members);
        splitEastOut.setResizeWeight(1);
        JSplitPane splitWestOut = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, west, splitEastOut);
        splitWestOut.setResizeWeight(0);
        this.add(splitWestOut);
    
        JPanel transaction = new JPanel(new BorderLayout());
        details = new JPanel(new BorderLayout());
        JPanel order = new JPanel(new BorderLayout());
        JPanel lists = new JPanel(new BorderLayout());
    
        splitCenterIn = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, transaction, details);
        splitCenterIn.setResizeWeight(1);
        JSplitPane splitWestIn = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, order, lists);
        splitWestIn.setResizeWeight(0.5);
        
        center.add(splitCenterIn, BorderLayout.CENTER);
        west.add(splitWestIn, BorderLayout.CENTER);
    
        JTabbedPane tabbedPaneTransaction = new JTabbedPane();
        tabbedPaneTransaction.add("Ventes", new PanelCurrentTransaction());
        tabbedPaneTransaction.add("Achats", new PanelPurchasedProduct());
        JTabbedPane tabbedPaneLists = new JTabbedPane();
        tabbedPaneLists.add("Produits bruts", new PanelRawMaterial());
        tabbedPaneLists.add("Articles en vente", new PanelSoldProduct());
        tabbedPaneLists.add("Articles achetés", new PanelPurchasedProductVal());
        tabbedPaneLists.add("Historique", new PanelHistoric());
    
        transaction.add(new PanelTitle("Transactions"), BorderLayout.NORTH);
        transaction.add(tabbedPaneTransaction, BorderLayout.CENTER);
        details.add(KaiceModel.getInstance().getDetails(), BorderLayout.CENTER);
        order.add(new PanelTitle("Commandes"), BorderLayout.NORTH);
        order.add(new PanelOrder(), BorderLayout.CENTER);
        lists.add(new PanelTitle("Listes"), BorderLayout.NORTH);
        lists.add(tabbedPaneLists, BorderLayout.CENTER);
        members.add(new PanelTitle("Membres"), BorderLayout.NORTH);
        members.add(new PanelMember(), BorderLayout.CENTER);
    
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Fichier");
        JMenu menuEdit = new JMenu("Édition");
        JMenu menuView = new JMenu("Affichage");
        JMenu menuHelp = new JMenu("Aide");
    
        JMenuItem mIExit = new JMenuItem("Quitter");
        mIExit.addActionListener(new CloseListener(this));
        JMenuItem mIExport = new JMenuItem("Exporter");
        JMenuItem mIHidden = new JCheckBoxMenuItem("Produits masqués");
        mIHidden.addActionListener(e -> KaiceModel.getInstance().changeShowHiddenState());
    
        menuFile.add(mIExport);
        menuFile.add(new JPopupMenu.Separator());
        menuFile.add(mIExit);
        menuBar.add(menuFile);
    
        menuBar.add(menuEdit);
    
        menuView.add(mIHidden);
        menuBar.add(menuView);
    
        menuBar.add(menuHelp);
    
        this.setJMenuBar(menuBar);
    
        Dimension windowDim;
        windowDim = new Dimension(1280, 1024);
        setPreferredSize(windowDim);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        pack();
        setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        setPreferredSize(getSize());
        JPanel newDetails = KaiceModel.getInstance().getDetails();
    
        details.removeAll();
        details.add(newDetails, BorderLayout.CENTER);
    
        splitCenterIn.setDividerLocation(-1);
        pack();
    }
    
}
