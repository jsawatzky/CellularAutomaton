package jacoblibrary.gui.components;

import jacoblibrary.gui.Menu;
import jacoblibrary.utils.font.FontUtils;
import jacoblibrary.utils.size.DimensionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Button extends Component {

    private String label = "";
    private Font font = new Font("Times New Roman", Font.PLAIN, 1);

    public Button(String label, Font font, final Object obj, final Method action, int x, int y, int xSpan, int ySpan, double xSize, double ySize, double[] insets) {

        super(new JButton(label), x, y, xSpan, ySpan, xSize, ySize, insets);

        ((JButton)component).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    action.invoke(obj, null);
                } catch (IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.label = label;
        this.font = font;

    }

    @Override
    public void update(Menu menu) {

        super.update(menu);

        this.component.setFont(FontUtils.scaleFontToBounds(font, label, DimensionUtils.scale(component.getPreferredSize(), 0.30, 0.90), menu, false));

    }
}
