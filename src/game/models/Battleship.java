package game.models;

import game.utils.Point;

/**
 * Battleship class
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Baseship
 */
public final class Battleship extends Baseship {
    private static final int SIZE = 4;
    private static final String NAME = "BATTLESHIP";

    public Battleship(Point head, boolean isHorizontal) {
        super(head, SIZE, isHorizontal);
    }

    @Override
    public String represent() {
        return NAME + " --- " + super.represent();
    }
}
