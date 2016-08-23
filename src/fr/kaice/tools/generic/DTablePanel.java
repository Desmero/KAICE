package fr.kaice.tools.generic;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is a generic {@link JPanel} who display a {@link JTable} with a {@link DTableModel}.
 * This also use a {@link JScrollPane}, and implements {@link Observer}.
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
public class DTablePanel extends JPanel implements Observer {

    private DTableModel tableModel;
    private RXTable table;
    private JScrollPane scrollPane;

    /**
     * Create a new {@link DTablePanel}.
     *
     * @param obs        {@link Observer} - The observer who ask to the panel to update.
     * @param tableModel {@link DTableModel} - The moder of the table.
     */
    public DTablePanel(Observable obs, DTableModel tableModel) {
        if (obs != null) {
            obs.addObserver(this);
        }
        this.setLayout(new BorderLayout());
        this.tableModel = tableModel;
        table = new RXTable(tableModel);
        table.setSelectAllForEdit(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    tableModel.actionCell(table.getSelectedRow(), table.getSelectedColumn());
                }
            }
        });
        scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        tableModel.fireTableChanged(null);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i)
                    .setCellRenderer(tableModel.getColumnModel(i));
        }
    }

    /**
     * Create a new {@link DTablePanel}.
     *
     * @param obs        {@link Observer} - The observer who ask to the panel to update.
     * @param tableModel {@link DTableModel} - The moder of the table.
     * @param row        int - The number of row to display.
     */
    public DTablePanel(Observable obs, DTableModel tableModel, int row) {
        this(obs, tableModel);
        setNumberRow(row);
    }

    /**
     * Set the size of the panel to display only the given number of rows.
     *
     * @param row int - The number of rows to display.
     */
    private void setNumberRow(int row) {
        Dimension d = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * row));
    }

    /**
     * Chose the selection mode between the "multiple interval selection" or the "single selection".
     *
     * @param multi boolean - If true, select the "multiple interval selection". If false, the "single selection".
     */
    public void setMultiSelection(boolean multi) {
        if (multi) {
            table.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        } else {
            table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        }
    }

    /**
     * Return the {@link JTable}.
     *
     * @return The {@link JTable}.
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Set the width with a integer value, but keep the same height.
     *
     * @param width int - The new width value.
     */
    public void setWidth(int width) {
        Dimension d = this.getPreferredSize();
        d.setSize(width, d.getHeight());
        this.setPreferredSize(d);

    }

    /**
     * Copied from {@link JTable}. <br/>
     * Returns the index of the first selected row, -1 if no row is selected.
     *
     * @return the index of the first selected row
     */
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    /**
     * Copied from {@link JTable}. <br/>
     * Returns the indices of all selected rows.
     *
     * @return an array of integers containing the indices of all selected rows, or an empty array if no row is selected
     *
     * @see #getSelectedRow
     */
    public int[] getSelectedRows() {
        return table.getSelectedRows();
    }

    /**
     * Copied from {@link JTable}. <br/>
     * Deselects all selected columns and rows.
     */
    public void clearSelection() {
        table.clearSelection();
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        tableModel.fireTableChanged(null);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i)
                    .setCellRenderer(tableModel.getColumnModel(i));
        }
        resizeColumnWidth();
    }

    /**
     * Method copied from internet. <br/>
     * Resize all column of the table proportionally of the size they need.
     */
    public void resizeColumnWidth() {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 2; // Min width
            int widthMax = 175; // Max width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
                width = Math.min(width, widthMax);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

}
