package game.main;

import game.controls.GameField;
import game.utils.GameFieldGenerator;
import game.utils.Utils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        GameFieldGenerator generator = new GameFieldGenerator(10, 10);

        generator.addByShipType(GameFieldGenerator.ShipTypes.Cruiser);
        generator.addByShipType(GameFieldGenerator.ShipTypes.Cruiser);

        generator.addByShipType(GameFieldGenerator.ShipTypes.Destroyer);
        generator.addByShipType(GameFieldGenerator.ShipTypes.Destroyer);
        generator.addByShipType(GameFieldGenerator.ShipTypes.Destroyer);

        generator.addByShipType(GameFieldGenerator.ShipTypes.Submarine);
        generator.addByShipType(GameFieldGenerator.ShipTypes.Submarine);
        generator.addByShipType(GameFieldGenerator.ShipTypes.Submarine);
        generator.addByShipType(GameFieldGenerator.ShipTypes.Submarine);

        generator.addByShipType(GameFieldGenerator.ShipTypes.Battleship);

        GameField field = generator.build();
        field.Draw();
        char h;
        int v;
        h = keyboard.next().charAt(0);
        v = keyboard.nextInt();
        field.attackAt(Utils.coordToPoint(h, v));
        field.Draw();
    }
}
