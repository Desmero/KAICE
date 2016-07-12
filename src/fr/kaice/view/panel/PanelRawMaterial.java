package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTablePanel;

public class PanelRawMaterial extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelRawMaterial() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getRawMatCollection());
		JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) JOptionPane.showInputDialog(null,
						"Nom du produit : ", "Nouveau produit", -1, null, null,
						null);
				if (s != null) {
					KaiceModel.getRawMatCollection().addNewRawMaterial(s);
				}
			}
		});
		view.setEnabled(false);
		
		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(view);
	}

}
