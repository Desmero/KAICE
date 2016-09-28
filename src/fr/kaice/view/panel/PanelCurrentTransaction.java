package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.member.Member;
import fr.kaice.model.sell.CurrentTransaction;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DColor;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import static fr.kaice.tools.local.French.*;

/**
 * This panel display the {@link CurrentTransaction} and the {@link PanelChoseSoldProduct}.
 * The interface allow to add or remove a {@link SoldProduct} from the current transaction, set a client {@link Member},
 * and valid or cancel the transaction.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see SoldProduct
 * @see Member
 * @see CurrentTransaction
 * @see PanelChoseSoldProduct
 */
public class PanelCurrentTransaction extends JPanel implements Observer {
    
    private final DTablePanel currentTran;
    private final DMonetarySpinner cash;
    private final JLabel cashBack;
    private final JLabel cashBackText;
    private final JLabel total;
    private final JTextField memberName;
    private final JTextField memberFirstName;
    private final IdSpinner memberId;
    private final JButton valid;
    private final Set<Character> pressed = new HashSet<>();
    private String sName;
    private String sFirstName;
    
    /**
     * Create a new {@link PanelCurrentTransaction}.
     */
    public PanelCurrentTransaction() {
        KaiceModel.getInstance().addObserver(this);
        currentTran = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getCurrentTransaction(), 10);
        PanelChoseSoldProduct product = new PanelChoseSoldProduct();
        cash = new DMonetarySpinner(0.1);
        cash.addChangeListener(e -> KaiceModel.update(KaiceModel.TRANSACTION));
        cashBack = new JLabel();
        cashBackText = new JLabel(TF_CASH_BACK);
        total = new JLabel(TF_TOTAL + "0.00 " + DFormat.EURO);
        total.setBorder(new LineBorder(Color.RED));
        total.setFont(new Font(total.getFont().getFontName(), Font.BOLD, 20));
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
                    selectMember();
                }
            }
        });
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
                    selectMember();
                }
            }
        });
        memberId = new IdSpinner();
        Dimension dimId = memberFirstName.getPreferredSize();
        dimId.setSize(80, dimId.getHeight());
        memberId.setPreferredSize(dimId);
        memberId.addChangeListener(e -> selectMemberId());
        
        JButton add = new JButton(B_ADD);
        add.setIcon(new ImageIcon(getClass().getResource("/fr/kaice/images/downArrow.png")));
        add.addActionListener(e -> product.addSelection());
        JButton rem = new JButton(B_REM);
        rem.setIcon(new ImageIcon(getClass().getResource("/fr/kaice/images/upArrow.png")));
        rem.addActionListener(e -> {
            removeProduct();
            KaiceModel.update(KaiceModel.TRANSACTION);
        });
        valid = new JButton(B_VALID);
        valid.setIcon(new ImageIcon(getClass().getResource("/fr/kaice/images/valid.png")));
        valid.addActionListener(e -> WindowAskAdmin.generate(e2 -> valid()));
        JButton cancel = new JButton(B_CANCEL);
        cancel.setIcon(new ImageIcon(getClass().getResource("/fr/kaice/images/cancel.png")));
        cancel.addActionListener(e -> reset());
        JButton clearMember = new JButton();
        clearMember.setBackground(DColor.BLUE_SELECTION);
        clearMember.setIcon(new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                int w = getIconWidth(), h = getIconHeight();
                g.setColor(DColor.RED);
                g.fillRect(x, y, w, h);
            }
        
            @Override
            public int getIconWidth() {
                return 25;
            }
        
            @Override
            public int getIconHeight() {
                return 25;
            }
        });
        clearMember.setPreferredSize(new Dimension(15, 15));
        clearMember.addActionListener(e -> clearMember());
    
        JPanel tran = new JPanel(new BorderLayout());
        JPanel ctrl = new JPanel(new BorderLayout());
        
        this.setLayout(new BorderLayout());
        this.add(product, BorderLayout.CENTER);
        this.add(tran, BorderLayout.SOUTH);
        
        tran.add(ctrl, BorderLayout.NORTH);
        tran.add(currentTran, BorderLayout.CENTER);
        
        JPanel ctrlButton = new JPanel();
        JPanel ctrlMember = new JPanel();
        JPanel ctrlPrice = new JPanel();
        JPanel ctrlSeparator = new JPanel(new BorderLayout());
        
        ctrl.add(ctrlButton, BorderLayout.NORTH);
        ctrl.add(ctrlSeparator, BorderLayout.CENTER);
        ctrl.add(ctrlPrice, BorderLayout.SOUTH);
        
        ctrlSeparator.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
        ctrlSeparator.add(ctrlMember, BorderLayout.CENTER);
        ctrlSeparator.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
        
        ctrlButton.add(valid);
        ctrlButton.add(cancel);
        ctrlButton.add(add);
        ctrlButton.add(rem);
    
        ctrlMember.add(new JLabel(TF_NAME));
        ctrlMember.add(memberName);
        ctrlMember.add(new JLabel(TF_FIRST_NAME));
        ctrlMember.add(memberFirstName);
        ctrlMember.add(new JLabel(TF_MEMBERSHIP_NUM));
        ctrlMember.add(memberId);
        ctrlMember.add(clearMember);
        
        ctrlPrice.add(new JLabel(TF_CASH));
        ctrlPrice.add(cash);
        ctrlPrice.add(cashBackText);
        ctrlPrice.add(cashBack);
        ctrlPrice.add(total);
        
        update(null, null);
    }
    
    private void selectMember() {
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
    
    private void clearMember() {
        sName = "";
        sFirstName = "";
        KaiceModel.getMemberCollection().setSelectedMemberById(0);
    }
    
    private void selectMemberId() {
        KaiceModel.getMemberCollection().setSelectedMemberById(memberId.getValue());
    }
    
    /**
     * Remove all the selected {@link SoldProduct} from the {@link CurrentTransaction}.
     */
    private void removeProduct() {
        CurrentTransaction tran = KaiceModel.getCurrentTransaction();
        int lastRow = tran.getRowCount() - 1;
        ArrayList<SoldProduct> items = tran.getAllProduct();
        int[] val = currentTran.getSelectedRows();
        for (int aVal : val) {
            if (aVal != lastRow) {
                KaiceModel.getCurrentTransaction().removeSoldProduct(items.get(aVal));
            }
        }
    }
    
    /**
     * Valid the {@link CurrentTransaction}, reset it and the selections ({@link Member} and {@link SoldProduct}).
     */
    private void valid() {
        CurrentTransaction tran = KaiceModel.getCurrentTransaction();
        int res = JOptionPane.YES_OPTION;
        int price = tran.getPrice();
        int add = cash.getIntValue() - price;
        if (res == JOptionPane.YES_OPTION && add < 0) {
            res = JOptionPane.showConfirmDialog(null, DIALOG_TEXT_INSUFFICIENT_CASH, DIALOG_NAME_INSUFFICIENT_CASH,
                    JOptionPane.YES_NO_OPTION, 2);
            if (res == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, DIALOG_TEXT_ACCOUNT, DIALOG_NAME_ACCOUNT, JOptionPane
                        .WARNING_MESSAGE);
            } else {
                return;
            }
        }
        if (res == JOptionPane.YES_OPTION) {
            int cashIn = Integer.min(price, cash.getIntValue());
            tran.validTransaction(cashIn);
            reset();
        }
    }
    
    /**
     * Reset the {@link CurrentTransaction} and the selections ({@link Member} and {@link SoldProduct}).
     */
    private void reset() {
        cash.setValue(0.);
        sName = "";
        sFirstName = "";
        KaiceModel.getCurrentTransaction().reset();
        KaiceModel.getMemberCollection().clearSelectedMember();
        KaiceModel.update(KaiceModel.TRANSACTION);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.TRANSACTION)) {
            CurrentTransaction tran = KaiceModel.getCurrentTransaction();
            int price = tran.getPrice();
            int add = cash.getIntValue() - price;
    
            total.setText("Total : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(price)) + " " + DFormat.EURO);
            if (add > 0) {
                cashBack.setText("" + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(add)) + " " + DFormat.EURO);
                cashBack.setForeground(Color.BLACK);
                cashBackText.setForeground(Color.BLACK);
            } else {
                add = 0;
                cashBack.setText("" + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(add)) + " " + DFormat.EURO);
                cashBack.setForeground(Color.LIGHT_GRAY);
                cashBackText.setForeground(Color.LIGHT_GRAY);
            }
            Member mem = KaiceModel.getMemberCollection().getSelectedMember();
            if (mem == null) {
                memberName.setText(sName);
                memberFirstName.setText(sFirstName);
                memberId.setValue(0);
                valid.setEnabled(false);
            } else {
                int nameCaret = sName.length() + mem.getName().toLowerCase().indexOf(sName.toLowerCase());
                memberName.setText(mem.getName());
                memberName.setCaretPosition(nameCaret);
                int firstNameCaret = sFirstName.length() + mem.getFirstName().toLowerCase().indexOf(sFirstName
                        .toLowerCase());
                memberFirstName.setText(mem.getFirstName());
                memberFirstName.setCaretPosition(firstNameCaret);
                memberId.setValue(mem.getMemberId());
                valid.setEnabled(true);
            }
            currentTran.setNumberRow(tran.getRowCount()+1);
        }
    }
}
