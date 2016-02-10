package fr.kaice.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;
import fr.kaice.view.panel.PanelMember;
import fr.kaice.view.panel.PanelPurcheseProduct;
import fr.kaice.view.panel.PanelRawMaterial;
import fr.kaice.view.panel.PanelSellProduct;

public class MainWindow extends JFrame{

	public MainWindow() {
		super("KAICE v2.0");
		
		JTabbedPane jtb1 = new JTabbedPane();
		jtb1.add("Raw", new PanelRawMaterial());
		jtb1.add("Sell", new PanelSellProduct());
		jtb1.add("Purchase", new PanelPurcheseProduct());
		jtb1.add("Members", new PanelMember());
		
		this.add(jtb1);

		pack();
		setVisible(true);
	}
	
}
