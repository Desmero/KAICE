package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DColor;

import javax.swing.*;
import java.awt.*;

import static fr.kaice.tools.local.French.*;
import static java.awt.BorderLayout.*;

/**
 * Created by merkling on 27/08/16.
 */
public class PanelAddAdmin extends JPanel {
    
    private final JTextField memberName;
    private final JTextField memberFirstName;
    private final IdSpinner memberId;
    private final JPasswordField passNumber;
    private Member member;
    private final JButton accept;
    private final JButton changeCode;
    
    public PanelAddAdmin() {
        member = null;
        PanelTitle title = new PanelTitle(TITLE_NEW_CASHIER, e -> KaiceModel.getInstance().setDetails(new JPanel()));
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
        memberId.addChangeListener(e -> {
            member = KaiceModel.getMemberCollection().getMember(memberId.getValue());
            update();
        });
        passNumber = new JPasswordField();
        
        JLabel name = new JLabel(TF_NAME);
        JLabel firstName = new JLabel(TF_FIRST_NAME);
        JLabel id = new JLabel(TF_MEMBERSHIP_NUM);
        JLabel pswd = new JLabel(TF_PASSWORD_REG);
        
        accept = new JButton(B_ADD_CASHIER);
        accept.addActionListener(e -> valid());
        accept.setEnabled(false);
        changeCode = new JButton(B_CHANGE_CASHIER_CODE);
        changeCode.addActionListener(e -> changeCode());
        changeCode.setEnabled(false);
        changeCode.setVisible(false);
        
        GroupLayout groupLayout = new GroupLayout(center);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(name).addComponent(memberName));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(firstName).addComponent(memberFirstName));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(id).addComponent(memberId));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(pswd).addComponent(passNumber));
        
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(name).addComponent(firstName).addComponent(id)
                .addComponent(pswd));
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(memberName).addComponent(memberFirstName)
                .addComponent(memberId).addComponent(passNumber));
        
        groupLayout.setVerticalGroup(vGroup);
        groupLayout.setHorizontalGroup(hGroup);
        
        this.setLayout(new BorderLayout());
        center.setLayout(groupLayout);
        
        this.add(title, NORTH);
        this.add(center, CENTER);
        this.add(ctrl, SOUTH);
        
        ctrl.add(accept);
        ctrl.add(changeCode);
        
    }
    
    private void valid() {
        boolean isAdmin = (member != null) && (member.isAdmin());
        if (isAdmin) {
            member.removeAdminRight();
            KaiceModel.getInstance().setDetails(new JPanel());
            KaiceModel.getMemberCollection().serialize();
        } else {
            changeCode();
        }
    }
    
    private void changeCode() {
        String pswd = new String(passNumber.getPassword());
        try {
            int pscd = Integer.parseInt(pswd);
            member.addAdminRight(pscd);
            KaiceModel.getInstance().setDetails(new JPanel());
            KaiceModel.getMemberCollection().serialize();
        } catch (NumberFormatException e) {
            passNumber.setBackground(DColor.RED);
        }
    }
    
    private void selectAdmin() {
        member = KaiceModel.getMemberCollection().getMemberByName(memberName.getText(), memberFirstName.getText());
        update();
    }
    
    private void update() {
        boolean isNotNull = member != null;
        if (isNotNull) {
            memberName.setText(member.getName());
            memberFirstName.setText(member.getFirstName());
            memberId.setValue(member.getMemberId());
            boolean isAdmin = member.isAdmin();
            if (isAdmin) {
                accept.setText(B_REM_CASHIER);
            } else {
                accept.setText(B_ADD_CASHIER);
            }
            changeCode.setEnabled(isAdmin);
            changeCode.setVisible(isAdmin);
        }
        accept.setEnabled(isNotNull);
    }
}
