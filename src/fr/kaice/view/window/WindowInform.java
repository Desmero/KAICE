package fr.kaice.view.window;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import fr.kaice.tools.generic.CloseListener;

public class WindowInform extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WindowInform(String windowName, boolean block, JPanel mainPanel) {
		super((JFrame) null, windowName, block);
		this.setResizable(false);

		JPanel ctrl = new JPanel(new BorderLayout());
		JPanel ctrlButtons = new JPanel();
		
		JButton ok = new JButton("ok");
		ok.addActionListener(new CloseListener(this));
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(ctrl, BorderLayout.SOUTH);
		
		ctrl.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
		ctrl.add(ctrlButtons, BorderLayout.CENTER);
		ctrlButtons.add(ok);
		
		pack();
		int x = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (this.getWidth() / 2);
		int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- (this.getSize().getHeight() / 2));
		this.setLocation(x, y);
		setVisible(true);
	}
	
}
