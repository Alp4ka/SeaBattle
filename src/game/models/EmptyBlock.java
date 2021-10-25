package game.models;

public final class EmptyBlock implements GameObject {
    public static final char REPR = '·';
    public static final char INV = '?';

    public EmptyBlock() {
    }

    @Override
    public char represent() {
        return REPR;
    }
}
