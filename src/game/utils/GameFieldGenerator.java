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

    // false if u re not able
    public boolean addShipRandom(IBattleship newShip){
        int randomX, randomY;
        boolean randomOrientation;
        randomX = ((int) (Math.sqrt(Math.random() * Math.random())  * GameField.MAX_FIELD_SIZE)) % GameField.MAX_FIELD_SIZE;
        randomY = ((int) (Math.sqrt(Math.random() * Math.random())  * GameField.MAX_FIELD_SIZE)) % GameField.MAX_FIELD_SIZE;
        randomOrientation = ((int)(Math.sqrt(Math.random() * Math.random())  * 2)) == 1;
        Point startPoint = new Point(randomX, randomY);
        newShip.setHead(startPoint);
        newShip.setOrientation(randomOrientation);

        //from point to right down.
        for(int i = newShip.getHead().y ;i < _field.getHeight(); ++i){
            for(int j = newShip.getHead().x ;j < _field.getWidth(); ++j){
                newShip.setHead(new Point(j ,i));
                if(addShip(newShip)){ return true; }
                newShip.setOrientation(!newShip.getOrientation());
                if(addShip(newShip)){ return true; }
            }
        }
        //from paint to left up in case it was not succeed on prev step.
        for(int i = newShip.getHead().y ;i >= 0; --i){
            for(int j = newShip.getHead().x ;j >= 0; --j){
                newShip.setHead(new Point(j ,i));
                if(addShip(newShip)){ return true; }
                newShip.setOrientation(!newShip.getOrientation());
                if(addShip(newShip)){ return true; }
            }
        }
        return false;
    }

    public boolean addByShipType(ShipTypes shipType){
        Point tempHead = new Point(-1, -1);
        IBattleship ship;
        switch(shipType){
            case Submarine:
                ship = new Submarine(tempHead, false);
                break;
            case Destroyer:
                ship = new Destroyer(tempHead, false);
                break;
            case Cruiser:
                ship = new Cruiser(tempHead, false);
                break;
            case Battleship:
                ship = new Battleship(tempHead, false);
                break;
            case Carrier:
                ship = new Carrier(tempHead, false);
                break;
            default:
                return false;
        }
        boolean result = addShipRandom(ship);
        return result;
    }


    //Returns square area around point.
    public ArrayList<Point> getAmbientOfPoint(Point point){
        ArrayList<Point> result = new ArrayList<>();
        for(int i = -1; i <= 1; ++i){
            for(int j = -1; j <= 1; ++j){
                Point temp = new Point(point.x + i, point.y + j);
                if(temp.x >= 0 && temp.x < _field.getWidth() && temp.y >= 0 && temp.y < _field.getHeight()){
                    result.add(temp);
                }
            }
        }
        return result;
    }


    public GameField build(){
        return _field;
    }
}
