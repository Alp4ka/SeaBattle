package game.controls;

/**
 * Basic player realisation.
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Controllable
 */
public class Player implements Controllable {
    public static final int TORPEDOS_START_AMOUNT = 1;
    private String _name;
    private int _torpedosAmount;

    public Player() {
        _name = "Real player";
        _torpedosAmount = TORPEDOS_START_AMOUNT;
    }

    public String getName() {
        return _name;
    }

    public void setName(String value) {
        _name = value;
    }

    public int getTorpedosCount() {
        return _torpedosAmount;
    }

    public void setTorpedosCount(int value) {
        if (value < 0) {
            value = 0;
        }
        _torpedosAmount = value;
    }

    public boolean hasTorpedos() {
        return _torpedosAmount > 0;
    }
}
