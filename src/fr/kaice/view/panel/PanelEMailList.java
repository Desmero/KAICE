package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.MemberCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This panel display a list of e-Mail address. The list contains only correct address, and only of
 * {@linkplain fr.kaice.model.member.Member Member} who subscribe to the newsletter (using the method
 * {@link MemberCollection#getEMailList()}).
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see fr.kaice.model.member.Member
 * @see MemberCollection
 */
public class PanelEMailList extends JPanel implements Observer{
    
    private static final long serialVersionUID = 1L;
    private final JTextArea emails;
    
    /**
     * Create a new {@link PanelEMailList}.
     */
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
