package game.models;

import game.utils.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Baseship class realisation implementing ship.
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Ship
 */
public class Baseship implements Ship {
    private Point _head; // Head point of ship.
    private int _shipLength; // Length of ship.
    private boolean _isHorizontal; // Boolean declares the ship is horizontal or not.
    private List<ShipBlock> _shipArray; // Ship blocks.

    public Baseship(Point head, int length, boolean isHorizontal) {
        _head = new Point(head);
        _shipLength = length;
        _isHorizontal = isHorizontal;
        initializeShipArray();
    }

    /**
     * Initialize ship array by ship blocks using head point, orientation and ship length.
     *
     * @author Gorkovets Roman
     * @version 1.0
     * @see ShipBlock
     */
    private void initializeShipArray() {
        _shipArray = new ArrayList<ShipBlock>(_shipLength);
        for (int i = 0; i < _shipLength; ++i) {
            _shipArray.add(new ShipBlock(this));
        }
    }

    /**
     * Getter for _shipArray.
     *
     * @author Gorkovets Roman
     * @version 1.0
     * @see Baseship#_shipArray
     */
    public List<ShipBlock> getShipBlocksVector() {
        return _shipArray;
    }

    /**
     * Returns state of ship (Alive/Dead).
     *
     * @return true if the ship is alive, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public boolean isAlive() {
        int sum = 0;
        for (int i = 0; i < _shipArray.size(); ++i) {
            sum += _shipArray.get(i).getHP();
        }
        return sum > 0;
    }

    /**
     * Get damage at the specified part of ship.
     *
     * @param index  - index of part (int).
     * @param damage - damage (int).
     * @return true if you can hit this part.
     * @author Gorkovets Roman
     * @version 1.0
     */
    @Deprecated
    public boolean getDamageAt(int index, int damage) {
        if (!_shipArray.get(index).isAlive()) {
            return false;
        } else {
            _shipArray.get(index).getDamage(damage);
            return true;
        }
    }

    /**
     * Get health at index.
     *
     * @param index - index of part (int).
     * @return Health of ship block (int).
     * @author Gorkovets Roman
     * @version 1.0
     */
    public int getHealthAt(int index) {
        return _shipArray.get(index).getHP();
    }

    /**
     * Getter for _head.
     *
     * @return Point of head block.
     * @author Gorkovets Roman
     * @version 1.0
     * @see Baseship#_head
     */
    public Point getHead() {
        return _head;
    }

    /**
     * Setter for _head.
     *
     * @author Gorkovets Roman
     * @version 1.0
     * @see Baseship#_head
     */
    public void setHead(Point value) {
        _head = value;
    }

    /**
     * Getter for _isHorizontal.
     *
     * @return true if ship is horizontal, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     * @see Baseship#_isHorizontal
     */
    public boolean getOrientation() {
        return _isHorizontal;
    }

    /**
     * Setter for _isHorizontal.
     *
     * @author Gorkovets Roman
     * @version 1.0
     * @see Baseship#_isHorizontal
     */
    public void setOrientation(boolean value) {
        _isHorizontal = value;
    }

    /**
     * Getter for _shipLength.
     *
     * @return Ship length (int).
     * @author Gorkovets Roman
     * @version 1.0
     * @see Baseship#_shipLength
     */
    public int getShipLength() {
        return _shipLength;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < _shipArray.size(); ++i) {
            result += _shipArray.get(i).represent();
        }
        return result;
    }

    /**
     * Get ship blocks represented as their coordinates.
     *
     * @return ArrayList of Points.
     * @author Gorkovets Roman
     * @version 1.0
     * @see Point
     */
    public ArrayList<Point> getShipPointVector() {
        ArrayList<Point> result = new ArrayList<>(_shipLength);
        Point current = new Point(_head);
        Point shift = new Point(0, 0);
        Point shiftValue = _isHorizontal ? new Point(1, 0) : new Point(0, 1);
        for (int i = 0; i < _shipLength; ++i) {
            result.add(current.add(shift));
            shift = shift.add(shiftValue);
        }
        return result;
    }

    /**
     * Kill all ship blocks.
     *
     * @author Gorkovets Roman
     * @version 1.0
     * @see ShipBlock
     */
    public void suicide() {
        for (ShipBlock block : _shipArray) {
            block.suicide();
        }
    }

    /**
     * Recover all ship blocks.
     *
     * @author Gorkovets Roman
     * @version 1.0
     * @see ShipBlock
     */
    public void recover() {
        for (var block : getShipBlocksVector()) {
            block.recover();
        }
    }

    /**
     * Get summary health points of ship.
     *
     * @return Summary health points of all ship blocks (int).
     * @author Gorkovets Roman
     * @version 1.0
     * @see ShipBlock
     */
    public int getHP() {
        int sum = 0;
        for (var block : _shipArray) {
            sum += block.getHP();
        }
        return sum;
    }

    /**
     * Maximal possible amount of health points for the specified ship.
     *
     * @return Maximal value of health points for the specified ship(int).
     * @author Gorkovets Roman
     * @version 1.0
     */
    public int maxHP() {
        return maxHP() * _shipArray.size();
    }

    /**
     * Return the state of ship(Hit/Fully recovered)
     *
     * @return true - if the ship is hit, false - otherwise.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public boolean isHitted() {
        return isAlive() && getHP() < maxHP();
    }

    /**
     * Represents ship as string.
     *
     * @return String representation of ship.
     * @author Gorkovets Roman
     * @version 1.0
     */
    public String represent() {
        return "Status: " + (isAlive() ? "Alive" : "Sink")
                + "; View: " + toString() +
                "; Position: " + _head + " " + (_isHorizontal ? "Horizontal" : "Vertical");
    }
}
