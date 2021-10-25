package game.main;

import game.controls.GameInstance;
import game.utils.GameConfiguration;

public class Main {
    public static void main(String[] args) {
        // 1 13 14 1 1 1 2 2 2 3 3 4 5 1
        // Represents as:
        // Recovery mode = true;
        // Width: 13
        // Height: 14
        // 4 Submarines
        // 3 Destroyers
        // 2 Cruisers
        // 1 Battleship
        // 1 Carrier

        GameConfiguration config = GameConfiguration.parseArgs(args);
        if (config == null) {
            try {
                config = GameInstance.getSetupManually(System.in);
            } catch (RuntimeException re) {
                System.out.println("Wrong input parameters:c");
                return;
            }
        }
        if (config == null) {
            System.out.println("Wrong input parameters:c");
            return;
        }

        System.out.println(config);
        try {
            GameInstance game = new GameInstance(config);
            game.run();
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
            return;
        } catch (Exception ex) {
            System.out.println("Something went wrong! Restart the game.");
        }
    }
}
