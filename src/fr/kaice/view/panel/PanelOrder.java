package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;
import fr.kaice.view.window.WindowAskAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This panel display all the not yet delivred {@linkplain fr.kaice.model.order.Order Order} contains in the
 * {@linkplain fr.kaice.model.order.OrderCollection OrderCollection} known by {@link KaiceModel}.
 * The interface allow to remove or valid orders.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see fr.kaice.model.order.Order
 * @see fr.kaice.model.order.OrderCollection
 */
public class PanelOrder extends JPanel {
    
    /**
     * Create a new {@link PanelOrder}.
     */
    public PanelOrder() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getOrderCollection());
        JButton valid = new JButton("Valider"), rem = new JButton("Annuler");
        JPanel ctrl = new JPanel();
        DMonetarySpinner cashBack = new DMonetarySpinner(0.1);
        
        valid.addActionListener(e -> {
            int[] rows = table.getSelectedRows();
            KaiceModel.getOrderCollection().validOrders(rows);
        });
        rem.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                WindowAskAdmin.generate(e2 -> {
                    KaiceModel.getOrderCollection().cancelOrder(row, cashBack.getIntValue());
                    cashBack.setValue(0);
                });
            }
        });
        
        table.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    KaiceModel.getOrderCollection().validOrder(row);
                }
            }
        });
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        ctrl.add(valid);
        ctrl.add(rem);
        ctrl.add(cashBack);
    }
}
