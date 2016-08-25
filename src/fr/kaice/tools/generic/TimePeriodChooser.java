package fr.kaice.tools.generic;

import com.toedter.calendar.JDateChooser;
import fr.kaice.tools.PeriodGetter;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is a {@link JPanel} who display some {@link JDateChooser} in order to select a period of time.
 *
 * @author Raphaël Merkling
 * @version 1.0
 * @see JPanel
 * @see JDateChooser
 */
public class TimePeriodChooser extends JPanel {
    
    private static final int DAY = 0;
    private static final int WEEK = 1;
    private static final int MONTH = 2;
    private static final int YEAR = 3;
    private static final int ALL = 4;
    private final JDateChooser start;
    private final JDateChooser end;
    private final JComboBox<String> selec;
    
    /**
     * Create a new {@link TimePeriodChooser}.
     */
    public TimePeriodChooser(PeriodGetter getter) {
        start = new JDateChooser(new Date(), "dd/MM/yyyy");
        end = new JDateChooser(new Date(), "dd/MM/yyyy");
        
        final int h = start.getPreferredSize().height, w = 100;
        
        PropertyChangeListener listener = evt -> getter.setPeriod(getStart(), getEnd());
        start.addPropertyChangeListener(listener);
        start.setPreferredSize(new Dimension(w, h));
        end.addPropertyChangeListener(listener);
        end.setPreferredSize(new Dimension(w, h));
        
        selec = new JComboBox<>();
        selec.addItem("Aujourd'hui");
        selec.addItem("Cette semaine");
        selec.addItem("Ce mois");
        selec.addItem("Cette année");
        selec.addItem("Tout");
        selec.addActionListener(e -> updateDate(selec.getSelectedIndex()));
        
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
    
    /**
     * Return the beginning {@link Date} of the selected period.
     *
     * @return The beginning {@link Date} of the selected period.
     */
    public Date getStart() {
        return start.getDate();
    }
    
    /**
     * Return the end {@link Date} of the selected period.
     *
     * @return The end {@link Date} of the selected period.
     */
    public Date getEnd() {
        return end.getDate();
    }
    
    /**
     * Set a new period of time and update the {@link JDateChooser} with a pre-selection period.
     *
     * @param selection int - The code of the new pre-selection period.
     */
    private void updateDate(int selection) {
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.clear(Calendar.MINUTE);
        calStart.clear(Calendar.SECOND);
        Calendar calEnd = (Calendar) calStart.clone();
        switch (selection) {
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
        selec.setSelectedIndex(selection);
        calEnd.add(Calendar.SECOND, -1);
        start.setDate(calStart.getTime());
        end.setDate(calEnd.getTime());
    }
}
