package game.utils;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point(Point point){
        this.x = point.x;
        this.y = point.y;
    }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public String toString(){
        return "(" + x + ";" + y + ")";
    }
}
