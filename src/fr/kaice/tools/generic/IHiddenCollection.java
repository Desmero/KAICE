package fr.kaice.tools.generic;

/**
 * This interface is used for coloring hidden items.
 */
public interface IHiddenCollection {
    
    /**
     * Return true if the item at the specified row is hidden.
     *
     * @param row int - The row containing the item.
     * @return True if the item at the specified row is hidden.
     */
    boolean isHiddenRow(int row);
    
}
