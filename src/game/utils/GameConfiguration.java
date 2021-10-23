package game.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record GameConfiguration(int width, int height, ArrayList<GameFieldGenerator.ShipTypes> shipTypes) {
    public static GameConfiguration parseArgs(String[] args){
        try {
            List<String> argsList = Arrays.stream(args).collect(Collectors.toList());
            ArrayList<GameFieldGenerator.ShipTypes> newShipTypes = new ArrayList<>();
            int newWidth = Integer.parseInt(argsList.get(0));
            int newHeight = Integer.parseInt(argsList.get(1));
            for(int i = 2; i < argsList.size(); ++i){
                GameFieldGenerator.ShipTypes shipType = GameFieldGenerator.ShipTypes.
                                                        values()[Integer.parseInt(argsList.get(i))];
                newShipTypes.add(shipType);
            }
            return new GameConfiguration(newWidth, newHeight, newShipTypes);
        }
        catch (Exception ex){
            return null;
        }
    }
}
