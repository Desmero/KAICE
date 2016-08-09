package fr.kaice;

import fr.kaice.model.KaiceModel;
import fr.kaice.view.MainWindow;

/**
 * The class KaiceLauncher is the starting point of the program KAICE.
 * (KAICE Automatise l'Intendance de CENS Éfficacement)
 *
 * @author Raphaël Merkling
 * @version 2.0
 */
public abstract class KaiceLauncher {
    
    public static void main(String[] args) {
        new MainWindow();
        KaiceModel.update();
    }

}
