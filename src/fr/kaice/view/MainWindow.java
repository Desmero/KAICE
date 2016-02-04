package fr.kaice.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;

public class MainWindow extends JFrame{

	public MainWindow() {
		super("KAICE v2.0");
		
		KaiceModel model = KaiceModel.getInstance();
		
		this.setLayout(new BorderLayout());

		this.add(new DTablePanel(model, model.getRawMatCollection()), BorderLayout.CENTER);
		this.add(new DTablePanel(model, model.getSoldProdCollection()), BorderLayout.EAST);
		
		pack();
		setVisible(true);
	}
	
}
