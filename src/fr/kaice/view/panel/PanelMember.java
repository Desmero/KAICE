package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DTablePanel;
import fr.kaice.view.window.ViewSellProductDetails;

public class PanelMember extends JPanel {

	public PanelMember() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getMemberCollection());
		JButton add = new JButton("Ajouter"), remove = new JButton("Supprimer"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(remove);
		ctrl.add(view);
	}

}
