package fr.kaice;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.Converter;
import fr.kaice.tools.KFilesParameters;
import fr.kaice.view.MainWindow;

import java.io.File;

/**
 * Created by Raphael on 19/08/2016.
 */
public class Convert {

    public static void main(String[] args) {
        KFilesParameters.setGlobalPath(args[0]);
        File saveRep = new File(KFilesParameters.globalPath);
        Converter.readUser();
    }

}
