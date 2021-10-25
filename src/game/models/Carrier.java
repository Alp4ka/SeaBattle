package game.models;

import game.utils.Point;

/**
 * Carrier class
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Baseship
 */
public final class Carrier extends Baseship {
    private static final int SIZE = 5;
    private static final String NAME = "CARRIER";

    public Carrier(Point head, boolean isHorizontal) {
        super(head, SIZE, isHorizontal);
    }

    @Override
    public String represent() {
        return NAME + " --- " + super.represent();
    }
}
