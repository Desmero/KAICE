package fr.kaice.tools.generic;

import javax.swing.SpinnerNumberModel;

public class DMonetarySpinnerModel extends SpinnerNumberModel {

	public DMonetarySpinnerModel(double decal) {
		super(0.00, 0.00, null, decal);
	}

}