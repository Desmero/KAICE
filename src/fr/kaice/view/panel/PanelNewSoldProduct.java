package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.sell.CompoCollection;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.model.sell.SoldProduct.prodType;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This panel allow the user to create a new {@link SoldProduct} and store it in the
 * {@linkplain fr.kaice.model.sell.SoldProductCollection SoldProductCollection} known by the {@link KaiceModel}
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see KaiceModel
 * @see fr.kaice.model.sell.SoldProductCollection
 * @see SoldProduct
 */
public class PanelNewSoldProduct extends JPanel {
    
    private CompoCollection tmCompo;
    
    /**
     * Create a new {@link PanelNewSoldProduct}
     */
    public PanelNewSoldProduct() {
        JButton accept = new JButton("Valider");
        JButton add = new JButton();
        JButton rem = new JButton();
        JTextField name = new JTextField();
        DMonetarySpinner price = new DMonetarySpinner(0.1);
        JComboBox<SoldProduct.prodType> type = new JComboBox<SoldProduct.prodType>();
        JList<RawMaterial> list = new JList<RawMaterial>(KaiceModel.getRawMatCollection().getAllRawMaterial());
        JScrollPane spListRaw = new JScrollPane(list);
        tmCompo = new CompoCollection();
        DTablePanel compos = new DTablePanel(KaiceModel.getInstance(), tmCompo);
        
        RawMaterial[] items = KaiceModel.getRawMatCollection().getAllRawMaterial();
        
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() % 2 == 0) {
                    int[] val = list.getSelectedIndices();
                    for (int v : val) {
                        tmCompo.addRawMaterial(items[v]);
                    }
                    update();
                }
            }
        });
        
        add.setIcon(new ImageIcon("icon/rightArrow.png"));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] val = list.getSelectedIndices();
                for (int v : val) {
                    tmCompo.addRawMaterial(items[v]);
                }
                update();
            }
        });
        rem.setIcon(new ImageIcon("icon/leftArrow.png"));
        rem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tmCompo.removeSelectedRows(compos.getSelectedRow());
                update();
            }
        });
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoldProduct prod = KaiceModel.getSoldProdCollection().addNewSoldProduct(name.getText(),
                        price.getIntValue(), (prodType) type.getSelectedItem());
                for (RawMaterial mat : tmCompo.getAllRawMaterial()) {
                    prod.setRawMaterial(mat, tmCompo.getQuantity(mat));
                }
                KaiceModel.update();
            }
        });
        name.setColumns(10);
        type.addItem(SoldProduct.prodType.DRINK);
        type.addItem(SoldProduct.prodType.FOOD);
        type.addItem(SoldProduct.prodType.MISC);
        type.setSelectedItem(SoldProduct.prodType.MISC);
        
        JPanel param = new JPanel();
        JPanel ctrl = new JPanel();
        JPanel compo = new JPanel(new BorderLayout());
        JPanel ctrlCompoDisp = new JPanel();
        JPanel ctrlCompo = new JPanel();
        ctrlCompo.setLayout(new BoxLayout(ctrlCompo, BoxLayout.Y_AXIS));
        
        this.setLayout(new BorderLayout());
        this.add(param, BorderLayout.NORTH);
        this.add(ctrl, BorderLayout.SOUTH);
        this.add(spListRaw, BorderLayout.WEST);
        this.add(compo, BorderLayout.CENTER);
        
        param.add(new Label("Nom : "));
        param.add(name);
        param.add(new Label("Prix : "));
        param.add(price);
        param.add(new Label("Type : "));
        param.add(type);
        
        ctrl.add(accept);
        
        compo.add(ctrlCompoDisp, BorderLayout.WEST);
        compo.add(compos, BorderLayout.CENTER);
        
        JPanel pAdd = new JPanel();
        pAdd.add(add);
        JPanel pRem = new JPanel();
        pRem.add(rem);
        
        ctrlCompoDisp.add(ctrlCompo);
        
        ctrlCompo.setLayout(new GridLayout(2, 1));
        ctrlCompo.add(pAdd);
        ctrlCompo.add(pRem);
    }
    
    /**
     * Update the table. Use when a {@link RawMaterial} is add or remove from it.
     */
    void update() {
        tmCompo.fireTableDataChanged();
    }
}
