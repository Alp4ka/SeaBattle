package game.controls;

import game.utils.Pair;
import game.utils.Point;
import game.utils.Utils;

import java.util.stream.Collectors;

public class ArtificialPlayer implements Controllable{
    private String _name;
    private int _torpedoesAmount;
    public static final int TORPEDOS_START_AMOUNT = 1;
    private int _shotsMissedInRow = 0;
    private int _dangerLevel = 0;
    //TODO: choose what type of bullet i have to use.
    //TODO: choose where to shoot at.
    // Ya ustal pisat. ne hochu AI pisat.
    // Map point-bool . point is target. bool torpedo
    public Pair<Point, Boolean> makeDecision(GameField ownField, GameField enemyField){
        var shots = enemyField.getShots().stream().collect(Collectors.toList());
        if(shots.size() == 0){
            return new Pair<>(getRandomPoint(enemyField.getWidth(), enemyField.getHeight()),
                                (int)(Math.random() * 2) == 1);
        }
        var legalShots = Utils.fieldToPoints(enemyField).
                                                        stream().
                                                        filter((p)->!shots.contains(p)).
                                                        collect(Collectors.toList());
        Point tempPoint = shots.get(0);
        boolean torpedoUsage;
        if(!hasTorpedos()){
            torpedoUsage = false;
        }
        else{
            torpedoUsage = (int)(Math.random() * 4) == 1;
        }
        tempPoint = legalShots.get((int)(Math.random() * legalShots.size()));
        return new Pair<>(tempPoint, torpedoUsage);
    }

    public Point getRandomPoint(int maxWidth, int maxHeight){
        int x = (int)(Math.random() * maxWidth);
        int y = (int)(Math.random() * maxHeight);
        return new Point(x, y);
    }

    public ArtificialPlayer(){
        setName("Computer");
        _torpedoesAmount = TORPEDOS_START_AMOUNT;
    }
    public void setName(String value){
        _name = value;
    }
    public String getName(){
        return _name;
    }
    public int getTorpedosCount(){
        return _torpedoesAmount;
    }
    public boolean hasTorpedos(){
        return _torpedoesAmount > 0;
    }
    public void setTorpedosCount(int value){
        if(value <0){
            value = 0;
        }
        _torpedoesAmount = value;
    }
}
