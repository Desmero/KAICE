package fr.kaice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
		
		JPanel center = new JPanel(new BorderLayout());
		JPanel east = new JPanel(new BorderLayout());
		JPanel west = new JPanel(new BorderLayout());
		
		JPanel centerNorth = new JPanel(new BorderLayout());
		centerSouth = new JPanel(new BorderLayout());
		JPanel westNorth = new JPanel(new BorderLayout());
		JPanel westSouth = new JPanel(new BorderLayout());
		
		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);

		center.add(centerNorth, BorderLayout.CENTER);
		center.add(centerSouth, BorderLayout.SOUTH);
		west.add(westNorth, BorderLayout.NORTH);
		west.add(westSouth, BorderLayout.CENTER);
		
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
		centerSouth.add(details, BorderLayout.CENTER);
		westNorth.add(order, BorderLayout.CENTER);
		westSouth.add(tablePaneSouth, BorderLayout.CENTER);
		east.add(memberColl, BorderLayout.CENTER);
		
		Dimension d = new Dimension(1600, 900);
		setPreferredSize(d);
		
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
