package game.models;

import game.utils.Point;

public final class Destroyer extends Baseship{
    private static final int SIZE = 2;
    private static final String NAME = "DESTROYER";

    public Destroyer(Point head, boolean isHorizontal){
        super(head, SIZE, isHorizontal);
    }

    @Override
    public String represent(){
        return NAME + " --- " + super.represent();
    }
}
