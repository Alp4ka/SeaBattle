package game.models;

import game.utils.Point;
import java.util.ArrayList;
import java.util.List;

public class Baseship implements IBattleship{
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

    public void setHead(Point _head) {
        this._head = _head;
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

    public String represent(){
        return "Status: " + (isAlive() ? "Alive" : "Sinked")
                + "; View: " + toString() +
                "; Position: " + _head + " " + (_isHorizontal? "Horizontal" : "Vertical");
    }
}
