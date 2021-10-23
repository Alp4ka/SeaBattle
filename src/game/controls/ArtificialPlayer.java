package game.controls;

import game.utils.Point;

public class ArtificialPlayer extends Player{
    private int _shotsMissedInRow = 0;
    private int _dangerLevel = 0;
    //TODO: choose what type of bullet i have to use.
    //TODO: choose where to shoot at.
    public Point makeDecision(GameField ownField, GameField enemyField){
        return new Point(0, 0);
    }
}
