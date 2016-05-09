package jacoblibrary.gui;

import jacoblibrary.handlers.KeyboardHandler;
import jacoblibrary.handlers.MouseHandler;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import jacoblibrary.utils.size.DimensionUtils;

public class Application extends JFrame {

    public Dimension SCREEN_SIZE;

    private JPanel content;

    private Dimension oldSize;
    private Point oldLocation;
    
    public KeyboardHandler keys;
    public MouseHandler mouse;
    
    public Application(String title) { this(title, false); }
    
    public Application(String title, boolean fullScreen) {
    
        super(title);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                SCREEN_SIZE = getContentPane().getSize();
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
                if (content != null && content instanceof Pauseable) {
                    ((Pauseable) content).pause();
                }
            }
        });
        
        if (fullScreen) {
            setSize(Toolkit.getDefaultToolkit().getScreenSize());
            setUndecorated(true);
        } else {
            setSize(DimensionUtils.scale(Toolkit.getDefaultToolkit().getScreenSize(), 0.8, 0.8));
        }
        
        keys = new KeyboardHandler();
        getContentPane().addKeyListener(keys);
        mouse = new MouseHandler();
        getContentPane().addMouseListener(mouse);
        getContentPane().addMouseMotionListener(mouse);
        getContentPane().addMouseWheelListener(mouse);
        
        try {
            keys.addListener(KeyEvent.VK_F11, this, Application.class.getMethod("toggleFullScreen", null));
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
    
    }
    
    public boolean isFullScreen() {
        return isUndecorated();
    }
    
    public void toggleFullScreen() {

        dispose();

        if (!isFullScreen()) {
            oldSize = getSize();
            oldLocation = getLocation();
            setSize(Toolkit.getDefaultToolkit().getScreenSize());
            setUndecorated(true);
            setLocationRelativeTo(null);
        } else {
            try {
                setSize(oldSize);
                setLocation(oldLocation);
            } catch (NullPointerException e) {
                setSize(DimensionUtils.scale(Toolkit.getDefaultToolkit().getScreenSize(), 0.8, 0.8));
                setLocationRelativeTo(null);
            }
            setUndecorated(false);

        }

        setVisible(true);

    }

    public void setContent(JPanel content) {

        this.content = content;
        try {
            getContentPane().remove(0);
        } catch (Exception e) {}
        getContentPane().add(content, 0);
        content.setSize(getSize());
        validate();
        update();

    }

    public void update() {

        if (content instanceof Menu) {
            ((Menu) content).update();
        }

    }
    
}
