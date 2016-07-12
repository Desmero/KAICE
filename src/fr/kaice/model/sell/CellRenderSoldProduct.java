package fr.kaice.model.sell;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.generic.DCellRender;

public class CellRenderSoldProduct extends DCellRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CellRenderSoldProduct(Class<?> colClass, boolean editable, boolean totalLine) {
		super(colClass, editable, totalLine);
	}

	protected Color getColor(JTable table, int row, int col, JLabel l) {
		Color color = KaiceModel.getSoldProdCollection().getRowColor(row);
		if (color == null) {
			return super.getColor(table, row, col, l);
		} else {
			return color;
		}
	}
	
}
