package jacoblibrary.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    private int x = 0;
    private int y = 0;
    
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean middlePressed = false;
    
    private boolean inWindow = true;
    
    private HashMap<Integer, HashMap<Integer, HashMap<Object, Method>>> listeners = new HashMap<>();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isMiddlePressed() {
        return middlePressed;
    }

    public boolean isInWindow() {
        return inWindow;
    }
    
    public void addListener(int event, int button,  Object obj, Method method) {
        
        if (!listeners.containsKey(event)) {
            listeners.put(event, new HashMap<Integer, HashMap<Object, Method>>());
        }
        if (!listeners.get(event).containsKey(button)) {
            listeners.get(event).put(button, new HashMap<Object, Method>());
        }
        
        listeners.get(event).get(button).put(obj, method);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
        if (listeners.containsKey(CLICK_EVENT)) {
            if (listeners.get(CLICK_EVENT).containsKey(e.getButton())) {
                for (Object obj: listeners.get(CLICK_EVENT).get(e.getButton()).keySet()) {
                    try {
                        listeners.get(CLICK_EVENT).get(e.getButton()).get(obj).invoke(obj, e.getX(), e.getY());
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(KeyboardHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            rightPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            middlePressed = true;
        }
        
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            rightPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            middlePressed = false;
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
        inWindow = true;
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
        inWindow = false;
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    
        if (listeners.containsKey(DRAG_EVENT)) {
            if (listeners.containsKey(e.getButton())) {
                for (Object obj: listeners.get(DRAG_EVENT).get(e.getButton()).keySet()) {
                    try {
                        listeners.get(DRAG_EVENT).get(e.getButton()).get(obj).invoke(obj, (Object) null);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(KeyboardHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    
        x = e.getX();
        y = e.getY();
    
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
        if (listeners.containsKey(WHEEL_EVENT)) {
            if (listeners.containsKey(e.getButton())) {
                for (Object obj: listeners.get(WHEEL_EVENT).get(e.getButton()).keySet()) {
                    try {
                        listeners.get(WHEEL_EVENT).get(e.getButton()).get(obj).invoke(obj, e);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(KeyboardHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
    }
    
    public int CLICK_EVENT = 0;
    public int DRAG_EVENT = 1;
    public int WHEEL_EVENT = 2;
    
}
