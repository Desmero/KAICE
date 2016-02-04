package fr.kaice.tools;

import javax.swing.table.AbstractTableModel;

/**
 * Simple {@link AbstractTableModel} with some methods already implements. Used
 * in the KAICE project.
 * 
 * @author Raph
 * @version 1.0
 */
public abstract class DTableModel extends AbstractTableModel {

	protected String[] colNames;
	protected Class<?>[] colClass;
	protected Boolean[] colEdit;

	public DCellRender getColumnModel(int col) {
		return new DCellRender(colClass[col], colEdit[col], false);
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return colClass[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return colEdit[columnIndex];
	}

}
