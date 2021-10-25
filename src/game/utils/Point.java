package game.utils;

/**
 * Point structure
 *
 * @author Gorkovets Roman
 * @version 1.0
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public static Point getRandom(int maxWidth, int maxHeight) {
        int x = (int) (Math.random() * maxWidth);
        int y = (int) (Math.random() * maxHeight);
        return new Point(x, y);
    }

    public Point add(Point point) {
        Point result = new Point(this);
        result.x += point.x;
        result.y += point.y;
        return result;
    }

    @Override
    public int hashCode() {
        int prime1 = 31;
        int prime2 = 7;
        return (this.x + this.y) * Integer.parseInt(Integer.toString(this.x * prime1) + Integer.toString(this.y * prime2));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point point = (Point) obj;
        return this.x == point.x && this.y == point.y;
    }

    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
