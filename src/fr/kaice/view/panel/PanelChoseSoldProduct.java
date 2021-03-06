package fr.kaice.view.panel;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.CurrentTransaction;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.model.sell.SoldProductCollection;
import fr.kaice.model.sell.SoldProductTypedCollection;
import fr.kaice.tools.generic.DTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * This panel display all available {@link SoldProduct}.
 * The interface allow to add the selected product to the {@link CurrentTransaction}.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see JPanel
 * @see SoldProduct
 * @see CurrentTransaction
 */
class PanelChoseSoldProduct extends JPanel implements Observer {
    
    private final DTablePanel[] tables;
    private final SoldProductTypedCollection[] tableModels;
    
    /**
     * Create a new {@link PanelChoseSoldProduct}.
     */
    public PanelChoseSoldProduct() {
        KaiceModel.getInstance().addObserver(this);
        SoldProductCollection.prodType[] types = SoldProductCollection.prodType.class.getEnumConstants();
        tables = new DTablePanel[types.length];
        tableModels = new SoldProductTypedCollection[types.length];
        
        class MouseListener extends MouseAdapter {
            private DTablePanel table;
            
            public MouseListener(DTablePanel table) {
                this.table = table;
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() % 2 == 0) {
                    addSelection();
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (!e.isControlDown()) {
                    resetSelectionWithout(table);
                }
            }
        }
        
        JPanel tablesPanel = new JPanel(new GridLayout(2, types.length/2));
        JPanel ctrl = new JPanel();
        
        this.setLayout(new BorderLayout());
        this.add(tablesPanel, BorderLayout.CENTER);
        this.add(ctrl, BorderLayout.SOUTH);
        
        for (int i = 0; i < types.length; i++) {
            JLabel lName = new JLabel(types[i].toString());
            lName.setHorizontalAlignment(SwingConstants.CENTER);
            JPanel name = new JPanel(new BorderLayout());
            name.add(lName, BorderLayout.CENTER);
            name.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
            tableModels[i] = new SoldProductTypedCollection(types[i]);
            tables[i] = new DTablePanel(KaiceModel.getInstance(), tableModels[i]);
            tables[i].getTable().addMouseListener(new MouseListener(tables[i]));
            tables[i].setWidth(20);
            tables[i].resizeColumnWidth();
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(name, BorderLayout.NORTH);
            panel.add(tables[i], BorderLayout.CENTER);
            tablesPanel.add(panel);
        }
        
    }
    
    /**
     * Add the selected {@link SoldProduct} to the {@link CurrentTransaction}.
     */
    public void addSelection() {
        for (int i = 0; i < tables.length; i++) {
            DTablePanel table = tables[i];
            SoldProductTypedCollection tableModel = tableModels[i];
            CurrentTransaction tran = KaiceModel.getCurrentTransaction();
            int[] rows = table.getSelectedRows();
            for (int row : rows) {
                SoldProduct prod = tableModel.getSoldProduct(row);
                tran.addSoldProduct(prod, 1);
            }
        }
        resetSelection();
        KaiceModel.update(KaiceModel.SOLD_PRODUCT);
    }
    
    /**
     * Reset the selection af all tables except one.
     *
     * @param missTable {@link DTablePanel} - The table to KEEP the selection.
     * @see DTablePanel#clearSelection()
     */
    private void resetSelectionWithout(DTablePanel missTable) {
        for (DTablePanel table : tables) {
            if (table != missTable) {
                table.clearSelection();
            }
        }
    }
    
    /**
     * Reset the selection of all tables.
     *
     * @see DTablePanel#clearSelection()
     */
    private void resetSelection() {
        for (DTablePanel table : tables) {
            table.clearSelection();
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (KaiceModel.isPartModified(KaiceModel.SOLD_PRODUCT, KaiceModel.RAW_MATERIAL)) {
            for (int i = 0; i < tables.length; i++) {
                tableModels[i].updateCollection();
                tables[i].resizeColumnWidth();
            }
        }
    }
}
