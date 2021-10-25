package game.controls;

import game.utils.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class GameInstance {
    private static final String HELP_MSG =  "Submarine:  SIZE = 1, CODE = 0\n" +
                                            "Destroyer:  SIZE = 2, CODE = 1\n" +
                                            "Cruiser:    SIZE = 3, CODE = 2\n" +
                                            "Battleship: SIZE = 4, CODE = 3\n" +
                                            "Carrier:    SIZE = 5, CODE = 4\n";
    private HashMap<Controllable, GameField> _playerFieldMapping;
    private Controllable _activePlayer;
    private Controllable _winner;
    private boolean _gameOver = false;

    private boolean isRealPlayer(Controllable player){
        return player instanceof Player;
    }

    public GameInstance(GameConfiguration config){
        Player player = new Player();
        ArtificialPlayer computer = new ArtificialPlayer();
        _activePlayer = player;
        _winner = null;
        _playerFieldMapping = new HashMap<Controllable, GameField>();
        _playerFieldMapping.put(player, (new GameFieldGenerator(config).build()));
        _playerFieldMapping.put(computer, (new GameFieldGenerator(config).build()));

    }


    public void run(){
        Scanner keyboard = new Scanner(System.in);
        Pair<Point, Boolean> target;
        while(!_gameOver){
            boolean switchFlag = true;
            try {
                GameField activeField = _playerFieldMapping.get(_activePlayer);
                GameField enemyField = _playerFieldMapping.get(getSecondPlayer(_activePlayer));
                if (enemyField.isAlive()) {
                    System.out.println("Now it's turn of " + _activePlayer.getName() + "!");
                    if (isRealPlayer(_activePlayer)) {
                        System.out.println("You have: " + _activePlayer.getTorpedosCount() + " torpedos!");
                        System.out.println("Your field: ");
                        activeField.draw();
                        System.out.println("Enemy field: ");
                        enemyField.drawHidden();
                        target = getTargetManually(_activePlayer, System.in);
                    } else {
                        enemyField.drawHidden();
                        ArtificialPlayer activePlayer = (ArtificialPlayer) _activePlayer;
                        target = activePlayer.makeDecision(activeField, enemyField);
                    }
                    // Handling target.
                    if (!target.getSecondValue()) {
                        switchFlag = !enemyField.attackAt(target.getFirstValue());
                    } else {
                        switchFlag = !enemyField.attackTorpedoAt(target.getFirstValue());
                        _activePlayer.setTorpedosCount(_activePlayer.getTorpedosCount() - 1);
                    }
                    enemyField.drawHidden();
                    System.out.println("<Press Enter>");
                    try { System.in.read(); }
                    catch (IOException ioe) { }
                } else {
                    OnFinish(_activePlayer);
                    return;
                }
                // If hit, don't switch.
                if(switchFlag){
                    switchActivePlayer();
                }

            }
            catch(Exception ex){
                System.out.println("Wrong input! Try again.");
            }
        }
    }


    public Pair<Point, Boolean> getTargetManually(Controllable player, InputStream stream){
        boolean isCorrect = false;
        char letter = 'a';
        int number = 0;
        boolean torpedo = false;
        while(!isCorrect) {
            Scanner inputScanner = new Scanner(stream);
            System.out.println("Enter your target(LETTER NUMBER BOOL_INT_TORPEDO): ");
            letter = inputScanner.next().charAt(0);
            number = inputScanner.nextInt();
            torpedo = inputScanner.nextInt() == 1;
            Point target = new Point(letter, number);
            if(_playerFieldMapping.get(player).isInBounds(target)){
                if(torpedo){
                    if(!player.hasTorpedos()){
                        System.out.println("Wrong torpedo input!");
                        isCorrect = false;
                        continue;
                    }
                }
                isCorrect = true;
            }
            else{
                System.out.println("Wrong target input!");
            }
        }
        return new Pair<Point, Boolean>(Utils.coordToPoint(letter, number), torpedo);
    }

    public static GameConfiguration getSetupManually(InputStream stream){
        Scanner inputScanner = new Scanner(stream);
        int width, height, shipsAmount;
        ArrayList<GameFieldGenerator.ShipTypes> shipTypes = new ArrayList<>();
        System.out.println("Enter required width in [1 : " + GameField.MAX_FIELD_SIZE + "] range: ");
        try {
            width = inputScanner.nextInt();
            if(width < 1 || width > GameField.MAX_FIELD_SIZE){
                throw new InputMismatchException();
            }
        }
        catch(InputMismatchException ime){
            return null;
        }
        System.out.println("Enter required height in [1 : " + GameField.MAX_FIELD_SIZE + "] range: ");
        try {
            height = inputScanner.nextInt();
            if(height < 1 || height > GameField.MAX_FIELD_SIZE){
                throw new InputMismatchException();
            }
        }
        catch(InputMismatchException ime){
            return null;
        }
        System.out.println("Enter number of ships (>0): ");
        try {
            shipsAmount = inputScanner.nextInt();
            if(shipsAmount <= 0){
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
                GameFieldGenerator.ShipTypes type = GameFieldGenerator.ShipTypes.values()[inputScanner.nextInt()];
                shipTypes.add(type);
                System.out.println(type+" added to config list!");
            }
        }
        catch(InputMismatchException ime){
            return null;
        }
        return new GameConfiguration(width, height, shipTypes);
    }

    public Controllable getActivePlayer() {
        return _activePlayer;
    }

    public Controllable getWinner(){
        return _winner;
    }

    public void OnFinish(Controllable winner){
        System.out.println(winner.getName() + " won this game!");
        _gameOver = true;
        _winner = winner;
    }
    private Controllable getSecondPlayer(Controllable player){
        var players = new ArrayList<Controllable>(_playerFieldMapping.keySet());
        if(player == players.get(0)){
            return players.get(1);
        }
        else{
            return players.get(0);
        }
    }
    public void switchActivePlayer(){
        var players = new ArrayList<Controllable>(_playerFieldMapping.keySet());
        if(_activePlayer == players.get(0)){
            _activePlayer = players.get(1);
        }
        else{
            _activePlayer = players.get(0);
        }
    }
}
