package game.controls;

import game.models.EmptyBlock;
import game.models.IBattleship;
import game.models.IGameObject;
import game.models.ShipBlock;
import game.utils.Point;
import java.util.ArrayList;
import java.util.HashSet;

public class GameField {
    public static final int MAX_FIELD_SIZE = 15;
    public static final int STANDART_DAMAGE = ShipBlock.MAX_HP;

    private ArrayList<ArrayList<IGameObject>> _field;
    private ArrayList<IBattleship> _ships;
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
        if(position.x < 0 || position.y < 0 || position.x >= _fieldWidth || position.y >= _fieldHeight){
            throw new IllegalArgumentException("You can't attack outside the gamefield!");
        }
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

    // Bez proverki sdelano!
    public void addShip(IBattleship newShip){
        _ships.add(newShip);
        ArrayList<Point> vector = newShip.getShipPointVector();
        for(int i =0; i < vector.size(); ++i){
            IGameObject o = newShip.getShipBlocksVector().get(i);
            _field.get(vector.get(i).y).set(vector.get(i).x, o);
        }
    }

    public IGameObject getAt(Point position){
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

    public ArrayList<IBattleship> getShips(){
        return _ships;
    }

    // call it in case u hit smth.
    public void onHit(ShipBlock shipBlock){
        IBattleship parent = shipBlock.getParent();
        System.out.println(parent.represent());
        if(parent.isAlive()){
            System.out.println("HITTED");
        }
        else{
            System.out.println("DEAD");
        }
    }


    public void Draw(){
        System.out.println("Normal field");
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

    //TODO
    public void DrawHidden(){
        System.out.println("Hidden field");
        for(int i =0;i < _fieldHeight; ++i){
            for(int j =0;j < _fieldWidth; ++j){
                Point current = new Point(j, i);
                //if()
                //if(System.out.print(_field.get(i).get(j))
                System.out.print(_field.get(i).get(j).represent());
                System.out.print(' ');
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }
}
