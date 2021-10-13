package game.models;

import game.utils.Point;


public class Carrier extends Baseship{
    private static final int SIZE = 5;
    private static final String NAME = "CARRIER";

    public Carrier(Point head, boolean isHorizontal){
        super(head, SIZE, isHorizontal);
    }

    @Override
    public String represent(){
        return NAME + " --- " + super.represent();
    }
}
