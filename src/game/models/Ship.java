package game.models;

import game.utils.Point;
import java.util.ArrayList;
import java.util.List;

public interface Ship {
    Point _head = null;
    int _shipLength = 0;
    boolean _isHorizontal = false;
    List<ShipBlock> getShipBlocksVector();
    char _drawSymbol = 0;
    List<Integer> _shipArray = null;
    String represent();
    ArrayList<Point> getShipPointVector();
    boolean isAlive();
    int getHealthAt(int index);
    Point getHead();
    void setHead(Point value);
    boolean getOrientation();
    void setOrientation(boolean value);
    int getShipLength();
    void suicide();
    boolean isHitted();
    int getHP();
    int maxHP();
}
