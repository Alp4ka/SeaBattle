package game.models;

import game.utils.Point;

/**
 * Submarine class
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Baseship
 */
public final class Submarine extends Baseship {
    private static final int SIZE = 1;
    private static final String NAME = "DESTROYER";

    public Submarine(Point head, boolean isHorizontal) {
        super(head, SIZE, isHorizontal);
    }

    public String represent() {
        return NAME + " --- " + super.represent();
    }
}
