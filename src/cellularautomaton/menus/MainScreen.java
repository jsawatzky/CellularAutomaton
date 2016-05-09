package cellularautomaton.menus;

import cellularautomaton.CellularAutomaton;
import cellularautomaton.game.Game;
import jacoblibrary.gui.Application;
import jacoblibrary.gui.Menu;
import jacoblibrary.gui.components.Label;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends Menu {

    private Application parent;

    private Label title;
    private Game game;
    private ControlsMenu controlsMenu;
    private SettingsMenu settingsMenu;


    public MainScreen(CellularAutomaton parent) {

        super(parent);

        this.parent = parent;

        setBackground(Color.BLACK);

        title = new Label("Classic Game of Life - Generation 0", new Font("Times New Roman", Font.PLAIN, 1), SwingConstants.CENTER, Color.WHITE, 0, 0, 2, 1, 0.8, 0.05, new double[]{0.01, 0.1, 0.01, 0.1});
        game = new Game(parent);
        controlsMenu = new ControlsMenu(parent, game);
        settingsMenu = new SettingsMenu(parent, game);


        addComponent(title, "title");
        addComponent(game, "game", 0, 1, 1, 1, 0.85, 0.8, new double[]{0, 0.05, 0.03, 0});
        addComponent(controlsMenu, "controls", 1, 1, 1, 1, 0.1, 0.8, new double[]{0, 0, 0, 0});
        addComponent(settingsMenu, "settings", 0, 2, 2, 1, 1, 0.1, new double[]{0, 0, 0, 0});


        game.setTitle((JLabel) title.get());
        game.start();

    }

}
