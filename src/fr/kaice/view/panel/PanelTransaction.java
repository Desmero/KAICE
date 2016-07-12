package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.Transaction;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

public class PanelTransaction extends JPanel {

	private Transaction tran;
	
	public PanelTransaction(Transaction tran) {

		this.tran = tran;
		
		JLabel member = new JLabel(tran.getClient());
		JLabel date = new JLabel(DFormat.FULL_DATE_FORMAT.format(tran.getDate()));
		JLabel price = new JLabel("" + DMonetarySpinner.intToDouble(tran.getPrice()));
		JLabel paid = new JLabel("" + DMonetarySpinner.intToDouble(tran.getPaid()));
		
		DTablePanel table = new DTablePanel(KaiceModel.getInstance(), tran);
		
		JPanel details = new JPanel(new GridLayout(2, 2));
		
		this.setLayout(new BorderLayout());
		this.add(details, BorderLayout.NORTH);
		this.add(table, BorderLayout.CENTER);
		
		details.add(member);
		details.add(date);
		details.add(price);
		details.add(paid);
	}
}
