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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JDateChooser;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.model.membre.Member;
import fr.kaice.model.membre.MemberCollection;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DFormat;

public class PanelMemberDetails extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean gender;
	private boolean edition;

	private IdSpinner id;
	private JTextField name;
	private JTextField firstname;
	private JPanel pBirth;
	private JLabel lBirthDate;
	private JDateChooser birthDate;
	private JPanel pGender;
	private JLabel lGender;
	private JButton bGender;
	private JTextField studies;
	private JTextField mailStreet;
	private JTextField mailPostalCode;
	private JTextField mailTown;
	private JTextField eMail;
	private JCheckBox newsLetter;
	private JTextField tel;
	private JButton edit;
	private JButton editValid;

	public PanelMemberDetails(int memberId) {
		edition = false;
		construct(memberId);
	}

	public PanelMemberDetails(int memberId, boolean edition) {
		this.edition = edition;
		construct(memberId);
	}

	private void construct(int memberId) {
		int col = 10;

		id = new IdSpinner();
		id.setValue(memberId);
		id.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateId();
			}
		});

		name = new JTextField(col);
		firstname = new JTextField(col);

		pBirth = new JPanel(new GridLayout(1, 1));
		lBirthDate = new JLabel();
		birthDate = new JDateChooser(new Date(), "dd/MM/yyyy");
		pBirth.add(lBirthDate);

		gender = true;
		pGender = new JPanel(new GridLayout(1, 1));
		lGender = new JLabel();
		bGender = new JButton();
		pGender.add(lGender);
		bGender.setPreferredSize(name.getPreferredSize());
		bGender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gender = !gender;
				updateGender();
			}
		});
		studies = new JTextField(col);
		col = 30;
		mailStreet = new JTextField(col);
		mailPostalCode = new JTextField(col);
		mailTown = new JTextField(col);
		eMail = new JTextField(col);
		newsLetter = new JCheckBox("newsLetter");
		tel = new JTextField();

		edit = new JButton("Éditer");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				edition = !edition;
				update();
			}
		});
		editValid = new JButton("Valider");
		editValid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editProfil();
				edition = false;
				update();
			}
		});

		// TODO test BorderLayout bl = new BorderLayout();
		JPanel center = new JPanel(new BorderLayout());
		JPanel details = new JPanel(new BorderLayout());
		JPanel detailsLeft = new JPanel(new GridLayout(6, 2));
		JPanel detailsCenter = new JPanel(new GridLayout(6, 4));
		JPanel detailsRightR = new JPanel(new GridLayout(6, 1, 0, 8));
		JPanel detailsRightL = new JPanel(new GridLayout(6, 1));
		JPanel detailsEdit = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		this.add(details, BorderLayout.NORTH);

		// details.add(detailsLeft, BorderLayout.WEST);
		// details.add(detailsRightR, BorderLayout.CENTER);
		details.add(detailsCenter, BorderLayout.CENTER);
		// details.add(detailsRightL, BorderLayout.EAST);
		details.add(detailsEdit, BorderLayout.SOUTH);

		detailsCenter.add(new JLabel("Id : "));
		detailsCenter.add(id);
		detailsCenter.add(new JLabel("Adresse : "));
		detailsCenter.add(mailStreet);
		detailsCenter.add(new JLabel("Nom : "));
		detailsCenter.add(name);
		detailsCenter.add(new JLabel("    (Code postal)"));
		detailsCenter.add(mailPostalCode);
		detailsCenter.add(new JLabel("Prenom : "));
		detailsCenter.add(firstname);
		detailsCenter.add(new JLabel("    (Commune)"));
		detailsCenter.add(mailTown);
		detailsCenter.add(new JLabel("Date de naissance : "));
		detailsCenter.add(pBirth);
		detailsCenter.add(new JLabel("Adresse e-Mail : "));
		detailsCenter.add(eMail);
		detailsCenter.add(new JLabel("Sexe : "));
		detailsCenter.add(pGender);
		detailsCenter.add(new JLabel());
		detailsCenter.add(newsLetter);
		detailsCenter.add(new JLabel("Filière : "));
		detailsCenter.add(studies);
		detailsCenter.add(new JLabel("Numero de Tel : "));
		detailsCenter.add(tel);
		
		detailsEdit.add(editValid);
		detailsEdit.add(edit);

		update();
	}

	private void updateId() {
		edition = false;
		int newId = (int) id.getValue();
		// TODO historique
		// tableModel.setId(newId);
		if (KaiceModel.getMemberCollection().isIdUsed(newId)) {
			gender = KaiceModel.getMemberCollection().getMember(newId).isMale();
		} else {
			gender = true;
		}
		update();
	}

	private void editProfil() {
		MemberCollection col = KaiceModel.getMemberCollection();
		Member u = col.getMember(id.getValue());
		if (u == null) {
			u = new Member(id.getValue());
			int res = JOptionPane.showConfirmDialog(null,
					"Le payment a-t-il été effectué en luiquide ?", "Payement",
					JOptionPane.YES_NO_OPTION, 2);
			int paid = 0;
			if (res == JOptionPane.YES_OPTION) {
				// TODO ajouter la possibilité de changer le prix de l'inscription
				paid = 500;
			}
			Transaction tran = new Transaction(id.getValue(), transactionType.INS, 500, paid, new Date());
			ArchivedProduct archProd = new ArchivedProduct("Inscription", 1, 5);
			tran.addArchivedProduct(archProd);
			KaiceModel.getHistoric().addTransaction(tran);
			col.addMember(u);
		}
		u.setName(name.getText());
		u.setFirstname(firstname.getText());
		u.setBirthDate(birthDate.getDate());
		u.setGender(gender);
		u.setStudies(studies.getText());
		u.setMailStreet(mailStreet.getText());
		u.setMailPostalCode(mailPostalCode.getText());
		u.setMailTown(mailTown.getText());
		u.setEMail(eMail.getText());
		u.setNewsLetter(newsLetter.isSelected());
		u.setPhoneNumber(tel.getText());
		// TODO write file
		// Model.getInstance().writeAllUSers();
		// Model.getInstance().update();
	}

	private void updateGender() {
		if (gender) {
			lGender.setText("Homme");
			bGender.setText("Homme");
		} else {
			lGender.setText("Femme");
			bGender.setText("Femme");
		}

	}

	private void update() {
		int memId = id.getValue();
		Member m = KaiceModel.getMemberCollection().getMember(memId);
		// TODO table model ...

		pBirth.removeAll();
		pGender.removeAll();
		if (edition) {
			pBirth.add(birthDate);
			pGender.add(bGender);
			edit.setText("Annuler");
		} else {
			pBirth.add(lBirthDate);
			pGender.add(lGender);
			if (m == null) {
				edit.setText("Ajouter");
			} else {
				edit.setText("Éditer");
			}
		}
		editValid.setVisible(edition);

		name.setEditable(edition);
		firstname.setEditable(edition);
		bGender.setEnabled(edition);
		studies.setEditable(edition);
		mailStreet.setEditable(edition);
		mailPostalCode.setEditable(edition);
		mailTown.setEditable(edition);
		eMail.setEditable(edition);
		newsLetter.setEnabled(edition);
		tel.setEditable(edition);

		if (m != null) {
			gender = m.isMale();
			name.setText(m.getName());
			firstname.setText(m.getFirstname());
			Calendar cal = Calendar.getInstance();
			cal.setTime(m.getBirthDate());
			birthDate.setDate(cal.getTime());
			lBirthDate.setText(DFormat.DATE_ONLY.format(m.getBirthDate()));
			updateGender();
			studies.setText(m.getStudies());
			mailStreet.setText(m.getMailStreet());
			mailPostalCode.setText(m.getMailPostalCode());
			mailTown.setText(m.getMailTown());
			eMail.setText(m.getEMail());
			if (m.isValidEmailAddress()) {
				eMail.setForeground(Color.DARK_GRAY);
			} else {
				eMail.setForeground(Color.RED);
			}
			if (m.isAdult()) {
				lBirthDate.setForeground(Color.DARK_GRAY);
			} else {
				lBirthDate.setForeground(Color.RED);
			}
			newsLetter.setSelected(m.isNewsLetter());
			tel.setText(m.getPhoneNumber());
		} else {
			name.setText("...");
			firstname.setText("...");
			Calendar cal = Calendar.getInstance();
			cal.set(1900, 0, 1);
			birthDate.setDate(cal.getTime());
			lBirthDate.setText("../../....");
			lGender.setText("...");
			bGender.setText("...");
			studies.setText("...");
			mailStreet.setText("...");
			mailPostalCode.setText(".....");
			mailTown.setText("...");
			eMail.setText("...");
			newsLetter.setSelected(false);
			tel.setText(".. .. .. .. ..");
		}
		repaint();
	}

}
