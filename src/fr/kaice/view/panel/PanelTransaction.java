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

        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), tran);

        JPanel all = new JPanel(new BorderLayout());
        JPanel details = new JPanel();
        
        this.setLayout(new BorderLayout());
        this.add(new PanelTitle(tran.getName(), e -> KaiceModel.getInstance().setDetails(new JPanel())), BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        all.add(details, BorderLayout.NORTH);
        all.add(table, BorderLayout.CENTER);

        details.add(new JLabel("Client : " + tran.getClient()));
        details.add(new JLabel("Date : " + DFormat.format(tran.getDate())));
        details.add(new JLabel("Prix : " + DMonetarySpinner.intToString(tran.getPrice())));
        details.add(new JLabel("Espece : " + DMonetarySpinner.intToString(tran.getPaid())));
        details.add(new JLabel("Caissier : " + KaiceModel.getMemberCollection().getMember(tran.getAdminId())));
    }
}
