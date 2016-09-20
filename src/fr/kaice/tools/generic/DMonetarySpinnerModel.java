package fr.kaice.tools.generic;

import javax.swing.*;

/**
 * This class is a pre-made {@link SpinnerNumberModel} used by {@link DMonetarySpinner}. The number type is double.
 * The default and minimum values are 0, there is no maximum value, and the step size must be define.
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
class DMonetarySpinnerModel extends SpinnerNumberModel {
    
    /**
     * Create a pre-made {@link SpinnerNumberModel} for {@link DMonetarySpinner}.
     *
     * @param stepSize double - The difference between elements of the sequence.
     */
    DMonetarySpinnerModel(double stepSize) {
        super(0.00, null, null, stepSize);
    }
    
}
