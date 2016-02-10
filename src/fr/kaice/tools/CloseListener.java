package fr.kaice.tools;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseListener implements ActionListener {

	protected Window window;

	public CloseListener(Window window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.dispose();
	}

}
