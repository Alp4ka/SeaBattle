package game.models;

public class EmptyBlock implements IGameObject{
    private static final char REPR = '.';
    public EmptyBlock(){}

    @Override
    public char represent(){
        return REPR;
    }
}
