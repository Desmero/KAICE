package fr.kaice.tools.cells;

import fr.kaice.tools.generic.DCellRender;
import fr.kaice.tools.generic.DColor;
import fr.kaice.tools.generic.IHiddenCollection;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a {@link DCellRender} modified for coloring the cells with the containing a hidden item.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see DCellRender
 * @see fr.kaice.model.raw.RawMaterial
 */
public class CellRenderHiddenProduct extends DCellRender {
    
    private final IHiddenCollection coll;
    
    /**
     * Create a {@link CellRenderHiddenProduct} extending of {@link DCellRender}.
     *
     * @param colClass  {@link Class} - The class of object contains in the cells.
     * @param editable  boolean - True if the cells are editable, false if not.
     * @param totalLine boolean - True if the last cell is use for a summary.
     */
    public CellRenderHiddenProduct(Class<?> colClass, boolean editable, boolean totalLine, IHiddenCollection coll) {
        super(colClass, editable, totalLine);
        this.coll = coll;
    }
    
    @Override
    protected Color getColor(JTable table, int row, int col, JLabel l) {
        if (coll.isHiddenRow(row)) {
            return DColor.DARK_GRAY;
        } else {
            return super.getColor(table, row, col, l);
        }
    }
}
