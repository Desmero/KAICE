package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.Transaction;
import fr.kaice.model.historic.TransactionTableModel;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTableModel;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;

import static fr.kaice.tools.local.French.*;

/**
 * This panel display all information about a {@link Transaction}.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see Transaction
 */
public class PanelTransaction extends JPanel {
    
    /**
     * Create a new {@link PanelTransaction} who display the details of the given {@link Transaction}.
     *
     * @param tran {@link Transaction} - The transaction to visualise.
     */
    public PanelTransaction(Transaction tran) {
    
        DTableModel tableModel = new TransactionTableModel(tran);
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), tableModel);

        JPanel all = new JPanel(new BorderLayout());
        JPanel details = new JPanel();
        
        this.setLayout(new BorderLayout());
        this.add(new PanelTitle(tran.getName(), e -> KaiceModel.getInstance().setDetails(new JPanel())), BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        all.add(details, BorderLayout.NORTH);
        all.add(table, BorderLayout.CENTER);

        details.add(new JLabel(TF_CLIENT + tran.getClient()));
        details.add(new JLabel(TF_DATE + DFormat.format(tran.getDate())));
        details.add(new JLabel(TF_PRICE + DMonetarySpinner.intToString(tran.getPrice())));
        details.add(new JLabel(TF_CASH + DMonetarySpinner.intToString(tran.getPaid())));
        details.add(new JLabel(TF_CASHIER + KaiceModel.getMemberCollection().getMember(tran.getAdminId())));
    }
}
