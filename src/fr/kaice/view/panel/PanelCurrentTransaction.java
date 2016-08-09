package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.membre.Member;
import fr.kaice.model.sell.CurrentTransaction;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.tools.IdSpinner;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This panel display the {@link CurrentTransaction} and the {@link PanelChoosSoldProduct}.
 * The interface allow to add or remove a {@link SoldProduct} from the current transaction, set a client {@link Member},
 * and valid or cancel the transaction.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see SoldProduct
 * @see Member
 * @see CurrentTransaction
 * @see PanelChoosSoldProduct
 */
public class PanelCurrentTransaction extends JPanel implements Observer {
    
    private DTablePanel currentTran;
    private DMonetarySpinner cash;
    private JLabel cashBack;
    private JLabel cashBackText;
    private JLabel total;
    private JTextField memberName;
    private JTextField memberFirstName;
    private IdSpinner memberId;
    
    /**
     * Create a new {@link PanelCurrentTransaction}.
     */
    public PanelCurrentTransaction() {
        KaiceModel.getInstance().addObserver(this);
        currentTran = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getCurrentTransaction(), 10);
        PanelChoosSoldProduct product = new PanelChoosSoldProduct();
        cash = new DMonetarySpinner(0.1);
        cash.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                KaiceModel.update();
            }
        });
        cashBack = new JLabel();
        cashBackText = new JLabel("Rendu : ");
        total = new JLabel("Total : 0.00?");
        total.setBorder(new LineBorder(Color.RED));
        total.setFont(new Font(total.getFont().getFontName(), Font.BOLD, 20));
        
        memberName = new JTextField();
        memberName.setColumns(10);
        memberName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMember();
            }
        });
        memberFirstName = new JTextField();
        memberFirstName.setColumns(10);
        memberFirstName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMember();
            }
        });
        memberId = new IdSpinner();
        Dimension dimId = memberFirstName.getPreferredSize();
        dimId.setSize(80, dimId.getHeight());
        memberId.setPreferredSize(dimId);
        memberId.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                KaiceModel.getMemberCollection().setSelectedMemberById(memberId.getValue());
            }
        });
        
        JButton add = new JButton("Ajouter");
        add.setIcon(new ImageIcon("icon/downArrow.png"));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.addSelection();
            }
        });
        JButton rem = new JButton("Retirer");
        rem.setIcon(new ImageIcon("icon/upArrow.png"));
        rem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeProduct();
                KaiceModel.update();
            }
        });
        JButton valide = new JButton("Valider");
        valide.setIcon(new ImageIcon("icon/valid.png"));
        valide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valid();
                reset();
            }
        });
        JButton cancel = new JButton("Annuler");
        cancel.setIcon(new ImageIcon("icon/cancel.png"));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        
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
        
        ctrlButton.add(valide);
        ctrlButton.add(cancel);
        ctrlButton.add(add);
        ctrlButton.add(rem);
        
        ctrlMember.add(new JLabel("Pr?nom : "));
        ctrlMember.add(memberFirstName);
        ctrlMember.add(new JLabel("Nom : "));
        ctrlMember.add(memberName);
        ctrlMember.add(new JLabel("Id : "));
        ctrlMember.add(memberId);
        
        ctrlPrice.add(new JLabel("Espece : "));
        ctrlPrice.add(cash);
        ctrlPrice.add(cashBackText);
        ctrlPrice.add(cashBack);
        ctrlPrice.add(total);
        
        update(null, null);
    }
    
    /**
     * Try to set a {@link Member} as the current client. The method success if there are no ambiguity.
     */
    private void selectMember() {
        KaiceModel.getMemberCollection().setSelectedMemberByName(memberName.getText(), memberFirstName.getText());
    }
    
    /**
     * Remove all the selected {@link SoldProduct} from the {@link CurrentTransaction}.
     */
    private void removeProduct() {
        CurrentTransaction tran = KaiceModel.getCurrentTransaction();
        int lastRow = tran.getRowCount() - 1;
        ArrayList<SoldProduct> items = tran.getAllProduct();
        int[] val = currentTran.getSelectedRows();
        for (int i = 0; i < val.length; i++) {
            if (val[i] != lastRow) {
                KaiceModel.getCurrentTransaction().removeSoldProduct(items.get(val[i]));
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
        int surp = cash.getIntValue() - price;
        // TODO membre non enregistrée
        if (res == JOptionPane.YES_OPTION && surp < 0) {
            res = JOptionPane.showConfirmDialog(null,
                    "Le payment en espece est inssufisant, voulez-vous d?biter le compte ?", "Espece insuffisant",
                    JOptionPane.YES_NO_OPTION, 2);
            if (res == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Pensez ? d?biter le compte dans le carnet de comptes, merci",
                        "Comptes", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (res == JOptionPane.YES_OPTION) {
            int cashIn = Integer.min(price, cash.getIntValue());
            tran.validTransaction(cashIn);
        }
    }
    
    /**
     * Reset the {@link CurrentTransaction} and the selections ({@link Member} and {@link SoldProduct}).
     */
    private void reset() {
        cash.setValue(0.);
        KaiceModel.getCurrentTransaction().reset();
        KaiceModel.getMemberCollection().setSelectedMember(null);
        KaiceModel.update();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        CurrentTransaction tran = KaiceModel.getCurrentTransaction();
        int price = tran.getPrice();
        int surp = cash.getIntValue() - price;
        
        total.setText("Total : " + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(price)) + " ?");
        if (surp > 0) {
            cashBack.setText("" + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(surp)) + " ?");
            cashBack.setForeground(Color.BLACK);
            cashBackText.setForeground(Color.BLACK);
        } else {
            surp = 0;
            cashBack.setText("" + DFormat.MONEY_FORMAT.format(DMonetarySpinner.intToDouble(surp)) + " ?");
            cashBack.setForeground(Color.LIGHT_GRAY);
            cashBackText.setForeground(Color.LIGHT_GRAY);
        }
        Member mem = KaiceModel.getMemberCollection().getSelectedMember();
        if (mem == null) {
            memberName.setText("");
            memberFirstName.setText("");
            memberId.setValue(0);
        } else {
            memberName.setText(mem.getName());
            memberFirstName.setText(mem.getFirstName());
            memberId.setValue(mem.getUserId());
            
        }
    }
    
}
