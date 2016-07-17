package fr.kaice.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fr.kaice.view.panel.PanelCurrentTransaction;
import fr.kaice.view.panel.PanelHistoric;
import fr.kaice.view.panel.PanelMember;
import fr.kaice.view.panel.PanelMemberDetails;
import fr.kaice.view.panel.PanelMemberLightDetails;
import fr.kaice.view.panel.PanelPurcheseProduct;
import fr.kaice.view.panel.PanelRawMaterial;
import fr.kaice.view.panel.PanelSellProduct;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4647074488941101569L;

	public MainWindow() {
		super("KAICE v2.0");
		
		JPanel center = new JPanel(new BorderLayout());
		JPanel east = new JPanel(new BorderLayout());
		JPanel west = new JPanel(new BorderLayout());
		
		JPanel centerNorth = new JPanel(new BorderLayout());
		JPanel centerSouth = new JPanel(new BorderLayout());
		JPanel eastNorth = new JPanel(new BorderLayout());
		JPanel eastSouth = new JPanel(new BorderLayout());
		JPanel westNorth = new JPanel(new BorderLayout());
		JPanel westSouth = new JPanel(new BorderLayout());
		
		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);

		center.add(centerNorth, BorderLayout.NORTH);
		center.add(centerSouth, BorderLayout.CENTER);
		east.add(eastNorth, BorderLayout.NORTH);
		east.add(eastSouth, BorderLayout.CENTER);
		west.add(westNorth, BorderLayout.NORTH);
		west.add(westSouth, BorderLayout.CENTER);
		
		JTabbedPane tablePaneNorth = new JTabbedPane();
		tablePaneNorth.add("Vente", new PanelCurrentTransaction());
		tablePaneNorth.add("Articles achetés", new PanelPurcheseProduct());
		JTabbedPane tablePaneSouth = new JTabbedPane();
		tablePaneSouth.add("Produits bruts", new PanelRawMaterial());
		tablePaneSouth.add("Articles en vente", new PanelSellProduct());
		tablePaneSouth.add("Historic", new PanelHistoric());
		JPanel member = new PanelMemberDetails(0);
		JPanel memberLight = new PanelMemberLightDetails(0);
		PanelMember memberColl = new PanelMember();
		
		centerNorth.add(tablePaneNorth, BorderLayout.NORTH);
		centerSouth.add(member, BorderLayout.CENTER);
		// westNorth
		westSouth.add(tablePaneSouth, BorderLayout.CENTER);
		eastNorth.add(memberLight, BorderLayout.NORTH);
		eastSouth.add(memberColl, BorderLayout.CENTER);
		
		pack();
		setVisible(true);
	}
	
}
