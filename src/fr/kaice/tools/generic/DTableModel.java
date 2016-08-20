package fr.kaice.tools.generic;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;

/**
 * This class extends {@link AbstractTableModel}. This class manage column models with {@link DCellRender}.
 * And provide a implementation of some methods in the {@linkplain javax.swing.table.TableModel TableModel} interface.
 * This can also add a last line to summary the table data.
 *
 * @author Raphaël Merkling
 * @version 1.1
 * @see AbstractTableModel
 * @see DCellRender
 * @see javax.swing.table.TableModel
 */
public abstract class DTableModel extends AbstractTableModel implements Serializable {

    private static final long serialVersionUID = 3402791501977226719L;
    protected DTableColumnModel[] colModel;
    protected boolean totalLine;
    
    /**
     * Initialise a new {@link DTableModel}.
     */
    protected DTableModel() {
        totalLine = false;
    }
    
    /**
     * Return the {@link DCellRender} of the column.
     *
     * @param col int - The number of the column.
     * @return The {@link DCellRender} of the corresponding column.
     */
    public DCellRender getColumnModel(int col) {
        return new DCellRender(colModel[col].getColClass(), colModel[col].isEditable(), totalLine);
    }

    public void actionCell(int row, int column) {
        System.out.println("Click " + row + " " + column);
    }

    @Override
    public int getColumnCount() {
        return colModel.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return colModel[columnIndex].getName();
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return colModel[columnIndex].getColClass();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return !(totalLine && rowIndex == getRowCount() - 1) && colModel[columnIndex].isEditable();
    }
    
}
