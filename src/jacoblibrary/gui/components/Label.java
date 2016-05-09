package jacoblibrary.gui.components;

import jacoblibrary.gui.Menu;
import jacoblibrary.utils.font.FontUtils;

import javax.swing.*;
import java.awt.*;

public class Label extends Component {

    private String text;
    private Font font;

    public Label(String text, Font font, int alignment, Color color, int x, int y, int xSpan, int ySpan, double xSize, double ySize, double[] insets) {

        super(new JLabel(text), x, y, xSpan, ySpan, xSize, ySize, insets);

        ((JLabel) this.component).setHorizontalAlignment(alignment);
        ((JLabel) this.component).setVerticalAlignment(alignment);
        this.component.setForeground(color);

        this.text = text;
        this.font = font;

    }

    @Override
    public void update(Menu menu) {

        super.update(menu);

        this.component.setFont(FontUtils.scaleFontToBounds(font, text, component.getPreferredSize(), menu, false));

    }
}
