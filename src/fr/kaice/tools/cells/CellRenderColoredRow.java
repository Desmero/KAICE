package fr.kaice.tools.cells;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.historic.IColoredTableModel;
import fr.kaice.tools.generic.DCellRender;
import fr.kaice.tools.generic.DColor;
import fr.kaice.tools.generic.DFunction;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a {@link DCellRender} modified for coloring the cells with the
 * {@linkplain fr.kaice.model.historic.Transaction Transaction} Color.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see DCellRender
 * @see fr.kaice.model.historic.Transaction
 */
public class CellRenderColoredRow extends DCellRender {

    private final IColoredTableModel tableModel;

    /**
     * Create a {@link CellRenderColoredRow} extending of {@link DCellRender}.
     *
     * @param colClass  {@link Class} - The class of object contains in the cells.
     * @param editable  boolean - True if the cells are editable, false if not.
     * @param totalLine boolean - True if the last cell is use for a summary.
     */
    public CellRenderColoredRow(Class<?> colClass, boolean editable, boolean totalLine, IColoredTableModel tableModel) {
        super(colClass, editable, totalLine);
        this.tableModel = tableModel;
    }
    
    @Override
    protected Color getColor(JTable table, int row, int col, JLabel l) {
        if (totalLine && row == table.getRowCount() - 1) {
            return DColor.GRAY;
        }
        return tableModel.getRowColor(row);
        // return DFunction.colorFusion(color1, super.getColor(table, row, col, l));
    }
    
}
