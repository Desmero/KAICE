package fr.kaice.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;

public class MainWindow extends JFrame{

	public MainWindow() {
		super("KAICE v2.0");
		
		KaiceModel model = KaiceModel.getInstance();
		
		JTabbedPane jtb1 = new JTabbedPane();
		jtb1.add("Raw", new DTablePanel(model, KaiceModel.getRawMatCollection()));
		jtb1.add("Sell", new PanelSellProduct());
		jtb1.add("Purchase", new DTablePanel(model, KaiceModel.getPurchasedProdCollection()));
		jtb1.add("Members", new DTablePanel(model, KaiceModel.getMemberCollection()));
		
		this.add(jtb1);

		pack();
		setVisible(true);
	}
	
}
