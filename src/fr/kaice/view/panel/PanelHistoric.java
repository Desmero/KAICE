package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.Transaction;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.tools.generic.TimePeriodChooser;
import fr.kaice.view.window.WindowInform;

public class PanelHistoric extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6696019278375971766L;
	private TimePeriodChooser dateChooser;
	
	public PanelHistoric() {
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getHistoric());
		dateChooser = new TimePeriodChooser();
		JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
		JPanel ctrl = new JPanel();

		add.setEnabled(false);
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					Transaction tran = KaiceModel.getHistoric().getTransaction(table.getSelectedRow());
					KaiceModel.getInstance().setDetails(new PanelTransaction(tran));
				}
			}
		});

		table.setMultiselection(false);

		JPanel tableDate = new JPanel(new BorderLayout());
		tableDate.add(table, BorderLayout.CENTER);
		tableDate.add(dateChooser, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(tableDate, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		ctrl.add(add);
		ctrl.add(view);
	}
}
