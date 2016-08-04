package fr.kaice.tools.generic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import fr.kaice.model.KaiceModel;

public class TimePeriodChooser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDateChooser start;
	private JDateChooser end;
	private static final int DAY = 0;
	private static final int WEEK = 1;
	private static final int MONTH = 2;
	private static final int YEAR = 3;
	private static final int ALL = 4;
	private static final int OTHER = 5;

	public TimePeriodChooser() {
		start = new JDateChooser(new Date(), "dd/MM/yyyy HH:mm:ss");
		end = new JDateChooser(new Date(), "dd/MM/yyyy HH:mm:ss");

		PropertyChangeListener listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				KaiceModel.getHistoric().setDateSelect(getStart(), getEnd());
			}
		};
		start.addPropertyChangeListener(listener);
		start.setPreferredSize(new Dimension(150, 25));
		end.addPropertyChangeListener(listener);
		end.setPreferredSize(new Dimension(150, 25));
		
		JComboBox<String> selec = new JComboBox<>();
		selec.addItem("Aujourd'hui");
		selec.addItem("Cette semaine");
		selec.addItem("Ce mois");
		selec.addItem("Cette année");
		selec.addItem("Tout");
		selec.addItem("Personalisé");
		selec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateDate(selec.getSelectedIndex());
			}
		});
		
		JPanel top = new JPanel();
		JPanel bot = new JPanel();

		this.setLayout(new BorderLayout());

		this.add(top, BorderLayout.CENTER);
		this.add(bot, BorderLayout.SOUTH);

		top.add(new JLabel("Début : "));
		top.add(start);
		top.add(new JLabel("Fin : "));
		top.add(end);
		bot.add(new JLabel("Préselecction : "));
		bot.add(selec);
		updateDate(DAY);
	}

	public Date getStart() {
		return start.getDate();
	}

	public Date getEnd() {
		return end.getDate();
	}

	private void updateDate(int selec) {
		Calendar calStart = Calendar.getInstance();
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.clear(Calendar.MINUTE);
		calStart.clear(Calendar.SECOND);
		Calendar calEnd = (Calendar) calStart.clone();
		switch (selec) {
		case DAY:
			calEnd.add(Calendar.DAY_OF_MONTH, 1);
			break;
		case WEEK:
			calStart.set(Calendar.DAY_OF_WEEK, 2);
			calEnd.set(Calendar.DAY_OF_WEEK, 2);
			calEnd.add(Calendar.WEEK_OF_YEAR, 1);
			break;
		case MONTH:
			calStart.set(Calendar.DAY_OF_MONTH, 1);
			calEnd.set(Calendar.DAY_OF_MONTH, 1);
			calEnd.add(Calendar.MONTH, 1);
			break;
		case YEAR:
			if (calStart.get(Calendar.MONTH) < 8) {
				calStart.add(Calendar.YEAR, -1);
				calEnd.add(Calendar.YEAR, -1);
			}
			calStart.set(Calendar.DAY_OF_MONTH, 1);
			calEnd.set(Calendar.DAY_OF_MONTH, 1);
			calStart.set(Calendar.MONTH, 8);
			calEnd.set(Calendar.MONTH, 8);
			calEnd.add(Calendar.YEAR, 1);
			break;
		case ALL:
			calStart.set(Calendar.YEAR, 1900);
			calEnd.add(Calendar.YEAR, 10);
			break;
		default:
			return;
		}
		calEnd.add(Calendar.SECOND, -1);
		start.setDate(calStart.getTime());
		end.setDate(calEnd.getTime());
	}

}
