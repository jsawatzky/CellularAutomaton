package cellularautomaton.menus;

import cellularautomaton.game.Game;
import jacoblibrary.gui.Application;
import jacoblibrary.gui.Menu;
import jacoblibrary.gui.components.Button;

import java.awt.*;

public class ControlsMenu extends Menu {

    private Application parent;
    private Game game;

    private Button start, stop, step, reset;

    public ControlsMenu(Application parent, Game game) {

        super(parent);

        this.parent = parent;
        this.game = game;

        setBackground(Color.BLACK);

        try {
            start = new Button("Start", new Font("Times New Roman", Font.PLAIN, 1), this, ControlsMenu.class.getMethod("startGame", null), 0, 0, 1, 1, 0.9, 0.21, new double[] {0.05, 0.05, 0.02, 0.05});
            step = new Button("Step", new Font("Times New Roman", Font.PLAIN, 1), this, ControlsMenu.class.getMethod("stepGame", null), 0, 1, 1, 1, 0.9, 0.21, new double[] {0, 0.05, 0.02, 0.05});
            stop = new Button("Stop", new Font("Times New Roman", Font.PLAIN, 1), this, ControlsMenu.class.getMethod("stopGame", null), 0, 2, 1, 1, 0.9, 0.21, new double[] {0, 0.05, 0.02, 0.05});
            reset = new Button("Reset", new Font("Times New Roman", Font.PLAIN, 1), this, ControlsMenu.class.getMethod("resetGame", null), 0, 3, 1, 1, 0.9, 0.21, new double[] {0, 0.05, 0.05, 0.05});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        addComponent(start, "start");
        addComponent(step, "step");
        addComponent(stop, "stop");
        addComponent(reset, "reset");

    }

    public void startGame() {
        game.resume();
    }

    public void stepGame() {
        game.step();
    }

    public void stopGame() {
        game.pause();
    }

    public void resetGame() {
        game.pause();
        game.reset();
    }

}
