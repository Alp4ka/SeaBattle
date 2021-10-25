package game.utils;

import game.controls.GameField;
import java.util.HashSet;

public final class Utils {
    public static Point coordToPoint(char symbol, int digit) {
        return new Point(Character.toLowerCase(symbol) - 'a', digit);
    }
    public static HashSet<Point> fieldToPoints(GameField field){
        var result = new HashSet<Point>();
        for(int i =0; i < field.getWidth(); ++i){
            for(int j =0; j < field.getHeight(); ++j){
                result.add(new Point(i, j));
            }
        }
        return result;
    }
}
