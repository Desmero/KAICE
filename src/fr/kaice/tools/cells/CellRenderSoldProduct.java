package fr.kaice.tools.cells;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DCellRender;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a {@link DCellRender} modified for coloring the cells with the
 * {@linkplain fr.kaice.model.sell.SoldProduct SoldProduct} Color.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see DCellRender
 * @see fr.kaice.model.sell.SoldProduct
 */
public class CellRenderSoldProduct extends DCellRender {
    
    /**
     * Create a {@link CellRenderSoldProduct} extending of {@link DCellRender}.
     *
     * @param colClass  {@link Class} - The class of object contains in the cells.
     * @param editable  boolean - True if the cells are editable, false if not.
     * @param totalLine boolean - True if the last cell is use for a summary.
     */
    public CellRenderSoldProduct(Class<?> colClass, boolean editable, boolean totalLine) {
        super(colClass, editable, totalLine);
    }
    
    @Override
    protected Color getColor(JTable table, int row, int col, JLabel l) {
        Color color = KaiceModel.getSoldProdCollection().getRowColor(row);
        if (color == null) {
            return super.getColor(table, row, col, l);
        } else {
            return color;
        }
    }
    
}
