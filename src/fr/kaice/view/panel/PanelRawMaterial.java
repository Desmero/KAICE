package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel display all {@linkplain fr.kaice.model.raw.RawMaterial RawMaterial} contains in the
 * {@linkplain fr.kaice.model.raw.RawMaterialCollection RawMaterialCollection} known by {@link KaiceModel}.
 * The interface allow to visualise or create a new Material.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see fr.kaice.model.raw.RawMaterialCollection
 * @see fr.kaice.model.raw.RawMaterial
 */
public class PanelRawMaterial extends JPanel {
    
    /**
     *  Create a new {@link PanelRawMaterial}
     */
    public PanelRawMaterial() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getRawMatCollection());
        JButton add = new JButton("Ajouter"), view = new JButton("Visualiser");
        JPanel ctrl = new JPanel();
        
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String s = (String) JOptionPane.showInputDialog(null,
                        "Nom du produit : ", "Nouveau produit", -1, null, null,
                        null);
                if (s != null) {
                    KaiceModel.getRawMatCollection().addNewRawMaterial(s);
                }
            }
        });
        view.setEnabled(false);
        
        table.setMultiSelection(false);
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        ctrl.add(add);
        ctrl.add(view);
    }
}
