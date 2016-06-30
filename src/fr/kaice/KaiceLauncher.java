package fr.kaice;

import fr.kaice.model.KaiceModel;
import fr.kaice.tools.Reader;
import fr.kaice.view.MainWindow;

/**
 * The class KaincLauncher is the starting point of the program KAICE.
 * 
 * @author Raph
 * @version 2.0
 *
 */
public abstract class KaiceLauncher {

	public static void main(String[] args) {
		new MainWindow();
		Reader.readAll();
		KaiceModel.update();
	}

}
