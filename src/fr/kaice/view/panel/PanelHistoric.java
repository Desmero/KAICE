package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;
import fr.kaice.view.window.WindowInform;

public class PanelHistoric extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6696019278375971766L;

	public PanelHistoric() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getHistoric());
		JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		add.setEnabled(false);
		view.setEnabled(false);

		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(view);
	}
}
