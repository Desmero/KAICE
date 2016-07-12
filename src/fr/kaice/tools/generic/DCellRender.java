package fr.kaice.tools.generic;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DCellRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8572022100345618236L;
	private Class<?> colClass;
	private boolean editable;
	private boolean totalLine;

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

	private Color bluishColor(Color color) {
		return DFunction.colorfusion(DColor.BLUE_SELECTION, color);
	}

	protected Color getColor(JTable table, int row, int col, JLabel l) {
		Color newCol = DColor.WHITE;

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

	protected void updateText(JLabel l) {
		if (colClass == Double.class) {
			try {
				double val = Double.parseDouble(l.getText());
				l.setText(DFormat.MONEY_FORMAT.format(val) + " €");
			} catch (Exception e) {
			}
		}
	}
}
