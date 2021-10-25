package game.controls;

/**
 * Made for Player-like classes.
 *
 * @author Gorkovets Roman
 * @version 1.0
 */
public interface Controllable {
    String _name = null;
    int _torpedoesAmount = -1;

    String getName();

    void setName(String value);

    int getTorpedosCount();

    void setTorpedosCount(int value);

    boolean hasTorpedos();
}
