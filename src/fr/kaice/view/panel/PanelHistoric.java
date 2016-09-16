package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.Transaction;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.tools.generic.TimePeriodChooser;

import javax.swing.*;
import java.awt.*;

/**
 * This panel display the {@linkplain fr.kaice.model.historic.Historic Historic} table known by {@link KaiceModel}.
 * The interface allow to visualise the details of a {@link Transaction}.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see KaiceModel
 * @see fr.kaice.model.historic.Historic
 * @see Transaction
 */
public class PanelHistoric extends JPanel {
    
    /**
     * Create a new {@link PanelHistoric}.
     */
    public PanelHistoric() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getHistoric());
        TimePeriodChooser dateChooser = new TimePeriodChooser(KaiceModel.getHistoric());
        JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
        JPanel ctrl = new JPanel();
    
        if (KaiceModel.editor) {
            add.addActionListener(e -> KaiceModel.getHistoric().removeRow(table.getSelectedRow()));
        } else {
            add.addActionListener(e -> KaiceModel.getInstance().setDetails(new PanelNewHistoricLine()));
        }
        view.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                Transaction tran = KaiceModel.getHistoric().getTransaction(table.getSelectedRow());
                KaiceModel.getInstance().setDetails(new PanelTransaction(tran));
            }
        });
        
        
        table.setMultiSelection(false);
        
        JPanel tableDate = new JPanel(new BorderLayout());
        tableDate.add(table, BorderLayout.CENTER);
        tableDate.add(dateChooser, BorderLayout.SOUTH);
        
        this.setLayout(new BorderLayout());
        this.add(tableDate, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        ctrl.add(add);
        ctrl.add(view);
    }
}
