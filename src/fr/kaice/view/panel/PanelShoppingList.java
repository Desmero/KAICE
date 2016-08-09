package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;

import javax.swing.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * This panel display the list of {@link RawMaterial} below the alert level, and how much are needed to reach twice the
 * alert level.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see JPanel
 * @see KaiceModel
 */
public class PanelShoppingList extends JPanel implements Observer {
    
    private JList<JCheckBox> list;
    
    /**
     * Create a new {@link PanelShoppingList}
     */
    public PanelShoppingList() {
        KaiceModel.getInstance().addObserver(this);
        list = new JList<>();
        JScrollPane scroll = new JScrollPane(list);
        
        this.add(scroll);
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        HashMap<RawMaterial, Integer> map = KaiceModel.getRawMatCollection().getShoppingList();
        list.removeAll();
        for (RawMaterial mat : map.keySet()) {
            JCheckBox check = new JCheckBox(mat.getName() + " x " + map.get(mat));
            list.add(check);
        }
    }
}
