package game.controls;

import game.models.EmptyBlock;
import game.models.Ship;
import game.models.GameObject;
import game.models.ShipBlock;
import game.utils.Point;
import java.util.ArrayList;
import java.util.HashSet;

public final class GameField {
    public static final int MAX_FIELD_SIZE = 15;
    public static final int STANDART_DAMAGE = ShipBlock.MAX_HP;

    private ArrayList<ArrayList<GameObject>> _field;
    private ArrayList<Ship> _ships;
    private HashSet<Point> _shotsList;
    private int _fieldWidth;
    private int _fieldHeight;
    private void initializeField(){
        _field = new ArrayList<>();
        for(int i =0;i < _fieldHeight; ++i){
            _field.add(new ArrayList<>());
            for(int j = 0; j < _fieldWidth; ++j){
                _field.get(i).add(new EmptyBlock());
            }
        }
    }

    public boolean isInBounds(Point point){
        return point.x >= 0 || point.y >= 0 || point.x < _fieldWidth || point.y < _fieldHeight;
    }

    public boolean isAlive(){
        return _ships.stream().filter(Ship::isAlive).count() > 0;
    }

    //Returns square area around point.
    public ArrayList<Point> getAmbientOfPoint(Point point){
        ArrayList<Point> result = new ArrayList<>();
        for(int i = -1; i <= 1; ++i){
            for(int j = -1; j <= 1; ++j){
                Point temp = new Point(point.x + i, point.y + j);
                if(temp.x >= 0 && temp.x < _fieldWidth && temp.y >= 0 && temp.y < _fieldHeight){
                    result.add(temp);
                }
            }
        }
        return result;
    }

    public boolean isAttackedAt(Point position){
        boolean result = false;
        for(Point shot: _shotsList){
            if(shot.equals(position)){
                result = true;
            }
        }
        return result;
    }


    public boolean attackAt(Point position) throws IllegalArgumentException{
        if(!isInBounds(position)){
            throw new IllegalArgumentException("You can't attack outside the gamefield!");
        }
        _shotsList.add(position);
        if(isAttackable(position)){
            _shotsList.add(position);
            ShipBlock target = ((ShipBlock)_field.get(position.y).get(position.x));
            target.getDamage(STANDART_DAMAGE);
            onHit(target);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean attackTorpedoAt(Point position) throws IllegalArgumentException{
        if(!isInBounds(position)){
            throw new IllegalArgumentException("You can't attack outside the gamefield!");
        }
        System.out.println("Torpedo!");
        _shotsList.add(position);
        if(isAttackable(position)){
            ShipBlock target = ((ShipBlock)_field.get(position.y).get(position.x));
            _shotsList.addAll(target.getParent().getShipPointVector());
            target.getParent().suicide();
            onHit(target);
            return true;
        }
        else{
            return false;
        }
    }

    // Bez proverki sdelano!
    public void addShip(Ship newShip){
        _ships.add(newShip);
        ArrayList<Point> vector = newShip.getShipPointVector();
        for(int i =0; i < vector.size(); ++i){
            GameObject o = newShip.getShipBlocksVector().get(i);
            _field.get(vector.get(i).y).set(vector.get(i).x, o);
        }
    }

    public GameObject getAt(Point position){
        return _field.get(position.y).get(position.x);
    }

    public boolean isAttackable(Point point){
        if(_field.get(point.y).get(point.x) instanceof ShipBlock){
            if(((ShipBlock) _field.get(point.y).get(point.x)).isAlive() ){
                return true;
            }
        }
        return false;
    }

    public GameField(int width, int height) throws IllegalArgumentException{
        if(width <= 0 || height <= 0 || width > MAX_FIELD_SIZE || height > MAX_FIELD_SIZE){
            throw new IllegalArgumentException("Width and height of game field should be in [1 : " +
                    MAX_FIELD_SIZE + "] bounds!");
        }
        _fieldWidth = width;
        _fieldHeight = height;
        _ships = new ArrayList<>();
        _shotsList = new HashSet<>();
        initializeField();
    }


    public int getWidth() {
        return _fieldWidth;
    }

    public int getHeight() {
        return _fieldHeight;
    }

    public ArrayList<Ship> getShips(){
        return _ships;
    }

    // call it in case u hit smth.
    public void onHit(ShipBlock shipBlock){
        Ship parent = shipBlock.getParent();
        System.out.println(parent.represent());
        if(parent.isAlive()){
            System.out.println("HITTED");
        }
        else{
            System.out.println("DEAD");
        }
    }

    public HashSet<Point> getShots(){
        return _shotsList;
    }

    public void draw(){
        for(int i = 0;i < _fieldWidth; ++i){
            System.out.print((char)('A'+i) + "\t");
        }
        System.out.println();

        for(int i =0;i < _fieldHeight; ++i){
            for(int j =0;j < _fieldWidth; ++j){
                System.out.print(_field.get(i).get(j).represent());
                System.out.print("\t");
            }
            System.out.print("\b  " + i);
            System.out.println();
        }
        System.out.println();
    }

    public void drawHidden(){
        for(int i = 0;i < _fieldWidth; ++i){
            System.out.print((char)('A'+i) + "\t");
        }
        System.out.println();

        for(int i =0;i < _fieldHeight; ++i){
            for(int j =0;j < _fieldWidth; ++j){
                Point current = new Point(j, i);
                if (_shotsList.contains(current)){
                    System.out.print(_field.get(i).get(j).represent() + "\t");
                }
                else{
                    System.out.print(EmptyBlock.INV + "\t");
                }
            }
            System.out.print("\b  " + i);
            System.out.println();
        }
        System.out.println();
    }
}
