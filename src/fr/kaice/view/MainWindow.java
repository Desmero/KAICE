package fr.kaice.view;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.CloseListener;
import fr.kaice.view.panel.*;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static fr.kaice.tools.local.French.*;

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
        super(PROGRAMME_NAME);
        
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
        tabbedPaneTransaction.add(TAB_SELLING, new PanelCurrentTransaction());
        tabbedPaneTransaction.add(TAB_BUYING, new PanelPurchasedProduct());
        JTabbedPane tabbedPaneLists = new JTabbedPane();
        tabbedPaneLists.add(TAB_RAW_MATERIAL, new PanelRawMaterial());
        tabbedPaneLists.add(TAB_SOLD_PRODUCT, new PanelSoldProduct());
        tabbedPaneLists.add(TAB_PURCHASED_PRODUCT, new PanelPurchasedProductVal());
        tabbedPaneLists.add(TAB_HISTORIC, new PanelHistoric());
        
        transaction.add(new PanelTitle(TITLE_TRANSACTION), BorderLayout.NORTH);
        transaction.add(tabbedPaneTransaction, BorderLayout.CENTER);
        details.add(KaiceModel.getInstance().getDetails(), BorderLayout.CENTER);
        order.add(new PanelTitle(TITLE_ORDER), BorderLayout.NORTH);
        order.add(new PanelOrder(), BorderLayout.CENTER);
        lists.add(new PanelTitle(TITLE_LISTS), BorderLayout.NORTH);
        lists.add(tabbedPaneLists, BorderLayout.CENTER);
        members.add(new PanelTitle(TITLE_MEMBERS), BorderLayout.NORTH);
        members.add(new PanelMember(), BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuFile = new JMenu(M_FILE);
        menuBar.add(menuFile);
        JMenuItem miExport = new JMenuItem(MF_EXPORT);
//        menuFile.add(miExport);
//        menuFile.add(new JPopupMenu.Separator());
        JMenuItem miExit = new JMenuItem(MF_EXIT);
        miExit.addActionListener(new CloseListener(this));
        menuFile.add(miExit);
        
        JMenu menuEdit = new JMenu(M_EDIT);
        menuBar.add(menuEdit);
        JMenuItem miAddAdmin = new JMenuItem(ME_ADD_CASHIER);
        miAddAdmin.addActionListener(e -> WindowAskAdmin.generate(e2 -> KaiceModel.getInstance().setDetails(new PanelAddAdmin())));
        menuEdit.add(miAddAdmin);
        menuEdit.add(new JPopupMenu.Separator());
        JMenuItem miOption = new JMenuItem(ME_OPTION);
        miOption.addActionListener(e -> KaiceModel.getInstance().setDetails(new PanelOption()));
        menuEdit.add(miOption);
        
        JMenu menuView = new JMenu(M_VIEW);
        menuBar.add(menuView);
        JMenuItem miHidden = new JCheckBoxMenuItem(MV_HIDDEN);
        miHidden.addActionListener(e -> KaiceModel.getInstance().changeShowHiddenState());
        menuView.add(miHidden);
        
        JMenu menuHelp = new JMenu(M_HELP);
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
