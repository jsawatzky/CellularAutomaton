package cellularautomaton.game.cell;

public enum CellShape {

    SQUARE,
    HEXAGON,
    OCTAGON;

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }

}
