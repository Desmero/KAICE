package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JDateChooser;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.membre.Member;
import fr.kaice.model.membre.MemberCollection;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DFormat;

public class PanelMemberLightDetails extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IdSpinner id;
	private JLabel name;
	private JLabel firstname;

	public PanelMemberLightDetails(int memberId) {
		int col = 10;

		id = new IdSpinner();
		id.setValue(memberId);
		id.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateId();
			}
		});
		name = new JLabel();
		firstname = new JLabel();
		
		
		JPanel details = new JPanel(new GridLayout(3, 2));

		this.setLayout(new BorderLayout());
		this.add(details, BorderLayout.CENTER);

		details.add(new JLabel("Id : "));
		details.add(id);
		details.add(new JLabel("Nom : "));
		details.add(name);
		details.add(new JLabel("Prenom : "));
		details.add(firstname);

		update();
	}

	private void updateId() {
		int newId = (int) id.getValue();
		update();
	}

	private void update() {
		int memId = id.getValue();
		Member m = KaiceModel.getMemberCollection().getMember(memId);
		if (m != null) {
			name.setText(m.getName());
			firstname.setText(m.getFirstname());
		} else {
			name.setText("...");
			firstname.setText("...");
		}
		repaint();
	}

}
