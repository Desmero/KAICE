package fr.kaice;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.view.MainWindow;

import java.io.File;

/**
 * The class KaiceLauncher is the starting point of the program KAICE.
 * (KAICE Automatise l'Intendance de CENS Éfficacement)
 *
 * @author Raphaël Merkling
 * @version 2.0
 */
public abstract class KaiceLauncher {
    
    public static void main(String[] args) {
        KFilesParameters.setGlobalPath("/home/merkling/.KAICE/");
        File saveRep = new File(KFilesParameters.globalPath);
        if (!saveRep.exists()) {
            saveRep.mkdir();
        }
        new MainWindow();
        KaiceModel.update();
    }

}
