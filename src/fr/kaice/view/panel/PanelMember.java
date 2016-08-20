package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This panel display all {@linkplain fr.kaice.model.member.Member Member} contains in the
 * {@linkplain fr.kaice.model.member.MemberCollection MemberCollection} known by {@link KaiceModel}.
 * The interface allow to visualise or create a new member.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see fr.kaice.model.member.MemberCollection
 * @see fr.kaice.model.member.Member
 */
public class PanelMember extends JPanel {
    
    /**
     * Create a new {@link PanelMember}.
     */
    public PanelMember() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getMemberCollection());
        JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
        JPanel ctrl = new JPanel();
        JPanel select = new JPanel(new BorderLayout());
        JPanel search = new JPanel(new GridLayout(3, 2));
        JTextField name = new JTextField(15);
        JTextField firstName = new JTextField(15);
        JSpinner year = new JSpinner(new SpinnerNumberModel(KaiceModel.getActualYear()+2000, 2014, KaiceModel.getActualYear()+2000, 1));

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
        
        table.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isControlDown()) {
                    int row = table.getSelectedRow();
                    KaiceModel.getMemberCollection().setSelectedMember(row);
                } else if (e.getClickCount() == 2) {
                    int memberId = KaiceModel.getMemberCollection().getMemberIdAtRow(table.getSelectedRow());
                    KaiceModel.getInstance().setDetails(new PanelMemberDetails(memberId));
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
        select.setBorder(BorderFactory.createTitledBorder("Recherche"));
        
        search.add(new JLabel("Nom :"));
        search.add(name);
        search.add(new JLabel("Prénom :"));
        search.add(firstName);
        search.add(new JLabel("Année :"));
        search.add(year);

        ctrl.add(add);
        ctrl.add(view);
    }
}
