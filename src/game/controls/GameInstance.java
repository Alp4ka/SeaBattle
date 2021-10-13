package game.controls;

import java.util.ArrayList;
import java.util.HashMap;

public class GameInstance {
    private HashMap<Player, GameField> _playerFieldMapping;
    private Player _activePlayer;
    private Player _winner;
    private boolean _gameOver = false;

    public GameInstance(int width, int height, Player firstPlayer, Player secondPlayer) throws IllegalArgumentException{
        _winner = null;
        if(firstPlayer == null || secondPlayer == null){
            throw new IllegalArgumentException("One or more players were null.");
        }
        _activePlayer = firstPlayer;

        _playerFieldMapping = new HashMap<Player, GameField>();
        _playerFieldMapping.put(firstPlayer, new GameField(width, height));
        _playerFieldMapping.put(secondPlayer, new GameField(width, height));
    }

    public Player getActivePlayer() {
        return _activePlayer;
    }

    public Player getWinner(){
        return _winner;
    }

    public void switchActivePlayer(){
        ArrayList<Player> players = new ArrayList<Player>(_playerFieldMapping.keySet());
        if(_activePlayer == players.get(0)){
            _activePlayer = players.get(1);
        }
        else{
            _activePlayer = players.get(0);
        }
    }
}
