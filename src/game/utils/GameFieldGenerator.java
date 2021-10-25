package game.utils;

import game.controls.GameField;
import game.models.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class GameFieldGenerator {
    private GameField _field;
    public boolean isAllowedToPlace(Ship ship){
        ArrayList<Point> newShipVector = ship.getShipPointVector();
        ArrayList<Point> shipsOnFieldPoints = new ArrayList<>();
        for(int i =0; i < _field.getShips().size(); ++i){
            _field.getShips().get(i).getShipPointVector().stream().
                    map((p)->_field.getAmbientOfPoint(p)).
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

    public GameFieldGenerator(GameConfiguration config) throws RuntimeException{
        _field = new GameField(config.width(), config.height());
        for(ShipTypes shipType: config.shipTypes()){
            if(!addByShipType(shipType)){
                if(!addByShipType(shipType)){
                    throw new RuntimeException("Cant place " + shipType + " on map!");
                }
            }
        }
    }

    public void placeShip(Ship ship){
        _field.addShip(ship);
    }

    // TODO
    public boolean addShip(Ship newShip){
        if(isAllowedToPlace(newShip)){
            placeShip(newShip);
            return true;
        }
        return false;
    }

    // false if u re not able
    public boolean addShipRandom(Ship newShip){
        int randomX, randomY;
        boolean randomOrientation;
        randomX = ((int) (Math.random() * _field.getWidth())) % _field.getWidth();
        randomY = ((int) (Math.random() * _field.getHeight())) % _field.getHeight();
        randomOrientation = ((int)(Math.random() * 2)) == 1;
        Point startPoint = new Point(randomX, randomY);
        newShip.setHead(startPoint);
        newShip.setOrientation(randomOrientation);

        //from point to right down.
        boolean firstIteration = true;
        for(int i = newShip.getHead().y ;i < _field.getHeight(); ++i){
            for(int j = firstIteration ? newShip.getHead().x : 0 ;j < _field.getWidth(); ++j){
                newShip.setHead(new Point(j ,i));
                newShip.setOrientation(!newShip.getOrientation());
                if(addShip(newShip)){ return true; }
                newShip.setOrientation(!newShip.getOrientation());
                if(addShip(newShip)){ return true; }
                firstIteration = false;
            }
        }
        //from paint to left up in case it was not succeed on prev step.
        for(int i = newShip.getHead().y ;i >= 0; --i){
            for(int j = firstIteration ? newShip.getHead().x : _field.getWidth()-1 ;j >= 0; --j){
                newShip.setHead(new Point(j ,i));
                newShip.setOrientation(!newShip.getOrientation());
                if(addShip(newShip)){ return true; }
                newShip.setOrientation(!newShip.getOrientation());
                if(addShip(newShip)){ return true; }
                firstIteration = false;
            }
        }
        return false;
    }

    public boolean addByShipType(ShipTypes shipType){
        Point tempHead = new Point(-1, -1);
        Ship ship;
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


    public GameField build(){
        return _field;
    }
}
