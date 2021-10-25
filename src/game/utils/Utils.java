package game.utils;

import game.controls.GameField;
import java.util.HashSet;

/**
 * Utils methods.
 *
 * @author Gorkovets Roman
 * @version 1.0
 */
public final class Utils {
    /**
     * Translate human-like coordinates representation to a Point instance.
     *
     * @param symbol - char (horizontal coordinate).
     * @param digit - int (vertical coordinate).
     * @return Point representation of human-like coordinate.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public static Point coordToPoint(char symbol, int digit) {
        return new Point(Character.toLowerCase(symbol) - 'a', digit);
    }

    /**
     * Get point-like representation of field.
     *
     * @param field - field to represent.
     * @return Hashset of points.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public static HashSet<Point> fieldToPoints(GameField field) {
        var result = new HashSet<Point>();
        for (int i = 0; i < field.getWidth(); ++i) {
            for (int j = 0; j < field.getHeight(); ++j) {
                result.add(new Point(i, j));
            }
        }
        return result;
    }
}
