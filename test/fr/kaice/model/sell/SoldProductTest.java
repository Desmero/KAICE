package fr.kaice.model.sell;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.kaice.model.sell.SoldProduct.prodType;

public class SoldProductTest {

	public SoldProduct sp;

	@Before
	public void before() {
		sp = new SoldProduct("Test", 100, prodType.MISC);
	}

	// ----- setRawMaterial -----

	@Test
	public void testRightSetRawMaterial() {
		sp.setRawMaterial(1, 5);
		int ans = sp.getRawMaterial(1);
		if (ans != 5) {
			fail("setRawMaterial(int, int) error, set to " + ans + "instead of " + 5);
		}
	}

	@Test
	public void testBondarySetRawMaterialZero() {
		sp.setRawMaterial(1, 0);
		int ans = sp.getRawMaterial(1);
		if (ans != 0) {
			fail("setRawMaterial(int, int) error, set to " + ans + "instead of " + 5);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBondarySetRawMaterialNegative() {
		sp.setRawMaterial(1, -1);
		int ans = sp.getRawMaterial(1);
		if (ans == -1) {
			fail("setRawMaterial(int, int) error, set to " + ans + "instead of returning an IllegalArgumentException");
		}
	}

}
