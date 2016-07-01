package fr.kaice.view.panel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;

public class PanelMember extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelMember() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getMemberCollection());
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
