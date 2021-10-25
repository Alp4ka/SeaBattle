package game.models;

import game.utils.Point;
import java.util.ArrayList;
import java.util.List;

public class Baseship implements Ship {
    private Point _head;
    private int _shipLength;
    private boolean _isHorizontal;
    private List<ShipBlock> _shipArray;
    private void initializeShipArray(){
        _shipArray = new ArrayList<ShipBlock>(_shipLength);
        for(int i = 0; i < _shipLength; ++i){
            _shipArray.add(new ShipBlock(this));
        }
    }

    public List<ShipBlock> getShipBlocksVector(){
        return _shipArray;
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
        if(!_shipArray.get(index).isAlive()){
            return false;
        }
        else{
            _shipArray.get(index).getDamage(damage);
            return true;
        }
    }

    public int getHealthAt(int index){
        return _shipArray.get(index).getHP();
    }

    public Point getHead() {
        return _head;
    }

    public boolean getOrientation(){
        return _isHorizontal;
    }

    public void setOrientation(boolean value){
        _isHorizontal = value;
    }
    public void setHead(Point value){
        _head = value;
    }
    public int getShipLength() {
        return _shipLength;
    }

    public String toString(){
        String result = "";
        for(int i =0; i < _shipArray.size(); ++i){
            result += _shipArray.get(i).represent();
        }
        return result;
    }

    public ArrayList<Point> getShipPointVector(){
        ArrayList<Point> result = new ArrayList<>(_shipLength);
        Point current = new Point(_head);
        Point shift = new Point(0, 0);
        Point shiftValue = _isHorizontal? new Point(1, 0) : new Point(0, 1);
        for(int i=0; i < _shipLength; ++i){
            result.add(current.add(shift));
            shift = shift.add(shiftValue);
        }
        return result;
    }

    public void suicide(){
        for(ShipBlock block: _shipArray){
            block.suicide();
        }
    }

    public int getHP(){
        int sum =0;
        for(var block: _shipArray){
            sum+=block.getHP();
        }
        return sum;
    }
    public int maxHP(){
        return maxHP() * _shipArray.size();
    }

    public boolean isHitted(){
        return isAlive() && getHP() < maxHP();
    }

    public String represent(){
        return "Status: " + (isAlive() ? "Alive" : "Sink")
                + "; View: " + toString() +
                "; Position: " + _head + " " + (_isHorizontal? "Horizontal" : "Vertical");
    }
}
