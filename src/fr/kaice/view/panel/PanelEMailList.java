package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.MemberCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

/**
 * This panel display a list of e-Mail address. The list contains only correct address, and only of
 * {@linkplain fr.kaice.model.member.Member Member} who subscribe to the newsletter (using the method
 * {@link MemberCollection#getEMailList(boolean)}).
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see fr.kaice.model.member.Member
 * @see MemberCollection
 */
public class PanelEMailList extends JPanel implements Observer {
    
    private static final long serialVersionUID = 1L;
    private final JTextArea emails;
    private final JCheckBox newsLetterOnly;
    
    /**
     * Create a new {@link PanelEMailList}.
     */
    public PanelEMailList() {
        KaiceModel.getInstance().addObserver(this);
        
        emails = new JTextArea();
        emails.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(emails);
        PanelTitle title = new PanelTitle("Liste des addresses e-mails", e -> KaiceModel.getInstance().setDetails(new
                JPanel()));
        
        JPanel ctrl = new JPanel();
        
        this.setLayout(new BorderLayout());
        this.add(title, NORTH);
        this.add(scrollPane, CENTER);
        this.add(ctrl, SOUTH);
        
        newsLetterOnly = new JCheckBox("Afficher seulement les adresses des abonnés", true);
        newsLetterOnly.addActionListener(e -> KaiceModel.update(KaiceModel.DETAILS));
        ctrl.add(newsLetterOnly);
    }
    
    @Override
    public void update(Observable arg0, Object arg1) {
        if (KaiceModel.isPartModified(KaiceModel.MEMBER, KaiceModel.DETAILS)) {
            emails.setText(KaiceModel.getMemberCollection().getEMailList(newsLetterOnly.isSelected()));
        }
    }
}
