package fr.kaice.view.window;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.CloseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static java.awt.BorderLayout.*;

/**
 * Created by merkling on 27/08/16.
 */
public class WindowAskAdmin extends JDialog implements Observer {
    
    private final JTextField memberName;
    private final JTextField memberFirstName;
    private final IdSpinner memberId;
    private final JButton accept;
    
    private WindowAskAdmin(ActionListener listener) {
        super((JFrame) null, "Identification", true);
        
        this.setResizable(true);
        KaiceModel.getInstance().addObserver(this);
        
        JPanel center = new JPanel();
        JPanel ctrl = new JPanel();
        
        memberName = new JTextField();
        memberName.setColumns(10);
        memberName.addActionListener(e -> selectAdmin());
        memberFirstName = new JTextField();
        memberFirstName.setColumns(10);
        memberFirstName.addActionListener(e -> selectAdmin());
        memberId = new IdSpinner();
        Dimension dimId = memberFirstName.getPreferredSize();
        dimId.setSize(80, dimId.getHeight());
        memberId.setPreferredSize(dimId);
        memberId.addChangeListener(e -> KaiceModel.getMemberCollection().setSelectedAdminById(memberId.getValue()));
        
        JLabel name = new JLabel("Nom : ");
        JLabel firstName = new JLabel("Prénom : ");
        JLabel id = new JLabel("Numero de membre : ");
        
        accept = new JButton("Valider");
        accept.addActionListener(listener);
        accept.addActionListener(new CloseListener(this));
        accept.addActionListener(e -> removeObserver());
        accept.setEnabled(false);
        JButton cancel = new JButton("Annuler");
        cancel.addActionListener(new CloseListener(this));
        cancel.addActionListener(e -> removeObserver());
        
        GroupLayout groupLayout = new GroupLayout(center);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(name).addComponent(memberName));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(firstName).addComponent(memberFirstName));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(id).addComponent(memberId));
        
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(name).addComponent(firstName).addComponent(id));
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(memberName).addComponent(memberFirstName).addComponent(memberId));
        
        groupLayout.setVerticalGroup(vGroup);
        groupLayout.setHorizontalGroup(hGroup);
        
        this.setLayout(new BorderLayout());
        center.setLayout(groupLayout);
        
        this.add(center, CENTER);
        this.add(ctrl, SOUTH);
        
        ctrl.add(accept);
        ctrl.add(cancel);
        
        pack();
        int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2);
        int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
                - (this.getSize().getHeight() / 2));
        this.setLocation(x, y);
        setVisible(true);
    }
    
    public static void generate(ActionListener listener) {
        if (KaiceModel.getMemberCollection().isAdminSet()) {
            new WindowAskAdmin(listener);
        } else {
            listener.actionPerformed(null);
        }
    }
    
    private void removeObserver() {
        KaiceModel.getInstance().deleteObserver(this);
    }

    public void selectAdmin() {
        KaiceModel.getMemberCollection().setSelectedAdminByName(memberName.getText(), memberFirstName.getText());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.ADMIN)) {
            Member mem = KaiceModel.getMemberCollection().getSelectedAdmin();
            if (mem == null) {
                memberName.setText("");
                memberFirstName.setText("");
                memberId.setValue(0);
                accept.setEnabled(false);
            } else {
                memberName.setText(mem.getName());
                memberFirstName.setText(mem.getFirstName());
                memberId.setValue(mem.getMemberId());
                accept.setEnabled(true);
            }
        }
    }
}

