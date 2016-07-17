package fr.kaice.tools.generic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class DTablePanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5801882938131689389L;
	private DTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane; 
	
	public DTablePanel (Observable obs, DTableModel tableModel) {
		obs.addObserver(this);
		construct(tableModel);
	}

	public DTablePanel (Observable obs, DTableModel tableModel, int row) {
		obs.addObserver(this);
		construct(tableModel);
		setNumberRow(row);
	}

	public void setMultiselection(boolean multi) {
		if (multi) {
			table.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		}
	}
	
	private void construct(DTableModel tableModel) {
		this.setLayout(new BorderLayout());
		this.tableModel = tableModel;
		table = new JTable(tableModel);
		table.addContainerListener(new ContainerListener() {
			@Override
			public void componentRemoved(ContainerEvent arg0) {
			}

			@Override
			public void componentAdded(ContainerEvent arg0) {
				JTextField text = (JTextField) arg0.getChild();
				text.setText(null);
			}
		});
		table.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		scrollPane = new JScrollPane(table);
		this.add(scrollPane, BorderLayout.CENTER);

		tableModel.fireTableChanged(null);
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i)
			.setCellRenderer(tableModel.getColumnModel(i));
		}
	}

	public JTable getTable() {
		return table;
	}
	
	private void setNumberRow(int row) {
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * row));
	}
	
	public void setWidth(int width) {
		Dimension d = this.getPreferredSize();
		d.setSize(width, d.getHeight());
		this.setPreferredSize(d);
		
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public int[] getSelectedRows() {
		return table.getSelectedRows();
	}
	
	public void clearSelection() {
		table.clearSelection();
	}
	
	public void resizeColumnWidth() {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 2; // Min width
			int widthMax = 175; // Max width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
				width = Math.min(width, widthMax);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		tableModel.fireTableChanged(null);
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i)
			.setCellRenderer(tableModel.getColumnModel(i));
		}
		resizeColumnWidth();
	}

}
