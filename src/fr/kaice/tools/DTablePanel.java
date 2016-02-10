package fr.kaice.tools;

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
import fr.kaice.model.KaiceModel;

public class DTablePanel extends JPanel implements Observer{

	private DTableModel tableModel;
	private JTable table;
	
	public DTablePanel (Observable obs, DTableModel tableModel) {
		obs.addObserver(this);
		
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
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
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
