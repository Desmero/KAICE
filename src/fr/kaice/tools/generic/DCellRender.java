package fr.kaice.tools.generic;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * This class is a custom {@linkplain DefaultTableCellRenderer}.
 * It color editable cell in white and non editable cell in light gray, and blueish selected cells.
 * It format double value with monetary format and add a Euro symbol.
 * It can also can color the last line in dark gray for a summary line.
 *
 * @author Raphaël Merkling
 * @version 2.0
 */
public class DCellRender extends DefaultTableCellRenderer {
    
    protected Class<?> colClass;
    protected boolean editable;
    protected boolean totalLine;
    
    /**
     * Create a {@link DCellRender}.
     *
     * @param colClass  {@link Class} - The class of object contains in the cells.
     * @param editable  boolean - True if the cells are editable, false if not.
     * @param totalLine boolean - True if the last cell is use for a summary.
     */
    public DCellRender(Class<?> colClass, boolean editable, boolean totalLine) {
        super();
        this.colClass = colClass;
        this.editable = editable;
        this.totalLine = totalLine;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int col) {
        
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        
        Color newCol = getColor(table, row, col, l);
        
        if (table.isCellSelected(row, col)) {
            newCol = bluishColor(newCol);
        }
        updateText(l);
        
        l.setBackground(newCol);
        return l;
    }
    
    /**
     * Return the color for the cell.
     *
     * @param table {@link JTable} - The table that contain the cells.
     * @param row   int - The number of the row.
     * @param col   int - The number of the column.
     * @param l     {@link JLabel} - The JLabel of the cell.
     * @return The color for the cell
     */
    protected Color getColor(JTable table, int row, int col, JLabel l) {
        Color newCol;
        
        if (totalLine && row + 1 == table.getRowCount()) {
            newCol = DColor.GRAY;
        } else {
            if (editable) {
                newCol = DColor.WHITE;
            } else {
                newCol = DColor.LIGHT_GRAY;
            }
        }
        return newCol;
    }
    
    /**
     * Fuse a color with the blue of selection.
     *
     * @param color {@link Color} - The color to fuse.
     * @return The fused color.
     */
    private Color bluishColor(Color color) {
        return DFunction.colorFusion(DColor.BLUE_SELECTION, color);
    }
    
    /**
     * Update the text of the cell. Format the double value with a monetary format, and add a Euro symbol et the end.
     *
     * @param l {@link JLabel} - The JLabel of the cell.
     */
    protected void updateText(JLabel l) {
        if (colClass == Double.class) {
            try {
                double val = Double.parseDouble(l.getText());
                l.setText(DFormat.MONEY_FORMAT.format(val) + " ?");
            } catch (Exception e) {
            }
        }
    }
}
