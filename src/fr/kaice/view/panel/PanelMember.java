package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.kaice.tools.local.French.*;

/**
 * This panel display all {@linkplain fr.kaice.model.member.Member Member} contains in the
 * {@linkplain fr.kaice.model.member.MemberCollection MemberCollection} known by {@link KaiceModel}.
 * The interface allow to visualise or create a new member.
 *
 * @author Rapha�l Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see fr.kaice.model.member.MemberCollection
 * @see fr.kaice.model.member.Member
 */
public class PanelMember extends JPanel {
    
    private PanelEMailList eMailList;
    
    /**
     * Create a new {@link PanelMember}.
     */
    public PanelMember() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getMemberCollection());
        JButton add = new JButton(B_ADD), view = new JButton(B_VIEW), eMail = new JButton(B_E_MAIL);
        JPanel ctrl = new JPanel();
        JPanel select = new JPanel(new BorderLayout());
        JPanel search = new JPanel(new GridLayout(3, 2));
        JTextField name = new JTextField(15);
        JTextField firstName = new JTextField(15);
        JSpinner year = new JSpinner(new SpinnerNumberModel(KaiceModel.getActualYear()+2000, 2015, KaiceModel
                .getActualYear()+2000, 1));

        add.addActionListener(e -> {
            int memberId = KaiceModel.getMemberCollection().getNewId();
            KaiceModel.getInstance().setDetails(new PanelMemberDetails(memberId, true));
        });
        view.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                int memberId = KaiceModel.getMemberCollection().getMemberIdAtRow(table.getSelectedRow());
                KaiceModel.getInstance().setDetails(new PanelMemberDetails(memberId));
            }
        });
        eMail.addActionListener(e -> KaiceModel.getInstance().setDetails(getEMailList()));
        
        table.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int memberId = KaiceModel.getMemberCollection().getMemberIdAtRow(table.getSelectedRow());
                    KaiceModel.getInstance().setDetails(new PanelMemberDetails(memberId));
                } else if (e.isControlDown()) {
                    int row = table.getTable().rowAtPoint(e.getPoint());
                    KaiceModel.getMemberCollection().setSelectedMember(row);
                }
            }
        });
        table.getTable().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.getTable().columnAtPoint(e.getPoint());
                String name = table.getTable().getColumnName(col);
                KaiceModel.getMemberCollection().setSortColl(name);
            }
        });
        
        name.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                KaiceModel.getMemberCollection().setSearchName(name.getText());
            }
        });
        
        firstName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                KaiceModel.getMemberCollection().setSearchFirstName(firstName.getText());
            }
        });

        year.addChangeListener(e -> KaiceModel.getMemberCollection().setDisplayYear((int) year.getValue() - 2000));

        table.setWidth(300);
        table.setMultiSelection(false);
    
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        this.add(select, BorderLayout.NORTH);
        this.add(ctrl, BorderLayout.SOUTH);
        
        select.add(search, BorderLayout.CENTER);
        select.setBorder(BorderFactory.createTitledBorder(SUB_TITLE_SEARCH));
        
        search.add(new JLabel(TF_NAME));
        search.add(name);
        search.add(new JLabel(TF_FIRST_NAME));
        search.add(firstName);
        search.add(new JLabel(TF_YEAR));
        search.add(year);

        ctrl.add(add);
        ctrl.add(view);
        ctrl.add(eMail);
    }
    
    private PanelEMailList getEMailList() {
        if (eMailList == null) {
            eMailList = new PanelEMailList();
        }
        return eMailList;
    }
}
