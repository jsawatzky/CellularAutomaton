package jacoblibrary.gui.components;

import jacoblibrary.gui.Menu;
import jacoblibrary.utils.size.DimensionUtils;
import jacoblibrary.utils.size.InsetUtils;

import javax.swing.*;
import java.awt.*;

public class Component {

    protected JComponent component;

    private int x, y, xSpan, ySpan;
    private double xSize, ySize;
    private double[] insets;

    public Component(JComponent component, int x, int y, int xSpan, int ySpan, double xSize, double ySize, double[] insets) {
        this.component = component;
        this.x = x;
        this.y = y;
        this.xSpan = xSpan;
        this.ySpan = ySpan;
        this.xSize = xSize;
        this.ySize = ySize;
        this.insets = insets;

        component.setFocusable(false);

    }

    public GridBagConstraints getConstraints(Menu menu) {

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = x;
        c.gridy = y;
        c.gridwidth = xSpan;
        c.gridheight = ySpan;
        c.insets = InsetUtils.getInsets(menu, insets);

        return c;

    }

    public void update(Menu menu) {

        component.setPreferredSize(DimensionUtils.scale(menu.getSize(), xSize, ySize));
        if (component instanceof Menu) {
            ((Menu) component).update();
        }

    }

    public JComponent get() {
        return component;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxSpan() {
        return xSpan;
    }

    public void setxSpan(int xSpan) {
        this.xSpan = xSpan;
    }

    public int getySpan() {
        return ySpan;
    }

    public void setySpan(int ySpan) {
        this.ySpan = ySpan;
    }

    public double getxSize() {
        return xSize;
    }

    public void setxSize(double xSize) {
        this.xSize = xSize;
    }

    public double getySize() {
        return ySize;
    }

    public void setySize(double ySize) {
        this.ySize = ySize;
    }

    public double[] getInsets() {
        return insets;
    }

    public void setInsets(double[] insets) {
        this.insets = insets;
    }
}
