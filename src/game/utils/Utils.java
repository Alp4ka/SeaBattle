package game.utils;

import java.security.KeyPair;

public class Utils {
    public static Point coordToPoint(char symbol, int digit){
        return new Point(Character.toLowerCase(symbol) - 'a', digit);
    }
}
