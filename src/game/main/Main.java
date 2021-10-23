package game.main;

import game.controls.GameField;
import game.models.*;
import game.utils.GameFieldGenerator;
import game.utils.Point;

public class Main {
    public static void main(String[] args) {
        Battleship a = new Battleship(new Point(1, 1), true);
        Submarine b = new Submarine(new Point(5, 1), false);
        System.out.println(b.represent());
        GameFieldGenerator generator = new GameFieldGenerator(10, 10);
        generator.addShip(a);
        generator.addShip(b);
        GameField field = generator.build();
        field.Draw();
    }
}
