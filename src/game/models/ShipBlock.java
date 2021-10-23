package game.models;

public class ShipBlock implements IGameObject{
    private static final char STANDARD_REPR = '₪';
    private static final char DEAD_REPR = 'x';
    private static final char HITTED_REPR = '+';
    private IBattleship _parent;
    private int _HP;

    public ShipBlock(IBattleship parent, int hp){
        _HP = hp;
        _parent = parent;
    }

    public ShipBlock(IBattleship parent){
        this(parent, 100);
    }

    public IBattleship getParent(){
        return _parent;
    }

    public int getHP() {
        return _HP;
    }

    public void setHP(int hp) {
        _HP = Math.max(0, hp);
    }

    public boolean isAlive(){
        return _HP > 0;
    }

    public void getDamage(int damage){
        setHP(_HP-damage);
    }

    @Override
    public char represent(){
        if(_HP > 0){
            return STANDARD_REPR;
        }
        else if(_HP <= 0){
            return DEAD_REPR;
        }
        else{
            return HITTED_REPR;
        }
    }
}
