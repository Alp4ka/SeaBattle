package game.models;

public class ShipBlock implements IGameObject{
    private IBattleship _parent;
    private int _HP;


    public ShipBlock(IBattleship parent){
        _HP = 100;
        _parent = parent;
    }
    public IBattleship getParent(){
        return _parent;
    }
    @Override
    public String toString(){
        return "";
    }

    public int getHP() {
        return _HP;
    }

    public void setHP(int HP) {
        _HP = HP;
    }
}
