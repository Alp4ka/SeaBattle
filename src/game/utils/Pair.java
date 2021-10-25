package game.utils;

/**
 * Pair structure.
 *
 * @author Gorkovets Roman
 * @version 1.0
 */
public class Pair<F, S> {
    private F firstValue;
    private S secondValue;

    public Pair(F first, S second) {
        firstValue = first;
        secondValue = second;
    }

    public F getFirstValue() {
        return firstValue;
    }

    public S getSecondValue() {
        return secondValue;
    }
}
