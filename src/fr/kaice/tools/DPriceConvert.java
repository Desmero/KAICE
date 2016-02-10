package fr.kaice.tools;

public class DPriceConvert {

	public static int doubleToInt(double value) {
		return (int) Math.round((double) value*100);
	}
	
	public static double intToDouble(int value) {
		return (double) value / 100;
	}
	
}
