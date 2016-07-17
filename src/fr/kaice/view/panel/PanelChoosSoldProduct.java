package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import fr.kaice.model.KaiceModel;
import fr.kaice.model.sell.CurrentTransaction;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.model.sell.SoldProductDisplayCollection;
import fr.kaice.tools.generic.DTablePanel;

public class PanelChoosSoldProduct extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean ctrlPressed;
	private DTablePanel[] tables;
	private SoldProductDisplayCollection[] tableModels;

	public PanelChoosSoldProduct() {
		KaiceModel.getInstance().addObserver(this);
		SoldProduct.prodType[] types = SoldProduct.prodType.class.getEnumConstants();
		tables = new DTablePanel[types.length];
		tableModels = new SoldProductDisplayCollection[types.length];

		ctrlPressed = false;
		KeyListener ctrlListener = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == 17) {
					ctrlPressed = false;
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 17) {
					ctrlPressed = true;
				}
			}
		};

		class MouseListener extends MouseAdapter {
			private DTablePanel table;
			public MouseListener(DTablePanel table) {
				this.table = table;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (!ctrlPressed) {
					resetSelectionWithout(table);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					addSelection();
				}
			}
		};

		JPanel tablesNamesPanel = new JPanel(new GridLayout(1, types.length));
		JPanel tablesPanel = new JPanel(new GridLayout(1, types.length));
		JPanel ctrl = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(tablesNamesPanel, BorderLayout.NORTH);
		this.add(tablesPanel, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);

		for (int i = 0; i < types.length; i++) {
			JLabel lName = new JLabel(types[i].toString());
			lName.setHorizontalAlignment(SwingConstants.CENTER);
			JPanel name = new JPanel(new BorderLayout());
			name.add(lName, BorderLayout.CENTER);
			name.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
			tablesNamesPanel.add(name);
			tableModels[i] = new SoldProductDisplayCollection(types[i]);
			tables[i] = new DTablePanel(KaiceModel.getInstance(), tableModels[i]);
			tables[i].getTable().addMouseListener(new MouseListener(tables[i]));
			tables[i].getTable().addKeyListener(ctrlListener);
			tables[i].setWidth(20);
			tables[i].resizeColumnWidth();
			tablesPanel.add(tables[i]);
		}

	}

	private void resetSelection() {
		for (DTablePanel table : tables) {
			table.clearSelection();
		}
	}

	private void resetSelectionWithout(DTablePanel missTable) {
		for (DTablePanel table : tables) {
			if (table != missTable) {
				table.clearSelection();
			}
		}
	}

	public void addSelection() {
		for (int i = 0; i < tables.length; i++) {
			DTablePanel table = tables[i];
			SoldProductDisplayCollection tableModel = tableModels[i];
			CurrentTransaction tran = KaiceModel.getCurrentTransaction();
			int[] rows = table.getSelectedRows();
			for (int row : rows) {
				int id = tableModel.getSoldProduct(row);
				tran.addSoldProduct(id, 1);
			}
		}
		resetSelection();
		KaiceModel.update();
	}

	@Override
	public void update(Observable o, Object arg) {
		for (int i = 0; i < tables.length; i++) {
			tableModels[i].updateCollection();
			tables[i].resizeColumnWidth();
		}
	}
	
}
