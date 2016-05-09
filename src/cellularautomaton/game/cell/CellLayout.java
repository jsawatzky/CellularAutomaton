package cellularautomaton.game.cell;

/**
 * Created by Jacob on 2015-10-14.
 */
public enum CellLayout {

    CHECKERBOARD,
    RANDOM,
    PULSAR,
    PENTADECATHLON,
    DIEHARD,
    ACORN,
    GOSPER_GLIDER_GUN;


    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase().replace("_", " ");
    }
}
