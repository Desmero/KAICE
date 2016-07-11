package fr.kaice.tools;

import java.awt.Color;
import java.util.Random;

public abstract class DFunction {

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
		Random random = new Random(); 
		int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }	

	public static Color colorfusion(Color color1, Color color2) {
		int r, g, b;
		r = (int) Math.sqrt((Math.pow((double) color1.getRed(), 2) + Math.pow((double) color2.getRed(), 2)) / 2);
		g = (int) Math.sqrt((Math.pow((double) color1.getGreen(), 2) + Math.pow((double) color2.getGreen(), 2)) / 2);
		b = (int) Math.sqrt((Math.pow((double) color1.getBlue(), 2) + Math.pow((double) color2.getBlue(), 2)) / 2);
		return new Color(r, g, b);
	}

}
