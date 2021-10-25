package game.models;

import game.utils.Point;

/**
 * Cruiser class
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Baseship
 */
public final class Cruiser extends Baseship {
    private static final int SIZE = 3;
    private static final String NAME = "CRUISER";

    public Cruiser(Point head, boolean isHorizontal) {
        super(head, SIZE, isHorizontal);
    }

    @Override
    public String represent() {
        return NAME + " --- " + super.represent();
    }
}
