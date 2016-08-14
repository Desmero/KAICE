package fr.kaice.tools.cells;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DCellRender;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a {@link DCellRender} modified for coloring the cells with the
 * {@linkplain fr.kaice.model.raw.RawMaterial RawMaterial} Color.
 *
 * @author Raphaël Merkling
 * @version 2.0
 * @see DCellRender
 * @see fr.kaice.model.raw.RawMaterial
 */
public class CellRenderRawMaterial extends DCellRender {
    
    /**
     * Create a {@link CellRenderRawMaterial} extending of {@link DCellRender}.
     *
     * @param colClass  {@link Class} - The class of object contains in the cells.
     * @param editable  boolean - True if the cells are editable, false if not.
     * @param totalLine boolean - True if the last cell is use for a summary.
     */
    public CellRenderRawMaterial(Class<?> colClass, boolean editable, boolean totalLine) {
        super(colClass, editable, totalLine);
    }
    
    @Override
    protected Color getColor(JTable table, int row, int col, JLabel l) {
        return KaiceModel.getRawMatCollection().getRowColor(row);
    }
    
    
}
