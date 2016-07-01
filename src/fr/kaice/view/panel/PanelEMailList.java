package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.kaice.model.KaiceModel;

public class PanelEMailList extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea emails;

	public PanelEMailList() {
		KaiceModel.getInstance().addObserver(this);
		
		emails = new JTextArea();
		emails.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(emails);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		emails.setText(KaiceModel.getEMailList());
		System.out.println("email : \n" + KaiceModel.getEMailList());
	}
}
