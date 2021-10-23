package game.models;

public class ShipBlock implements IGameObject{
    public static final int MAX_HP = 100;
    public static final int MIN_HP = 0;
    private static final char STANDARD_REPR = '₪';
    private static final char DEAD_REPR = 'x';
    private static final char HITTED_REPR = '*';
    private IBattleship _parent;
    private int _HP;

    public ShipBlock(IBattleship parent, int hp){
        _HP = hp;
        _parent = parent;
    }

    public ShipBlock(IBattleship parent){
        this(parent, MAX_HP);
    }

    public IBattleship getParent(){
        return _parent;
    }

    public int getHP() {
        return _HP;
    }

    public void setHP(int hp) {
        _HP = Math.min(MAX_HP, Math.max(MIN_HP, hp));
    }

    public boolean isAlive(){
        return _HP > MIN_HP;
    }

    public void getDamage(int damage){
        setHP(_HP-damage);
    }

    @Override
    public char represent(){
        if(!_parent.isAlive()){
            return DEAD_REPR;
        }
        if(_HP > MIN_HP){
            return STANDARD_REPR;
        }
        else if(_HP <= MIN_HP){
            return HITTED_REPR;
        }
        return STANDARD_REPR;
    }
}
