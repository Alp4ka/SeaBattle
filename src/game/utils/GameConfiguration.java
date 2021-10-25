package game.utils;

import game.models.ShipBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dataclass for game configuration.
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see game.controls.GameField
 * @see game.controls.GameInstance
 */
public record GameConfiguration(int width, int height, boolean recoveryMode,
                                ArrayList<GameFieldGenerator.ShipTypes> shipTypes) {
    /**
     * Parse game configuration from input args.
     *
     * @param args - input arguments.
     * @return Game configuration instance.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public static GameConfiguration parseArgs(String[] args) {
        try {
            List<String> argsList = Arrays.stream(args).collect(Collectors.toList());
            ArrayList<GameFieldGenerator.ShipTypes> newShipTypes = new ArrayList<>();
            boolean recoveryMode = Integer.parseInt(argsList.get(0)) == 1;
            int newWidth = Integer.parseInt(argsList.get(1));
            int newHeight = Integer.parseInt(argsList.get(2));
            for (int i = 3; i < argsList.size(); ++i) {
                GameFieldGenerator.ShipTypes shipType = GameFieldGenerator.ShipTypes.
                        values()[Integer.parseInt(argsList.get(i))];
                newShipTypes.add(shipType);
            }
            return new GameConfiguration(newWidth, newHeight, recoveryMode, newShipTypes);
        } catch (Exception ex) {
            return null;
        }
    }
}
