package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;

import javax.swing.*;
import java.awt.*;

import static fr.kaice.model.historic.Transaction.transactionType.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

/**
 * Created by merkling on 28/08/16.
 */
public class PanelHistoricOption extends JPanel {
    
    private final JCheckBox fullName;
    private final JCheckBox admin;
    private final JCheckBox sell;
    private final JCheckBox buy;
    private final JCheckBox add;
    private final JCheckBox sub;
    private final JCheckBox cancel;
    private final JCheckBox enr;
    private final JCheckBox misc;
    
    public PanelHistoricOption() {
        fullName = new JCheckBox("Ajouter les types", KaiceModel.getHistoric().isDisplayTypeNames());
        fullName.addActionListener(e -> {
            KaiceModel.getHistoric().changeDisplayTypeNames();
            updateHistoric();
        });
        admin = new JCheckBox("Afficher les caissiers", true);
        admin.addActionListener(e -> {
            KaiceModel.getHistoric().changeDisplayAdmin();
            updateHistoric();
        });
        sell = new JCheckBox(SELL.getTitle(), SELL.isDisplay());
        sell.setBackground(SELL.getColor());
        sell.addActionListener(e -> {
            SELL.changeDisplay();
            KaiceModel.getInstance().getHistoric().updateDisplayList();
            updateHistoric();
        });
        buy = new JCheckBox(BUY.getTitle(), BUY.isDisplay());
        buy.setBackground(BUY.getColor());
        buy.addActionListener(e -> {
            BUY.changeDisplay();
            KaiceModel.getInstance().getHistoric().updateDisplayList();
            updateHistoric();
        });
        add = new JCheckBox(ADD.getTitle(), ADD.isDisplay());
        add.setBackground(ADD.getColor());
        add.addActionListener(e -> {
            ADD.changeDisplay();
            KaiceModel.getInstance().getHistoric().updateDisplayList();
            updateHistoric();
        });
        sub = new JCheckBox(SUB.getTitle(), SUB.isDisplay());
        sub.setBackground(SUB.getColor());
        sub.addActionListener(e -> {
            SUB.changeDisplay();
            KaiceModel.getInstance().getHistoric().updateDisplayList();
            updateHistoric();
        });
        cancel = new JCheckBox(CANCEL.getTitle(), CANCEL.isDisplay());
        cancel.setBackground(CANCEL.getColor());
        cancel.addActionListener(e -> {
            CANCEL.changeDisplay();
            KaiceModel.getInstance().getHistoric().updateDisplayList();
            updateHistoric();
        });
        enr = new JCheckBox(ENR.getTitle(), ENR.isDisplay());
        enr.setBackground(ENR.getColor());
        enr.addActionListener(e -> {
            ENR.changeDisplay();
            KaiceModel.getInstance().getHistoric().updateDisplayList();
            updateHistoric();
        });
        misc = new JCheckBox(MISC.getTitle(), MISC.isDisplay());
        misc.setBackground(MISC.getColor());
        misc.addActionListener(e -> {
            MISC.changeDisplay();
            KaiceModel.getInstance().getHistoric().updateDisplayList();
            updateHistoric();
        });
    
        JPanel options = new JPanel(new GridLayout(7, 2));
        
        this.setLayout(new BorderLayout());
        this.add(new PanelTitle("Options", e -> KaiceModel.getInstance().setDetails(new JPanel())), NORTH);
        this.add(options, CENTER);
        
        options.add(fullName);
        options.add(sell);
        options.add(admin);
        options.add(cancel);
        options.add(new JLabel());
        options.add(add);
        options.add(new JLabel());
        options.add(sub);
        options.add(new JLabel());
        options.add(buy);
        options.add(new JLabel());
        options.add(enr);
        options.add(new JLabel());
        options.add(misc);
    }
    
    private void updateHistoric() {
        KaiceModel.update(KaiceModel.HISTORIC);
    }
}
