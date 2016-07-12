package fr.kaice.tools.generic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

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

	private void setNumberRow(int row) {
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, table.getRowHeight() * row));
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		tableModel.fireTableChanged(null);
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i)
			.setCellRenderer(tableModel.getColumnModel(i));
		}
	}

}
