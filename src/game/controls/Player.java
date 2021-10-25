package game.controls;

public class Player implements Controllable{
    private String _name;
    private int _torpedosAmount;
    public static final int TORPEDOS_START_AMOUNT = 1;

    public Player(){
        _name = "Real player";
        _torpedosAmount = TORPEDOS_START_AMOUNT;
    }
    public void setName(String value){
        _name = value;
    }
    public String getName(){
        return _name;
    }
    public int getTorpedosCount(){
        return _torpedosAmount;
    }
    public boolean hasTorpedos(){
        return _torpedosAmount > 0;
    }
    public void setTorpedosCount(int value){
        if(value <0){
            value = 0;
        }
        _torpedosAmount = value;
    }
}
