package game.controls;

import game.models.EmptyBlock;
import game.models.GameObject;
import game.models.Ship;
import game.models.ShipBlock;
import game.utils.Point;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Gamefield class describes a field with certain ships.
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see GameInstance
 * @see GameObject
 * @see game.utils.GameFieldGenerator
 * @see Player
 */
public final class GameField {
    public static final int MAX_FIELD_SIZE = 15;
    public static final int STANDARD_DAMAGE = ShipBlock.MAX_HP;

    private ArrayList<ArrayList<GameObject>> _field; // Field of game objects.
    private ArrayList<Ship> _ships; // All ships on map.
    private HashSet<Point> _shotsList; // Shots made by user.
    private int _fieldWidth; // Field width.
    private int _fieldHeight; // Field height.

    public GameField(int width, int height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0 || width > MAX_FIELD_SIZE || height > MAX_FIELD_SIZE) {
            throw new IllegalArgumentException("Width and height of game field should be in [1 : " +
                    MAX_FIELD_SIZE + "] bounds!");
        }
        _fieldWidth = width;
        _fieldHeight = height;
        _ships = new ArrayList<>();
        _shotsList = new HashSet<>();
        initializeField();
    }

    /**
     * Field intitialization with empty blocks.
     *
     * @author Gorkovets Roman
     * @version 1.0
     */
    private void initializeField() {
        _field = new ArrayList<>();
        for (int i = 0; i < _fieldHeight; ++i) {
            _field.add(new ArrayList<>());
            for (int j = 0; j < _fieldWidth; ++j) {
                _field.get(i).add(new EmptyBlock());
            }
        }
    }

    /**
     * Check whether the point is in gamefild bounds.
     *
     * @param point - taget point.
     * @return true - whether the point is in bounds, otherwise - false.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public boolean isInBounds(Point point) {
        return point.x >= 0 || point.y >= 0 || point.x < _fieldWidth || point.y < _fieldHeight;
    }

    /**
     * Check whether at least one ship is alive.
     *
     * @return true - whether the field has existing ships, otherwise - false.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public boolean isAlive() {
        return _ships.stream().filter(Ship::isAlive).count() > 0;
    }

    /**
     * Returns square area around point.
     *
     * @param point - Point.
     * @return ArrayList of Point surrounding the central one.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public ArrayList<Point> getAmbientOfPoint(Point point) {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                Point temp = new Point(point.x + i, point.y + j);
                if (temp.x >= 0 && temp.x < _fieldWidth && temp.y >= 0 && temp.y < _fieldHeight) {
                    result.add(temp);
                }
            }
        }
        return result;
    }

    /**
     * Check whether the point was attacked at.
     *
     * @param position - Point.
     * @return true if the position was under attack, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     */
    @Deprecated
    public boolean isAttackedAt(Point position) {
        boolean result = false;
        for (Point shot : _shotsList) {
            if (shot.equals(position)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Do standard attack at the position.
     *
     * @return true if you hit, false - otherwise.
     * @throws IllegalArgumentException
     * @author Gorkovets Roman
     * @version 1.0
     */
    public boolean attackAt(Point position) throws IllegalArgumentException {
        if (!isInBounds(position)) {
            throw new IllegalArgumentException("You can't attack outside the gamefield!");
        }
        _shotsList.add(position);
        if (isAttackable(position)) {
            _shotsList.add(position);
            ShipBlock target = ((ShipBlock) _field.get(position.y).get(position.x));
            target.getDamage(STANDARD_DAMAGE);
            onHit(target);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Do torpedo attack at the position.
     *
     * @param position - point.
     * @return true if you hit, false - otherwise.
     * @throws IllegalArgumentException
     * @author Gorkovets Roman
     * @version 1.0
     */
    public boolean attackTorpedoAt(Point position) throws IllegalArgumentException {
        if (!isInBounds(position)) {
            throw new IllegalArgumentException("You can't attack outside the gamefield!");
        }
        System.out.println("Torpedo!");
        _shotsList.add(position);
        if (isAttackable(position)) {
            ShipBlock target = ((ShipBlock) _field.get(position.y).get(position.x));
            _shotsList.addAll(target.getParent().getShipPointVector());
            target.getParent().suicide();
            onHit(target);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add ship to a field.
     *
     * @param newShip - new ship.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public void addShip(Ship newShip) {
        _ships.add(newShip);
        ArrayList<Point> vector = newShip.getShipPointVector();
        for (int i = 0; i < vector.size(); ++i) {
            GameObject o = newShip.getShipBlocksVector().get(i);
            _field.get(vector.get(i).y).set(vector.get(i).x, o);
        }
    }

    public GameObject getAt(Point position) {
        return _field.get(position.y).get(position.x);
    }

    /**
     * Check whether the target is a part of ship and it's alive.
     *
     * @param point - Point.
     * @return true if you can hit this point, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public boolean isAttackable(Point point) {
        if (_field.get(point.y).get(point.x) instanceof ShipBlock) {
            if (((ShipBlock) _field.get(point.y).get(point.x)).isAlive()) {
                return true;
            }
        }
        return false;
    }

    public int getWidth() {
        return _fieldWidth;
    }

    public int getHeight() {
        return _fieldHeight;
    }

    public ArrayList<Ship> getShips() {
        return _ships;
    }

    /**
     * Call it in case u hit smth.
     *
     * @param shipBlock - hitted shipBlock.
     * @author Gorkovets Roman
     * @version 1.0
     * @see ShipBlock
     */
    public void onHit(ShipBlock shipBlock) {
        Ship parent = shipBlock.getParent();
        System.out.println(parent.represent());
        if (parent.isAlive()) {
            System.out.println("HITTED");
        } else {
            System.out.println("DEAD");
        }
    }

    public HashSet<Point> getShots() {
        return _shotsList;
    }

    /**
     * Draw field, FP look.
     *
     * @author Gorkovets Roman
     * @version 1.0
     */
    public void draw() {
        for (int i = 0; i < _fieldWidth; ++i) {
            System.out.print((char) ('A' + i) + "\t");
        }
        System.out.println();

        for (int i = 0; i < _fieldHeight; ++i) {
            for (int j = 0; j < _fieldWidth; ++j) {
                System.out.print(_field.get(i).get(j).represent());
                System.out.print("\t");
            }
            System.out.print("\b  " + i);
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Draw field, TP look.
     *
     * @author Gorkovets Roman
     * @version 1.0
     */
    public void drawHidden() {
        for (int i = 0; i < _fieldWidth; ++i) {
            System.out.print((char) ('A' + i) + "\t");
        }
        System.out.println();

        for (int i = 0; i < _fieldHeight; ++i) {
            for (int j = 0; j < _fieldWidth; ++j) {
                Point current = new Point(j, i);
                if (_shotsList.contains(current)) {
                    System.out.print(_field.get(i).get(j).represent() + "\t");
                } else {
                    System.out.print(EmptyBlock.INV + "\t");
                }
            }
            System.out.print("\b  " + i);
            System.out.println();
        }
        System.out.println();
    }
}
