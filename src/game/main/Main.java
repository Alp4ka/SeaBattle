package game.main;

import game.models.Battleship;
import game.models.Carrier;
import game.models.IBattleship;
import game.utils.Point;

public class Main {
    public static void main(String[] args) {
        Battleship a = new Battleship(new Point(1, 1), true);
        IBattleship b = new Carrier(new Point(1, 2), false);

        System.out.println(a.represent());
        System.out.println(b.represent());
    }
}
