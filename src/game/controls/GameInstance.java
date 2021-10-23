package game.controls;

import game.models.Destroyer;
import game.utils.GameConfiguration;
import game.utils.GameFieldGenerator;
import game.utils.Point;
import game.utils.Utils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class GameInstance {
    private static final String HELP_MSG =  "Submarine:  SIZE = 1, CODE = 0\n" +
                                            "Destroyer:  SIZE = 2, CODE = 1\n" +
                                            "Cruiser:    SIZE = 3, CODE = 2\n" +
                                            "Battleship: SIZE = 4, CODE = 3\n" +
                                            "Carrier:    SIZE = 5, CODE = 4\n";
    private HashMap<Player, GameField> _playerFieldMapping;
    private Player _activePlayer;
    private Player _winner;
    private boolean _gameOver = false;

    public GameInstance(GameConfiguration config){
        Player player = new Player();
        ArtificialPlayer computer = new ArtificialPlayer();
        _activePlayer = player;
        _winner = null;
        _playerFieldMapping = new HashMap<Player, GameField>();
        _playerFieldMapping.put(player, (new GameFieldGenerator(config).build()));
        _playerFieldMapping.put(computer, (new GameFieldGenerator(config).build()));
    }


    public void run(){
        _playerFieldMapping.values().stream().collect(Collectors.toList()).get(0).draw();
        _playerFieldMapping.values().stream().collect(Collectors.toList()).get(1).draw();
        /*while(!_gameOver){
            if(_playerFieldMapping.get(_activePlayer).isAlive()){

            }
            else{
                OnFinish(getSecondPlayer(_activePlayer));
            }
            switchActivePlayer();
        }*/
    }

    public static GameConfiguration getSetupManually(InputStream stream){
        Scanner keyboard = new Scanner(stream);
        int width, height, shipsAmount;
        ArrayList<GameFieldGenerator.ShipTypes> shipTypes = new ArrayList<>();
        System.out.println("Enter required width in [1 : " + GameField.MAX_FIELD_SIZE + "] range: ");
        try {
            width = keyboard.nextInt();
            if(width < 1 || width > GameField.MAX_FIELD_SIZE){
                throw new InputMismatchException();
            }
        }
        catch(InputMismatchException ime){
            return null;
        }
        System.out.println("Enter required height in [1 : " + GameField.MAX_FIELD_SIZE + "] range: ");
        try {
            height = keyboard.nextInt();
            if(height < 1 || height > GameField.MAX_FIELD_SIZE){
                throw new InputMismatchException();
            }
        }
        catch(InputMismatchException ime){
            return null;
        }
        System.out.println("Enter number of ships (>1): ");
        try {
            shipsAmount = keyboard.nextInt();
            if(shipsAmount <= 1){
                throw new InputMismatchException();
            }
        }
        catch(InputMismatchException ime){
            return null;
        }
        System.out.println("Now write CODE of ship you need (1 code per message). "+shipsAmount+" commands.");
        System.out.println(HELP_MSG);
        try {
            for (int i = 0; i < shipsAmount; ++i) {
                GameFieldGenerator.ShipTypes type = GameFieldGenerator.ShipTypes.values()[keyboard.nextInt()];
                shipTypes.add(type);
                System.out.println(type+" added to config list!");
            }
        }
        catch(InputMismatchException ime){
            return null;
        }
        return new GameConfiguration(width, height, shipTypes);
    }

    public Player getActivePlayer() {
        return _activePlayer;
    }

    public Player getWinner(){
        return _winner;
    }

    public void OnFinish(Player winner){
        _gameOver = true;
        _winner = winner;
    }
    private Player getSecondPlayer(Player player){
        ArrayList<Player> players = new ArrayList<Player>(_playerFieldMapping.keySet());
        if(player == players.get(0)){
            return players.get(1);
        }
        else{
            return players.get(0);
        }
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
