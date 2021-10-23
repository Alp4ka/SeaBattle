package game.utils;

import java.util.concurrent.atomic.AtomicInteger;

public final class Utils {
    public static Point coordToPoint(char symbol, int digit) {
        return new Point(Character.toLowerCase(symbol) - 'a', digit);
    }
}
