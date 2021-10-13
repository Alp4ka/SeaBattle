package game.models;

import game.utils.Point;

import java.util.List;

public interface IBattleship {
    Point _head = null;
    int _shipLength = 0;
    boolean _isHorizontal = false;
    char _drawSymbol = 0;
    List<Integer> _shipArray = null;
    String represent();
}
