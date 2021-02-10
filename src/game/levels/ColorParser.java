package game.levels;

import java.awt.Color;
import java.lang.reflect.Field;


/**
 * The type Color parser.
 */
public class ColorParser {
    private Color color;

    /**
     * Color from string java . awt . color.
     *
     * @param s the s
     * @return the java . awt . color
     */
// parse color definition and return the specified color.
    public java.awt.Color colorFromString(String s) {
        try {
            Field field = Class.forName("java.awt.Color").getField(s);
            color = (Color) field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }
        return color;
    }

}
