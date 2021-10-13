package game.models;

import game.utils.Point;
import java.util.ArrayList;
import java.util.List;

public class Baseship implements IBattleship{
    private Point _head;
    private int _shipLength;
    private boolean _isHorizontal;
    private static final char _drawSymbol = '*';
    private List<ShipBlock> _shipArray;
    private void initializeShipArray(){
        _shipArray = new ArrayList<ShipBlock>(_shipLength);
        for(int i = 0; i < _shipLength; ++i){
            _shipArray.add(new ShipBlock(this));
        }
    }


    public Baseship(Point head, int length, boolean isHorizontal){
        _head = new Point(head);
        _shipLength = length;
        _isHorizontal = isHorizontal;
        initializeShipArray();
    }

    public boolean isAlive(){
        int sum = 0;
        for(int i =0; i < _shipArray.size(); ++i){
            sum += _shipArray.get(i).getHP();
        }
        return sum > 0;
    }

    public boolean getDamageAt(int index, int damage){
        if(_shipArray.get(index).getHP() == 0){
            return false;
        }
        else{
            _shipArray.get(index).setHP(_shipArray.get(index).getHP() - damage);
            if(_shipArray.get(index).getHP() < 0){
                _shipArray.get(index).setHP(0);
            }
            return true;
        }
    }

    public int getHealthAt(int index){
        return _shipArray.get(index).getHP();
    }

    public Point getHead() {
        return _head;
    }

    public void setHead(Point _head) {
        this._head = _head;
    }

    public int getShipLength() {
        return _shipLength;
    }

    public String toString(){
        String result = "";
        for(int i =0; i < _shipArray.size(); ++i){
            result += _shipArray.get(i).getHP() > 0 ? _drawSymbol : 'X';
        }
        return result;
    }
    public String represent(){
        return "Status: " + (isAlive() ? "Alive" : "Sinked")
                + "; View: " + toString() +
                "; Position: " + _head + " " + (_isHorizontal? "Horizontal" : "Vertical");
    }
}
