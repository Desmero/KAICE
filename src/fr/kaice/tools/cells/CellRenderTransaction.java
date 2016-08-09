package fr.kaice.tools.cells;

import fr.kaice.model.KaiceModel;
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
public class CellRenderTransaction extends DCellRender {
    
    /**
     * Create a {@link CellRenderTransaction} extending of {@link DCellRender}.
     *
     * @param colClass
     *          {@link Class} - The class of object contains in the cells.
     * @param editable
     *          boolean - True if the cells are editable, false if not.
     * @param totalLine
     *          boolean - True if the last cell is use for a summary.
     */
    public CellRenderTransaction(Class<?> colClass, boolean editable, boolean totalLine) {
        super(colClass, editable, totalLine);
    }
    
    @Override
    protected Color getColor(JTable table, int row, int col, JLabel l) {
        if (totalLine && row == table.getRowCount() - 1) {
            return DColor.GRAY;
        }
        Color color1 = KaiceModel.getHistoric().getTransaction(row).getColor();
        return DFunction.colorFusion(color1, super.getColor(table, row, col, l));
    }
    
}
