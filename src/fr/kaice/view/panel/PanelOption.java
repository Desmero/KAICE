package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.Transaction;
import fr.kaice.tools.generic.DMonetarySpinner;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

/**
 * Created by merkling on 28/08/16.
 */
public class PanelOption extends JPanel {
    
    public PanelOption() {
    
        JPanel divers = new JPanel();
    
        JCheckBox fullName = new JCheckBox("Ajouter les types", KaiceModel.getHistoric().isDisplayTypeNames());
        fullName.addActionListener(e -> {
            KaiceModel.getHistoric().changeDisplayTypeNames();
            updateHistoric();
        });
        JCheckBox admin = new JCheckBox("Afficher les caissiers", true);
        admin.addActionListener(e -> {
            KaiceModel.getHistoric().changeDisplayAdmin();
            updateHistoric();
        });
        JCheckBox hidden = new JCheckBox("Afficher les produits cachés", KaiceModel.getInstance().isShowHidden());
        hidden.addActionListener(e -> KaiceModel.getInstance().changeShowHiddenState());
        DMonetarySpinner enrPrice = new DMonetarySpinner(0.5);
        enrPrice.setEnabled(false);
        
        GroupLayout groupLayout = new GroupLayout(divers);
    
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
    
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(fullName).addComponent(admin).addComponent
                (hidden).addComponent(enrPrice));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(fullName));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(admin));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(hidden));
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(enrPrice));
    
        groupLayout.setHorizontalGroup(hGroup);
        groupLayout.setVerticalGroup(vGroup);
    
        divers.setLayout(groupLayout);
        
        
        
        final JPanel hist = new JPanel();
        Transaction.transactionType[] list = Transaction.transactionType.values();
        int col = 2;
        int line = (list.length + col -1) / col;
        hist.setLayout(new GridLayout(line, col));

        for (Transaction.transactionType type : list) {
            JCheckBox checkBox = new JCheckBox(type.getTitle(), type.isDisplay());
            checkBox.setBackground(type.getColor());
            checkBox.addActionListener(e -> {
                type.changeDisplay();
                KaiceModel.getInstance().getHistoric().updateDisplayList();
                updateHistoric();
            });
            hist.add(checkBox);
        }
    
    
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Divers", divers);
        tabbedPane.add("Historique", hist);
    
        this.setLayout(new BorderLayout());
        this.add(new PanelTitle("Options", e -> KaiceModel.getInstance().setDetails(new JPanel())), NORTH);
        this.add(tabbedPane, CENTER);
        
        
    }
    
    private void updateHistoric() {
        KaiceModel.update(KaiceModel.HISTORIC);
    }
}
