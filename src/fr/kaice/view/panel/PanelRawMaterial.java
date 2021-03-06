package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;

import static fr.kaice.tools.local.French.*;

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
     * Create a new {@link PanelRawMaterial}
     */
    public PanelRawMaterial() {
        DTablePanel table = new DTablePanel(KaiceModel.getInstance(), KaiceModel.getRawMatCollection());
        JButton add = new JButton(B_ADD), view = new JButton(B_VIEW), hide = new JButton(B_HIDE);
        JPanel ctrl = new JPanel();
        
        add.addActionListener(arg0 -> {
            String s = (String) JOptionPane.showInputDialog(null,
                    DIALOG_TEXT_NEW_RAW_MATERIAL, DIALOG_NAME_NEW_RAW_MATERIAL, -1, null, null,
                    null);
            if (s != null) {
                KaiceModel.getRawMatCollection().addNewRawMaterial(s);
            }
        });
        view.addActionListener(e -> KaiceModel.getInstance().setDetails(KaiceModel.getRawMatCollection().getMaterialAtRow(table.getSelectedRow()).getDetails()));
        if (KaiceModel.editor) {
            hide.setText(B_DELETE);
            hide.addActionListener(e -> KaiceModel.getRawMatCollection().removeRow(table.getSelectedRow()));
        } else {
            hide.addActionListener(e -> KaiceModel.getRawMatCollection().hideRow(table.getSelectedRow()));
        }
    
        table.setMultiSelection(false);
        
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        ctrl.add(add);
        ctrl.add(view);
        ctrl.add(hide);
    }
}
