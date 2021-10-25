package game.models;

public final class ShipBlock implements GameObject {
    public static final int MAX_HP = 100;
    public static final int MIN_HP = 0;
    public static final char STANDARD_REPR = '₪';
    public static final char DEAD_REPR = 'T';
    public static final char HITTED_REPR = '*';
    private final Ship _parent;
    private int _HP;

    public ShipBlock(Ship parent, int hp) {
        _HP = hp;
        _parent = parent;
    }

    public ShipBlock(Ship parent) {
        this(parent, MAX_HP);
    }

    public Ship getParent() {
        return _parent;
    }

    public int getHP() {
        return _HP;
    }

    public void setHP(int hp) {
        _HP = Math.min(MAX_HP, Math.max(MIN_HP, hp));
    }

    public boolean isAlive() {
        return _HP > MIN_HP;
    }

    public void getDamage(int damage) {
        setHP(_HP - damage);
    }

    public void recover() {
        setHP(MAX_HP);
    }

    public void suicide() {
        setHP(MIN_HP);
    }

    @Override
    public char represent() {
        if (!_parent.isAlive()) {
            return DEAD_REPR;
        }
        if (_HP >= MAX_HP) {
            return STANDARD_REPR;
        } else if (_HP <= MIN_HP) {
            return HITTED_REPR;
        } else {
            return STANDARD_REPR;
        }
    }
}
