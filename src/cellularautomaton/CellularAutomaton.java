package cellularautomaton;

import cellularautomaton.menus.MainScreen;
import jacoblibrary.gui.Application;

public class CellularAutomaton extends Application {

    public CellularAutomaton() {

        super("Cellular Automaton");

    }

    public static void main(String[] args) {

        CellularAutomaton app = new CellularAutomaton();

        MainScreen menu = new MainScreen(app);

        app.setContent(menu);

    }

}
