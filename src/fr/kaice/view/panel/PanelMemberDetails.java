package fr.kaice.view.panel;

import com.toedter.calendar.JDateChooser;
import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.ArchivedProduct;
import fr.kaice.model.historic.PartialHistoric;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.Transaction.transactionType;
import fr.kaice.model.member.Member;
import fr.kaice.model.member.MemberCollection;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.tools.generic.FocusTextField;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * This panel display all information about a {@link Member}.
 * The interface allow to edit a member or create a new one.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see Member
 */
class PanelMemberDetails extends JPanel {

    private final PanelTitle title;
    private final JTabbedPane jTabbedPane;
    private final JPanel historic;
    private final IdSpinner id; // also call membership number
    private final JTextField name;
    private final JTextField firstName;
    private final JPanel pBirth;
    private final JLabel lBirthDate;
    private final JDateChooser birthDate;
    private final JPanel pGender;
    private final JLabel lGender;
    private final JButton bGender;
    private final JTextField studies;
    private final JTextField mailStreet;
    private final JTextField mailPostalCode;
    private final JTextField mailTown;
    private final JTextField eMail;
    private final JCheckBox newsLetter;
    private final JTextField phone;
    private final JButton edit;
    private final JButton editValid;
    private boolean gender;
    private boolean edition;

    /**
     * Create a new {@link PanelMemberDetails} in visualisation mode.
     *
     * @param memberId int - The membership number of the {@link Member}.
     */
    public PanelMemberDetails(int memberId) {
        this(memberId, false);
    }

    /**
     * Create a new {@link PanelMemberDetails}.
     *
     * @param memberId int - The membership number of the {@link Member}.
     * @param edition  boolean - True for edition mode, false for visualisation mode.
     */
    public PanelMemberDetails(int memberId, boolean edition) {
        this.edition = edition;
        int col = 10;

        id = new IdSpinner();
        id.setValue(memberId);
        id.addChangeListener(e -> updateId());

        name = new FocusTextField(col);
        firstName = new FocusTextField(col);

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
        studies = new FocusTextField(col);
        col = 35;
        mailStreet = new FocusTextField(col);
        mailPostalCode = new FocusTextField(col);
        mailTown = new FocusTextField(col);
        eMail = new FocusTextField(col);
        newsLetter = new JCheckBox("newsLetter");
        phone = new FocusTextField();

        edit = new JButton("Éditer");
        edit.addActionListener(e -> {
            this.edition = !this.edition;
            update();
        });
        editValid = new JButton("Valider");
        editValid.addActionListener(e -> WindowAskAdmin.generate(e2 -> {
            editProfile();
            this.edition = false;
            update();
        }));

        title = new PanelTitle("Détail membre", e -> KaiceModel.getInstance().setDetails(new JPanel()));
        JPanel allDetails = new JPanel(new BorderLayout());
        historic = new JPanel(new BorderLayout());

        jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Membre", allDetails);
        jTabbedPane.add("Historique", historic);

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(jTabbedPane, BorderLayout.CENTER);

        JPanel details = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new BorderLayout());
        JPanel centerEast = new JPanel();
        JPanel centerWest = new JPanel();
        center.add(centerEast, BorderLayout.CENTER);
        center.add(centerWest, BorderLayout.WEST);
        JPanel detailsEdit = new JPanel();

        allDetails.add(details, BorderLayout.NORTH);

        details.add(center, BorderLayout.CENTER);
        details.add(detailsEdit, BorderLayout.SOUTH);

        GroupLayout groupLayoutEast = new GroupLayout(centerEast);
        groupLayoutEast.setAutoCreateContainerGaps(true);
        GroupLayout groupLayoutWest = new GroupLayout(centerWest);
        groupLayoutWest.setAutoCreateContainerGaps(true);
    
        GroupLayout.SequentialGroup vGroupEast = groupLayoutEast.createSequentialGroup();
        GroupLayout.SequentialGroup hGroupEast = groupLayoutEast.createSequentialGroup();
        GroupLayout.SequentialGroup vGroupWest = groupLayoutEast.createSequentialGroup();
        GroupLayout.SequentialGroup hGroupWest = groupLayoutEast.createSequentialGroup();

        JLabel lId = new JLabel("Id : ");
        JLabel lMailStret = new JLabel("Adresse : ");
        JLabel lName = new JLabel("Nom : ");
        JLabel lMailPostalCode = new JLabel("    (Code postal)");
        JLabel lFirstName = new JLabel("Prenom : ");
        JLabel lMailTown = new JLabel("    (Commune)");
        JLabel lBirthDate = new JLabel("Date de naissance : ");
        JLabel lEMail = new JLabel("Adresse e-Mail : ");
        JLabel lGender = new JLabel("Sexe : ");
        JLabel lStudies = new JLabel("Filière : ");
        JLabel lPhone = new JLabel("Numero de Tel : ");
    
        vGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(lId).addComponent(id));
        vGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(lName).addComponent(name));
        vGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(lFirstName).addComponent(firstName));
        vGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(lBirthDate).addComponent(pBirth));
        vGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(lGender).addComponent(pGender));
        vGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(lStudies).addComponent(studies));
        vGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(lMailStret).addComponent(mailStreet));
        vGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(lMailPostalCode).addComponent
                (mailPostalCode));
        vGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(lMailTown).addComponent(mailTown));
        vGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(lEMail).addComponent(eMail));
        vGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(newsLetter));
        vGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(lPhone).addComponent(phone));

        hGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(lId).addComponent(lName).addComponent
                (lFirstName).addComponent(lBirthDate).addComponent(lGender).addComponent(lStudies));
        hGroupWest.addGroup(groupLayoutWest.createParallelGroup().addComponent(id).addComponent(name).addComponent
                (firstName).addComponent(pBirth).addComponent(pGender).addComponent(studies));
        hGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(lMailStret).addComponent
                (lMailPostalCode).addComponent(lMailTown).addComponent(lEMail).addComponent(lPhone));
        hGroupEast.addGroup(groupLayoutEast.createParallelGroup().addComponent(mailStreet).addComponent
                (mailPostalCode).addComponent(mailTown).addComponent(eMail).addComponent(newsLetter).addComponent
                (phone));

        groupLayoutEast.setVerticalGroup(vGroupEast);
        groupLayoutEast.setHorizontalGroup(hGroupEast);
        centerEast.setLayout(groupLayoutEast);
        groupLayoutWest.setVerticalGroup(vGroupWest);
        groupLayoutWest.setHorizontalGroup(hGroupWest);
        centerWest.setLayout(groupLayoutWest);

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
        gender = !KaiceModel.getMemberCollection().isIdUsed(newId) || KaiceModel.getMemberCollection().getMember(newId).isMale();
        update();
    }

    /**
     * Save the change of the {@link Member}'s details.
     */
    private void editProfile() {
        MemberCollection col = KaiceModel.getMemberCollection();
        int idValue = id.getValue();
        Member u = col.getMember(idValue);
        boolean newMember = false;
        if (u == null) {
            if (idValue / 10000 != KaiceModel.getActualYear()) {
                System.out.println(idValue % 10000);
                return;
            }
            newMember = true;
            u = new Member(idValue);
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
        u.setPhoneNumber(phone.getText());
        if (newMember) {
            col.addMember(u);
            int res = JOptionPane.showConfirmDialog(null,
                    "Le payment a-t-il été effectué en luiquide ?", "Payement",
                    JOptionPane.YES_NO_OPTION, 2);
            int cost = 500;
            int paid = 0;
            if (res == JOptionPane.YES_OPTION) {
                // TODO ajouter la possibilité de changer le prix de l'inscription
                paid = cost;
            }
            Transaction tran = new Transaction(u.getMemberId(), transactionType.ENR, cost, paid, new Date());
            ArchivedProduct archProd = new ArchivedProduct(u.getFullName(), 1, 5, -1);
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
        firstName.setEditable(edition);
        bGender.setEnabled(edition);
        studies.setEditable(edition);
        mailStreet.setEditable(edition);
        mailPostalCode.setEditable(edition);
        mailTown.setEditable(edition);
        eMail.setEditable(edition);
        newsLetter.setEnabled(edition);
        phone.setEditable(edition);

        if (m != null) {
            if (edition) {
                title.setTitle(m.getFullName() + " (édition)");
            } else {
                title.setTitle(m.getFullName());
            }
            jTabbedPane.setEnabledAt(1, true);
            historic.removeAll();
            historic.add(new DTablePanel(KaiceModel.getInstance(), new PartialHistoric(m)), BorderLayout.CENTER);
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
            phone.setText(m.getPhoneNumber());
        } else {
            if (edition) {
                title.setTitle("Nouveau membre");
            } else {
                title.setTitle("Personne");
            }
            jTabbedPane.setEnabledAt(1, false);
            name.setText("...");
            firstName.setText("...");
//            Calendar cal = Calendar.getInstance();
//            cal.set(1900, 0, 1);
            birthDate.setDate(new Date());
            lBirthDate.setText("../../....");
            lGender.setText("...");
            bGender.setText("...");
            studies.setText("...");
            mailStreet.setText("...");
            mailPostalCode.setText(".....");
            mailTown.setText("...");
            eMail.setText("...");
            newsLetter.setSelected(false);
            phone.setText(".. .. .. .. ..");
        }
        repaint();
    }
}
