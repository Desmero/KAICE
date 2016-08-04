package fr.kaice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import fr.kaice.model.KaiceModel;
import fr.kaice.view.panel.PanelCurrentTransaction;
import fr.kaice.view.panel.PanelHistoric;
import fr.kaice.view.panel.PanelMember;
import fr.kaice.view.panel.PanelOrder;
import fr.kaice.view.panel.PanelPurchasedProduct;
import fr.kaice.view.panel.PanelPurchasedProductLight;
import fr.kaice.view.panel.PanelRawMaterial;
import fr.kaice.view.panel.PanelSellProduct;

public class MainWindow extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4647074488941101569L;
	private JPanel centerSouth;
	
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
		tablePaneNorth.add("Vente", new PanelCurrentTransaction());
		tablePaneNorth.add("Achats", new PanelPurchasedProduct());
		JTabbedPane tablePaneSouth = new JTabbedPane();
		tablePaneSouth.add("Produits bruts", new PanelRawMaterial());
		tablePaneSouth.add("Articles en vente", new PanelSellProduct());
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
