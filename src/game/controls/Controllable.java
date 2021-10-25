package game.controls;

public interface Controllable {
    String _name = null;
    int _torpedosAmount = -1;
    void setName(String value);
    String getName();
    int getTorpedosCount();
    boolean hasTorpedos();
    void setTorpedosCount(int value);
}
