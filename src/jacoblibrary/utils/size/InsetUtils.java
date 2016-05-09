package jacoblibrary.utils.size;

import jacoblibrary.gui.Menu;

import java.awt.*;

public class InsetUtils {

    public static Insets getInsets(Menu menu, double[] insets) {

        return new Insets((int)(menu.getSize().height*insets[0]),
                (int)(menu.getSize().width*insets[1]),
                (int)(menu.getSize().height*insets[2]),
                (int)(menu.getSize().width*insets[3]));

    }

}
