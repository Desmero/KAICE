package fr.kaice.tools.generic;

import java.awt.*;
import java.util.Random;

/**
 * This class is a collection a function I use in my projects. There ars not necessarily used, sorted or having
 * something in common each others.
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
public abstract class DFunction {
    
    /**
     * The Euro '€' char.
     */
    public static final char EURO = '\u20ac';
    
    /**
     * Function found on the Internet. <br/>
     * Return a random element of an enum. <br/>
     * TODO find who use the function.
     *
     * @param clazz I don't remember...
     * @param <T>   I don't remember...
     * @return A random element of the given enum.
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        Random random = new Random();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    
    /**
     * Fuse 2 {@link Color} correctly, and return the fused color.
     *
     * @param color1
     *          {@link Color} - The first color to fuse.
     * @param color2
     *          {@link Color} - The second color to fuse.
     * @return The fused color.
     */
    public static Color colorFusion(Color color1, Color color2) {
        int r, g, b;
        r = (int) Math.sqrt((Math.pow((double) color1.getRed(), 2) + Math.pow((double) color2.getRed(), 2)) / 2);
        g = (int) Math.sqrt((Math.pow((double) color1.getGreen(), 2) + Math.pow((double) color2.getGreen(), 2)) / 2);
        b = (int) Math.sqrt((Math.pow((double) color1.getBlue(), 2) + Math.pow((double) color2.getBlue(), 2)) / 2);
        return new Color(r, g, b);
    }
    
}
