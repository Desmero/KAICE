package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.sell.CompoCollection;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.model.sell.SoldProductCollection;
import fr.kaice.model.sell.SoldProductCollection.prodType;
import fr.kaice.tools.generic.DMonetarySpinner;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
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
class PanelNewSoldProduct extends JPanel {

    private final CompoCollection tmCompo;
    private final JTextField name;
    private final DMonetarySpinner price;
    private final JList<RawMaterial> list;

    /**
     * Create a new {@link PanelNewSoldProduct}
     */
    public PanelNewSoldProduct() {
        JButton accept = new JButton("Valider");
        JButton add = new JButton();
        JButton rem = new JButton();
        name = new JTextField();
        price = new DMonetarySpinner(0.1);
        JComboBox<SoldProductCollection.prodType> type = new JComboBox<>();
        list = new JList<>(KaiceModel.getRawMatCollection().getAllRawMaterial());
        JScrollPane spListRaw = new JScrollPane(list);
        tmCompo = new CompoCollection();
        DTablePanel compos = new DTablePanel(KaiceModel.getInstance(), tmCompo);

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() % 2 == 0) {
                    addSelection();
                }
            }
        });

        add.setIcon(new ImageIcon("icon/rightArrow.png"));
        add.addActionListener(e -> addSelection());
        rem.setIcon(new ImageIcon("icon/leftArrow.png"));
        rem.addActionListener(e -> {
            tmCompo.removeSelectedRows(compos.getSelectedRow());
            update();
        });
        accept.addActionListener(e -> {
            SoldProduct prod = KaiceModel.getSoldProdCollection().addNewSoldProduct(name.getText(),
                    price.getIntValue(), (prodType) type.getSelectedItem());
            for (RawMaterial mat : tmCompo.getAllRawMaterial()) {
                prod.setRawMaterial(mat, tmCompo.getQuantity(mat));
            }
            KaiceModel.update(KaiceModel.SOLD_PRODUCT);
            reset();
        });
        name.setColumns(10);
        type.addItem(prodType.DRINK);
        type.addItem(prodType.FOOD);
        type.addItem(prodType.MISC);
        type.setSelectedItem(prodType.MISC);

        JPanel all = new JPanel(new BorderLayout());
        PanelTitle title = new PanelTitle("Nouvel article en vente", e -> KaiceModel.getInstance().setDetails(new JPanel()));
        JPanel param = new JPanel();
        JPanel ctrl = new JPanel();
        JPanel compo = new JPanel(new BorderLayout());
        JPanel ctrlCompoDisp = new JPanel();
        JPanel ctrlCompo = new JPanel();
        ctrlCompo.setLayout(new BoxLayout(ctrlCompo, BoxLayout.Y_AXIS));

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(all, BorderLayout.CENTER);
        all.add(param, BorderLayout.NORTH);
        all.add(ctrl, BorderLayout.SOUTH);
        all.add(spListRaw, BorderLayout.WEST);
        all.add(compo, BorderLayout.CENTER);

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

    private void reset() {
        name.setText("");
        price.setValue(0);
        tmCompo.clear();
    }

    private void addSelection() {
        RawMaterial[] items = KaiceModel.getRawMatCollection().getAllRawMaterial();
        int[] val = list.getSelectedIndices();
        for (int v : val) {
            tmCompo.addRawMaterial(items[v]);
        }
        if (name.getText().equals("") && val.length == 1) {
            name.setText(list.getSelectedValue().getName());
        }
        update();
    }

    /**
     * Update the table. Use when a {@link RawMaterial} is add or remove from it.
     */
    private void update() {
        tmCompo.fireTableDataChanged();
    }
}
