package fr.kaice.view.panel;

import com.toedter.calendar.JDateChooser;
import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.model.member.Member;
import fr.kaice.model.member.MemberCollection;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DFormat;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * This panel display all information about a {@link Member}.
 * The interface allow to edit a member or create a new one.
 *
 * @author Rapha�l Merkling
 * @version 2.0
 * @see JPanel
 * @see Member
 */
class PanelMemberDetails extends JPanel {
    
    private boolean gender;
    private boolean edition;
    
    private IdSpinner id; // also call membership number
    private JTextField name;
    private JTextField firstName;
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
    
    /**
     * Create a new {@link PanelMemberDetails} in visualisation mode.
     *
     * @param memberId
     *          int - The membership number of the {@link Member}.
     */
    public PanelMemberDetails(int memberId) {
        edition = false;
        construct(memberId);
    }
    
    /**
     * Create a new {@link PanelMemberDetails}.
     *
     * @param memberId
     *          int - The membership number of the {@link Member}.
     * @param edition
     *          boolean - True for edition mode, false for visualisation mode.
     */
    public PanelMemberDetails(int memberId, boolean edition) {
        this.edition = edition;
        construct(memberId);
    }
    
    /**
     * Method coll only by the constructors, used to initialise the {@link PanelMemberDetails}.
     *
     * @param memberId
     *          int - The membership number of the {@link Member}
     */
    private void construct(int memberId) {
        int col = 10;
        
        id = new IdSpinner();
        id.setValue(memberId);
        id.addChangeListener(e -> updateId());
        
        name = new JTextField(col);
        firstName = new JTextField(col);
        
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
        bGender.addActionListener(arg0 -> {
            gender = !gender;
            updateGender();
        });
        studies = new JTextField(col);
        col = 30;
        mailStreet = new JTextField(col);
        mailPostalCode = new JTextField(col);
        mailTown = new JTextField(col);
        eMail = new JTextField(col);
        newsLetter = new JCheckBox("newsLetter");
        tel = new JTextField();
        
        edit = new JButton("�diter");
        edit.addActionListener(e -> {
            edition = !edition;
            update();
        });
        editValid = new JButton("Valider");
        editValid.addActionListener(e -> {
            editProfile();
            edition = false;
            update();
        });
        
        // TODO test BorderLayout bl = new BorderLayout();
        JPanel center = new JPanel(new BorderLayout());
        JPanel details = new JPanel(new BorderLayout());
        JPanel detailsCenter = new JPanel(new GridLayout(6, 4));
        JPanel detailsEdit = new JPanel();
        
        this.setLayout(new BorderLayout());
        this.add(center, BorderLayout.CENTER);
        this.add(details, BorderLayout.NORTH);
        
        details.add(detailsCenter, BorderLayout.CENTER);
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
        detailsCenter.add(firstName);
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
        detailsCenter.add(new JLabel("Fili�re : "));
        detailsCenter.add(studies);
        detailsCenter.add(new JLabel("Numero de Tel : "));
        detailsCenter.add(tel);
        
        detailsEdit.add(editValid);
        detailsEdit.add(edit);
        
        update();
    }
    
    /**
     * Change the {@link Member} visualised by the member corresponding to the new selected membership number.
     */
    private void updateId() {
        edition = false;
        int newId = id.getValue();
        // TODO display member's historic
        // tableModel.setId(newId);
        // TODO Test if correct (KaiceModel.getMemberCollection().getMember(newId) could be null)
        gender = !KaiceModel.getMemberCollection().isIdUsed(newId) || KaiceModel.getMemberCollection().getMember(newId).isMale();
        update();
    }
    
    /**
     * Save the change of the {@link Member}'s details.
     */
    private void editProfile() {
        MemberCollection col = KaiceModel.getMemberCollection();
        Member u = col.getMember(id.getValue());
        boolean newMember = false;
        if (u == null) {
            newMember = true;
            u = new Member(id.getValue());
        }
        u.setName(name.getText());
        u.setFirstName(firstName.getText());
        u.setBirthDate(birthDate.getDate());
        u.setGender(gender);
        u.setStudies(studies.getText());
        u.setMailStreet(mailStreet.getText());
        u.setMailPostalCode(mailPostalCode.getText());
        u.setMailTown(mailTown.getText());
        u.setEMail(eMail.getText());
        u.setNewsLetter(newsLetter.isSelected());
        u.setPhoneNumber(tel.getText());
        if (newMember) {
            col.addMember(u);
            int res = JOptionPane.showConfirmDialog(null,
                    "Le payment a-t-il �t� effectu� en luiquide ?", "Payement",
                    JOptionPane.YES_NO_OPTION, 2);
            int paid = 0;
            if (res == JOptionPane.YES_OPTION) {
                // TODO ajouter la possibilit� de changer le prix de l'inscription
                paid = 500;
            }
            Transaction tran = new Transaction(id.getValue(), transactionType.ENR, 500, paid, new Date());
            ArchivedProduct archProd = new ArchivedProduct("Inscription", 1, 5, -1);
            tran.addArchivedProduct(archProd);
            KaiceModel.getHistoric().addTransaction(tran);
        } else {
            col.updateDisplayList();
            col.serialize();
        }
    }
    
    /**
     * Update the display label of the gender.
     */
    private void updateGender() {
        if (gender) {
            lGender.setText("Homme");
            bGender.setText("Homme");
        } else {
            lGender.setText("Femme");
            bGender.setText("Femme");
        }
        
    }
    
    /**
     * Refresh all the panel. Use this method when the membership number or the edition mode change.
     */
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
                edit.setText("?diter");
            }
        }
        editValid.setVisible(edition);
        
        name.setEditable(edition);
        firstName.setEditable(edition);
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
            firstName.setText(m.getFirstName());
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
            firstName.setText("...");
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
