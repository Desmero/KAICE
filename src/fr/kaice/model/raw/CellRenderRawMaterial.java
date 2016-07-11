package fr.kaice.model.raw;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.DCellRender;

public class CellRenderRawMaterial extends DCellRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CellRenderRawMaterial(Class<?> colClass, boolean editable, boolean totalLine) {
		super(colClass, editable, totalLine);
	}

	protected Color getColor(JTable table, int row, int col, JLabel l) {
		return KaiceModel.getRawMatCollection().getRowColor(row);
	}

	
}
