package game.main;

import game.controls.GameInstance;
import game.models.Battleship;
import game.models.Cruiser;
import game.models.Destroyer;
import game.models.Submarine;
import game.utils.GameConfiguration;
import game.utils.GameFieldGenerator;
import game.utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    enum pizda{
        pizda0,
        pizda1
    }
    public static void main(String[] args) {
        // 13 14 1 1 1 2 2 2 3 3 4 5 1
        // represents as:
        // width: 13
        // height: 14
        // 4 Submarines
        // 3 Destroyers
        // 2 Cruisers
        // 1 Battleship
        // 1 Carrier


        GameConfiguration config = GameConfiguration.parseArgs(args);
        if(config == null){
            config = GameInstance.getSetupManually(System.in);
        }
        if(config == null){
            System.out.println("Wrong input parameters:c");
            return;
        }
        System.out.println(config);
        GameInstance game = new GameInstance(config);
        game.run();

        /*Scanner keyboard = new Scanner(System.in);
        GameFieldGenerator generator = new GameFieldGenerator(15, 12);

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
        field.attackTorpedoAt(Utils.coordToPoint(h, v));
        field.DrawHidden();*/
    }
}
