package game.controls;

import game.models.EmptyBlock;
import game.models.IGameObject;
import java.util.ArrayList;

public class GameField {
    private ArrayList<ArrayList<IGameObject>> _field;
    private int _fieldWidth;
    private int _fieldHeight;
    private void initializeField(){
        _field = new ArrayList<>(_fieldHeight);
        for(int i =0;i < _fieldHeight; ++i){
            _field.set(i, new ArrayList<>(_fieldWidth));
            for(int j = 0; j < _fieldWidth; ++j){
                _field.get(i).set(j, new EmptyBlock());
            }
        }
    }

    public void getAttackAt(){

    }
    public GameField(int width, int height) throws IllegalArgumentException{
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("Width and height of game_field should be more than 0.");
        }
        _fieldWidth = width;
        _fieldHeight = height;
        initializeField();
        // TODO: Geneate field
    }

    public int getWidth() {
        return _fieldWidth;
    }
    public int getHeight() {
        return _fieldHeight;
    }

    public void Draw(){

    }

    public void DrawHidden(){

    }
}
