package game.controls;

import game.utils.Pair;
import game.utils.Point;
import game.utils.Utils;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Computer player Controllable class, which can make a decision where to shoot in.
 *
 * @author Gorkovets Roman
 * @version 1.0
 * @see Controllable
 */
public class ArtificialPlayer implements Controllable {
    public static final int TORPEDOS_START_AMOUNT = 1;
    private String _name;
    private int _torpedoesAmount;
    private int _shotsMissedInRow = 0;
    private int _dangerLevel = 0;

    public ArtificialPlayer() {
        setName("Computer");
        _torpedoesAmount = TORPEDOS_START_AMOUNT;
    }


    /**
     * Return pair of Point + bool which describes the next shot, based on AI decision.
     * No ya slishkom ustal, poetomu sdelal random.
     *
     * @param ownField   - Own field. TODO: Uses whether the situation is critical to choose torpedo.
     * @param enemyField - Enemy field. TODO: Look, whether the ship is hit and try to predict where we have to shoot at.
     * @return pair of Point + bool which describes the next shot.
     * @author Gorkovets Roman
     * @version 1.1
     */
    public Pair<Point, Boolean> makeDecision(GameField ownField, GameField enemyField) {
        boolean torpedoUsage;
        var shots = enemyField.getShots().stream().collect(Collectors.toList());
        if (shots.size() == 0) {
            return new Pair<>(Point.getRandom(enemyField.getWidth(), enemyField.getHeight()),
                    (int) (Math.random() * 2) == 1);
        }
        var legalShots = Utils.fieldToPoints(enemyField).
                stream().
                filter((p) -> !shots.contains(p)).
                collect(Collectors.toList());
        if (!hasTorpedos()) {
            torpedoUsage = false;
        } else {
            torpedoUsage = (int) (Math.random() * 4) == 1;
        }

        if (legalShots.size() == 0) {
            Collections.shuffle(shots);
            for (var shot : shots) {
                if (enemyField.isAttackable(shot)) {
                    return new Pair<>(shot, torpedoUsage);
                }
            }
            legalShots = shots;
        }
        Point tempPoint;

        tempPoint = legalShots.get((int) (Math.random() * legalShots.size()));
        return new Pair<>(tempPoint, torpedoUsage);
    }

    public String getName() {
        return _name;
    }

    public void setName(String value) {
        _name = value;
    }

    public int getTorpedosCount() {
        return _torpedoesAmount;
    }

    public void setTorpedosCount(int value) {
        if (value < 0) {
            value = 0;
        }
        _torpedoesAmount = value;
    }

    public boolean hasTorpedos() {
        return _torpedoesAmount > 0;
    }
}
