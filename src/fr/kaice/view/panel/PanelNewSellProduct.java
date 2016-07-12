package fr.kaice.view.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.management.monitor.MonitorSettingException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import fr.kaice.model.KaiceModel;
import fr.kaice.model.raw.RawMaterial;
import fr.kaice.model.raw.RawMaterialCollection;
import fr.kaice.model.sell.CompoCollection;
import fr.kaice.model.sell.SoldProduct;
import fr.kaice.model.sell.SoldProduct.prodType;
import fr.kaice.tools.CloseListener;
import fr.kaice.tools.DMonetarySpinner;
import fr.kaice.tools.DTableModel;
import fr.kaice.tools.DTablePanel;

public class PanelNewSellProduct extends JPanel {

	private CompoCollection tmCompo;

	public PanelNewSellProduct() {
		JButton accept = new JButton("Valider");
		JButton add = new JButton("=>");
		JButton rem = new JButton("<=");
		JTextField name = new JTextField();
		DMonetarySpinner price = new DMonetarySpinner(0.1);
		JComboBox<SoldProduct.prodType> type = new JComboBox<SoldProduct.prodType>();
		JList<RawMaterial> list = new JList<RawMaterial>(KaiceModel.getRawMatCollection().getAllRawMaterial());
		JScrollPane spListRaw = new JScrollPane(list);
		tmCompo = new CompoCollection();
		DTablePanel compos = new DTablePanel(KaiceModel.getInstance(), tmCompo);

		RawMaterial[] items = KaiceModel.getRawMatCollection().getAllRawMaterial();

		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() ==2) {
					int[] val = list.getSelectedIndices();
					for (int v : val) {
						tmCompo.addRawMaterial(items[v]);
					}
					update();
				}
			}
		});
		
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] val = list.getSelectedIndices();
				for (int v : val) {
					tmCompo.addRawMaterial(items[v]);
				}
				update();
			}
		});
		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tmCompo.removeSelectedRows(compos.getSelectedRow());
				update();
			}
		});
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SoldProduct prod = KaiceModel.getSoldProdCollection().addNewSoldProduct(name.getText(),
						price.getIntValue(), (prodType) type.getSelectedItem());
				for (RawMaterial mat : tmCompo.getAllRawMaterial()) {
					prod.setRawMaterial(mat.getId(), tmCompo.getQuentity(mat.getId()));
				}
				KaiceModel.update();
			}
		});
		name.setColumns(10);
		type.addItem(SoldProduct.prodType.DRINK);
		type.addItem(SoldProduct.prodType.FOOD);
		type.addItem(SoldProduct.prodType.MISC);
		type.setSelectedItem(SoldProduct.prodType.MISC);
		
		JPanel param = new JPanel();
		JPanel ctrl = new JPanel();
		JPanel compo = new JPanel(new BorderLayout());
		JPanel ctrlCompo = new JPanel();
		ctrlCompo.setLayout(new BoxLayout(ctrlCompo, BoxLayout.Y_AXIS));

		this.setLayout(new BorderLayout());
		this.add(param, BorderLayout.NORTH);
		this.add(ctrl, BorderLayout.SOUTH);
		this.add(spListRaw, BorderLayout.WEST);
		this.add(compo, BorderLayout.CENTER);

		param.add(new Label("Nom : "));
		param.add(name);
		param.add(new Label("Prix : "));
		param.add(price);
		param.add(new Label("Type : "));
		param.add(type);

		ctrl.add(accept);

		compo.add(ctrlCompo, BorderLayout.WEST);
		compo.add(compos, BorderLayout.CENTER);

		JPanel pAdd = new JPanel();
		pAdd.add(add);
		JPanel pRem = new JPanel();
		pRem.add(rem);
		
		ctrlCompo.setLayout(new GridLayout(10, 1));
		ctrlCompo.add(new JLabel(""));
		ctrlCompo.add(new JLabel(""));
		ctrlCompo.add(new JLabel(""));
		ctrlCompo.add(new JLabel(""));
		ctrlCompo.add(pAdd);
		ctrlCompo.add(pRem);
	}

	void update() {
		tmCompo.fireTableDataChanged();
	}
}
