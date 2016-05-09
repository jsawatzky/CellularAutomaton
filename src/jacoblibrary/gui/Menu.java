package jacoblibrary.gui;

import jacoblibrary.gui.components.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;

public class Menu extends JPanel {

    private Application parent;

    private HashMap<String, Component> components = new HashMap<>();

    public Menu(Application parent) {

        super(new GridBagLayout());

        this.parent = parent;

        setBackground(Color.WHITE);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                update();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

    }

    public void addComponent(JComponent component, String name, int x, int y, int xSpan, int ySpan, double xSize, double ySize, double[] insets) {

        this.addComponent(new Component(component, x, y, xSpan, ySpan, xSize, ySize, insets), name);

    }

    public void addComponent(Component component, String name) {

        components.put(name, component);

    }

    public void deleteComponent(String name) {
        if (components.containsKey(name)) {
            components.remove(name);
        }
    }

    public Component getComponent(String name) {
        if (components.containsKey(name)) {
            return components.get(name);
        } else {
            return null;
        }
    }

    public void update() {

        for (Component component: components.values()) {
            component.update(this);
            add(component.get(), component.getConstraints(this));
        }

    }

}
