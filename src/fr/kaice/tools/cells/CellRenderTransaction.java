package fr.kaice.tools.cells;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DCellRender;
import fr.kaice.tools.generic.DColor;
import fr.kaice.tools.generic.DFunction;

public class CellRenderTransaction extends DCellRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CellRenderTransaction(Class<?> colClass, boolean editable, boolean totalLine) {
		super(colClass, editable, totalLine);
	}

	protected Color getColor(JTable table, int row, int col, JLabel l) {
		if (row == table.getRowCount() - 1) {
			return DColor.GRAY;
		}
		Color color1 = KaiceModel.getHistoric().getTransaction(row).getColor();
		return DFunction.colorfusion(color1, super.getColor(table, row, col, l));
	}

}
