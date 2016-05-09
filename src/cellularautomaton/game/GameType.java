package cellularautomaton.game;

public enum GameType {

    CLASSIC,
    FOREST;

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }

}
