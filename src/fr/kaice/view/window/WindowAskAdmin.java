package fr.kaice.view.window;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.CloseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import static fr.kaice.tools.local.French.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

/**
 * Created by merkling on 27/08/16.
 */
public class WindowAskAdmin extends JDialog implements Observer {
    
    private final JTextField memberName;
    private final JTextField memberFirstName;
    private final IdSpinner memberId;
    private final JPasswordField password;
    private final JButton accept;
    private ActionListener listener;
    private final Set<Character> pressed = new HashSet<>();
    private String sName;
    private String sFirstName;
    
    private WindowAskAdmin(ActionListener listener) {
        super((JFrame) null, WIN_TITLE_AUTHENTICATION, true);
        
        this.listener = listener;
        this.setResizable(true);
        KaiceModel.getInstance().addObserver(this);
        
        JPanel center = new JPanel();
        JPanel ctrl = new JPanel();
        
        sName = "";
        sFirstName = "";
    
        memberName = new JTextField();
        memberName.setColumns(10);
        memberName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
        
            }
            @Override
            public void keyPressed(KeyEvent e) {
                pressed.add(e.getKeyChar());
                if (pressed.size() == 1) {
                    memberName.setText(sName);
                    memberFirstName.setText(sFirstName);
                }
            }
    
            @Override
            public void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyChar());
                if (pressed.size() == 0) {
                    sName = memberName.getText();
                    selectAdmin();
                }
            }
        });
        memberName.addActionListener(e -> selectPassField());
        memberFirstName = new JTextField();
        memberFirstName.setColumns(10);
        memberFirstName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
        
            }
            @Override
            public void keyPressed(KeyEvent e) {
                pressed.add(e.getKeyChar());
                if (pressed.size() == 1) {
                    memberName.setText(sName);
                    memberFirstName.setText(sFirstName);
                }
            }
    
            @Override
            public void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyChar());
                if (pressed.size() == 0) {
                    sFirstName = memberFirstName.getText();
                    selectAdmin();
                }
            }
        });
        memberFirstName.addActionListener(e -> selectPassField());
        memberId = new IdSpinner();
        Dimension dimId = memberFirstName.getPreferredSize();
        dimId.setSize(80, dimId.getHeight());
        memberId.setPreferredSize(dimId);
        memberId.addChangeListener(e -> selectAdminId());
        password = new JPasswordField();
        password.addActionListener(e -> valid(e));
        
        JLabel name = new JLabel(TF_NAME);
        JLabel firstName = new JLabel(TF_FIRST_NAME);
        JLabel id = new JLabel(TF_MEMBERSHIP_NUM);
        JLabel pass = new JLabel(TF_PASSWORD);
        
        accept = new JButton(B_VALID);
        accept.addActionListener(e -> valid(e));
        accept.setEnabled(false);
        JButton cancel = new JButton(B_CANCEL);
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
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(pass).addComponent(password));
        
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(name).addComponent(firstName).addComponent(id)
                .addComponent(pass));
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(memberName).addComponent(memberFirstName)
                .addComponent(memberId).addComponent(password));
        
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

    private void selectAdmin() {
        if (sName.equals("") && sFirstName.equals("")) {
            memberId.setValue(0);
        } else {
            Member admin = KaiceModel.getMemberCollection().getMemberByName(memberName.getText(), memberFirstName.getText());
            if (admin != null) {
                memberId.setValue(0);
                memberId.setValue(admin.getMemberId());
            }
        }
    }
    
    private void selectAdminId() {
        KaiceModel.getMemberCollection().setSelectedAdminById(memberId.getValue());
    }
    
    private void selectPassField() {
        password.requestFocus();
        selectAdmin();
    }
    
    private void valid(ActionEvent e) {
        selectAdminId();
        String pass = new String(password.getPassword());
        if (pass.equals("")) {
            pass = "0";
        }
        if (KaiceModel.getMemberCollection().isAdminSelected() && KaiceModel.getMemberCollection().getSelectedAdmin()
                .checkPassWord(pass)) {
            listener.actionPerformed(e);
            removeObserver();
            this.dispose();
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.ADMIN)) {
            Member mem = KaiceModel.getMemberCollection().getSelectedAdmin();
            if (mem == null) {
                memberName.setText(sName);
                memberFirstName.setText(sFirstName);
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

