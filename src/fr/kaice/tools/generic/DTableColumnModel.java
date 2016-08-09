package fr.kaice.tools.generic;

/**
 * This class contains what need a {@link DTableModel} to display correctly a table column. <br/>
 * It contains : <br/>
 * - The name of the column; <br/>
 * - The class of the elements in the cells of the column; <br/>
 * - And a boolean to know if the cells are editable or not.
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
public class DTableColumnModel {
    
    private final String name;
    private final Class colClass;
    private final boolean editable;
    
    /**
     * Create a new {@link DTableColumnModel}.
     *
     * @param name     {@link String} - The name of the column.
     * @param colClass {@link Class} - The class of the object in the cells.
     * @param editable boolean - True if the cells are editable, false if not.
     */
    public DTableColumnModel(String name, Class colClass, boolean editable) {
        this.name = name;
        this.colClass = colClass;
        this.editable = editable;
    }
    
    /**
     * Return the name of the column.
     *
     * @return The name of the column.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the class if the objects in the cells.
     *
     * @return The class if the objects in the cells.
     */
    public Class getColClass() {
        return colClass;
    }
    
    /**
     * Return true if the cells are editable, false if not.
     *
     * @return True if the cells are editable, false if not.
     */
    public boolean isEditable() {
        return editable;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
