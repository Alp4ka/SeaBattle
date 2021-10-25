package game.utils;

import game.controls.GameField;
import game.models.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Gamefield generator is a tool to generate a random field with a specified config.
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see GameField
 */
public final class GameFieldGenerator {
    private static final int MAX_ITER = 100; // Maximal amount of tries to generate the field.
    private GameField _field;

    public GameFieldGenerator(GameConfiguration config) throws RuntimeException {
        _field = new GameField(config.width(), config.height());
        if (!tryGenerate(config)) {
            throw new RuntimeException("You can't place these ships on field due to its size.");
        }
    }

    /**
     * Checks whether it is possible to place ship in a certain place.
     *
     * @param ship - Ship to place.
     * @return true if you are alllowed to place the ship, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     * @see Ship
     */
    public boolean isAllowedToPlace(Ship ship) {
        ArrayList<Point> newShipVector = ship.getShipPointVector();
        ArrayList<Point> shipsOnFieldPoints = new ArrayList<>();
        for (int i = 0; i < _field.getShips().size(); ++i) {
            _field.getShips().get(i).getShipPointVector().stream().
                    map((p) -> _field.getAmbientOfPoint(p)).
                    forEach(shipsOnFieldPoints::addAll);
        }
        shipsOnFieldPoints = (ArrayList<Point>) shipsOnFieldPoints.stream().distinct().collect(Collectors.toList());
        for (Point shipPoint : newShipVector) {
            if (shipPoint.x < 0 || shipPoint.y < 0 ||
                    shipPoint.x >= _field.getWidth() || shipPoint.y >= _field.getHeight()) {
                return false;
            }
            if (shipsOnFieldPoints.contains(shipPoint)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Try to generate a gamefield with a specidied config.
     *
     * @param config - game configuration.
     * @return true if it is possible to generate a field with specified configurations.
     * @author Gorkovets Roman
     * @version 1.0
     * @see ShipBlock
     */
    public boolean tryGenerate(GameConfiguration config) {
        boolean isCorrect = true;
        for (int i = 0; i < MAX_ITER; ++i) {
            isCorrect = true;
            for (ShipTypes shipType : config.shipTypes()) {
                if (!addByShipType(shipType)) {
                    _field = new GameField(config.width(), config.height());
                    isCorrect = false;
                    break;
                }
            }
            if (isCorrect) {
                return true;
            }
        }
        return false;
    }

    public void placeShip(Ship ship) {
        _field.addShip(ship);
    }

    /**
     * Try to add ship on the field.
     *
     * @param newShip - ship to place.
     * @return true - if ship is placed successfully, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     * @see Ship
     */
    public boolean addShip(Ship newShip) {
        if (isAllowedToPlace(newShip)) {
            placeShip(newShip);
            return true;
        }
        return false;
    }

    /**
     * Randomly adds ship on a field.
     *
     * @newShip - initial ship.
     * @return true in case it is possible to place ship, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     * @see Ship
     */
    public boolean addShipRandom(Ship newShip) {
        int randomX, randomY;
        boolean randomOrientation;
        // Random start point.
        randomX = ((int) (Math.random() * _field.getWidth())) % _field.getWidth();
        randomY = ((int) (Math.random() * _field.getHeight())) % _field.getHeight();
        randomOrientation = ((int) (Math.random() * 2)) == 1;
        Point startPoint = new Point(randomX, randomY);
        newShip.setHead(startPoint);
        newShip.setOrientation(randomOrientation);

        // From point to right down angle.
        boolean firstIteration = true;
        for (int i = newShip.getHead().y; i < _field.getHeight(); ++i) {
            for (int j = firstIteration ? newShip.getHead().x : 0; j < _field.getWidth(); ++j) {
                newShip.setHead(new Point(j, i));
                newShip.setOrientation(!newShip.getOrientation());
                if (addShip(newShip)) {
                    return true;
                }
                newShip.setOrientation(!newShip.getOrientation());
                if (addShip(newShip)) {
                    return true;
                }
                firstIteration = false;
            }
        }
        // From paint to left up in case it was not succeed on prev step.
        for (int i = newShip.getHead().y; i >= 0; --i) {
            for (int j = firstIteration ? newShip.getHead().x : _field.getWidth() - 1; j >= 0; --j) {
                newShip.setHead(new Point(j, i));
                newShip.setOrientation(!newShip.getOrientation());
                if (addShip(newShip)) {
                    return true;
                }
                newShip.setOrientation(!newShip.getOrientation());
                if (addShip(newShip)) {
                    return true;
                }
                firstIteration = false;
            }
        }
        return false;
    }

    /**
     * Add ship randomly by its type.
     *
     * @param shipType - Type of ship.
     * @author Gorkovets Roman
     * @return true in case it is possible to place ship, false - otherwise.
     * @version 1.0
     * @see ShipTypes
     */
    public boolean addByShipType(ShipTypes shipType) {
        Point tempHead = new Point(-1, -1);
        Ship ship;
        switch (shipType) {
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

    public GameField build() {
        return _field;
    }


    public enum ShipTypes {
        Submarine,
        Destroyer,
        Cruiser,
        Battleship,
        Carrier
    }
}
