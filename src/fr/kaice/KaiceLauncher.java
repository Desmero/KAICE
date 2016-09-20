package fr.kaice;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.Converter;
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
        if (args.length > 0) {
            KFilesParameters.setGlobalPath(args[0]);
        }
        File saveRep = new File(KFilesParameters.globalPath);
        if (!saveRep.exists()) {
            saveRep.mkdir();
        }
        if (args.length > 1 && args[1].equals("-r")) {
            for (int year = 15; year <= KaiceModel.getActualYear(); year++) {
                Converter.readUser(year);
            }
            Converter.readRawMaterial();
            Converter.readSoldProduct();
            Converter.readPurchasedProduct();
            Converter.readHistoric();
        } else {
            KaiceModel.getMemberCollection().deserialize(KaiceModel.getActualYear() - 1);
            KaiceModel.getMemberCollection().deserialize(KaiceModel.getActualYear());
            KaiceModel.getRawMatCollection().deserialize();
            KaiceModel.getSoldProdCollection().deserialize();
            KaiceModel.getPurchasedProdCollection().deserialize();
            KaiceModel.getHistoric().deserialize(KaiceModel.getActualYear());
        }
        if (args.length > 1 && args[1].equals("-s")) {
            for (int year = 15; year <= KaiceModel.getActualYear(); year++) {
                KaiceModel.getMemberCollection().saveText(year);
            }
            KaiceModel.getRawMatCollection().saveText();
            KaiceModel.getPurchasedProdCollection().saveText();
            KaiceModel.getSoldProdCollection().saveText();
            KaiceModel.getHistoric().saveText();
        } else {
//            KaiceModel.getHistoric().serializeAll();
            new MainWindow();
            KaiceModel.update(KaiceModel.ALL);
        }
        
    }
    
}
