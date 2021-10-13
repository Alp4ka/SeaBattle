package game.models;

import game.utils.Point;

public class Submarine extends Baseship{
    private static final int SIZE = 1;
    private static final String NAME = "DESTROYER";

    public Submarine(Point head, boolean isHorizontal){
        super(head, SIZE, isHorizontal);
    }

    public String represent(){
        return NAME + " --- " + super.represent();
    }
}
