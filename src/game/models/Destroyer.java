package game.models;

import game.utils.Point;

/**
 * Destroyer class
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Baseship
 */
public final class Destroyer extends Baseship {
    private static final int SIZE = 2;
    private static final String NAME = "DESTROYER";

    public Destroyer(Point head, boolean isHorizontal) {
        super(head, SIZE, isHorizontal);
    }

    @Override
    public String represent() {
        return NAME + " --- " + super.represent();
    }
}
