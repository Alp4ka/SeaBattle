package game.utils;

import game.controls.GameField;
import game.models.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameFieldGenerator {
    private GameField _field;
    public boolean isAllowedToPlace(IBattleship ship){
        ArrayList<Point> newShipVector = ship.getShipPointVector();
        ArrayList<Point> shipsOnFieldPoints = new ArrayList<>();
        for(int i =0; i < _field.getShips().size(); ++i){
            _field.getShips().get(i).getShipPointVector().stream().
                    map((p)->getAmbientOfPoint(p)).
                    forEach(shipsOnFieldPoints::addAll);
        }
        shipsOnFieldPoints = (ArrayList<Point>) shipsOnFieldPoints.stream().distinct().collect(Collectors.toList());
        for(Point shipPoint: newShipVector){
            if(shipPoint.x < 0 || shipPoint.y < 0 ||
                    shipPoint.x >= _field.getWidth() || shipPoint.y >= _field.getHeight()){
                return false;
            }
            if(shipsOnFieldPoints.contains(shipPoint)){
                return false;
            }
        }
        return true;
    }

    public enum ShipTypes{
        Submarine,
        Destroyer,
        Cruiser,
        Battleship,
        Carrier
    }

    public GameFieldGenerator(int width, int height){
        _field = new GameField(width, height);
    }

    public void placeShip(IBattleship ship){
        _field.addShip(ship);
    }

    // TODO
    public boolean addShip(IBattleship newShip){
        if(isAllowedToPlace(newShip)){
            placeShip(newShip);
            return true;
        }
        return false;
    }

    public boolean placeRandom(IBattleship newShip){
        return true;
    }

    public boolean addByShipType(ShipTypes shipType){
        int randomX, randomY;
        boolean randomOrientation;
        randomX = (int) (Math.random() * GameField.MAX_FIELD_SIZE);
        randomY = (int) (Math.random() * GameField.MAX_FIELD_SIZE);
        randomOrientation = ((int)(Math.random() * 2)) == 1;

        Point startPoint = new Point(randomX, randomY);
        IBattleship ship;
        switch(shipType){
            case Submarine:
                ship = new Submarine(startPoint, randomOrientation);
                break;
            case Destroyer:
                ship = new Destroyer(startPoint, randomOrientation);
                break;
            case Cruiser:
                ship = new Cruiser(startPoint, randomOrientation);
                break;
            case Battleship:
                ship = new Battleship(startPoint, randomOrientation);
                break;
            case Carrier:
                ship = new Carrier(startPoint, randomOrientation);
                break;
            default:
                return false;
        }

        //todo
        return true;
    }

    // false if ure not able to place this ship;
    public boolean placeOnRandomPosition(){
        return false;
    }

    //Returns square area around point.
    public ArrayList<Point> getAmbientOfPoint(Point point){
        ArrayList<Point> result = new ArrayList<>();
        for(int i = -1; i <= 1; ++i){
            for(int j = -1; j <= 1; ++j){
                try{ result.add(new Point(point.x + i, point.y + j)); }
                catch(Exception ex){}
            }
        }
        result.add(new Point(point));
        return result;
    }


    public GameField build(){
        return _field;
    }
}
