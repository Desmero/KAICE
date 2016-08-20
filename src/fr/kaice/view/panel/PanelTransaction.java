package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.Transaction;
import fr.kaice.tools.generic.DFormat;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;

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
        
        JLabel member = new JLabel(tran.getClient());
        JLabel date = new JLabel(DFormat.FULL_DATE_FORMAT.format(tran.getDate()));
        JLabel price = new JLabel("" + DMonetarySpinner.intToDouble(tran.getPrice()));
        JLabel paid = new JLabel("" + DMonetarySpinner.intToDouble(tran.getPaid()));

        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), tran);

        JPanel all = new JPanel(new BorderLayout());
        JPanel details = new JPanel(new GridLayout(2, 2));
        
        this.setLayout(new BorderLayout());
        this.add(new PanelTitle("Détails d'une transaction", e -> KaiceModel.getInstance().setDetails(new JPanel())), BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        all.add(details, BorderLayout.NORTH);
        all.add(table, BorderLayout.CENTER);

        // TODO reorganize panel
        details.add(member);
        details.add(date);
        details.add(price);
        details.add(paid);
    }
}
