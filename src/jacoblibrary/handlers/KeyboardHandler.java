package jacoblibrary.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardHandler implements KeyListener {
    
    public HashMap<Integer, Boolean> keys = new HashMap<>();
    
    public HashMap<Integer, HashMap<Object, Method>> listeners = new HashMap<>();
    
    public boolean isKeyPressed(int keyCode) {
        
        if (!keys.containsKey(keyCode)) { keys.put(keyCode, false); }
		
	return keys.get(keyCode);
        
    }
    
    public void addListener(int keyCode, Object obj, Method method) {
        
        if (!listeners.containsKey(keyCode)) {
            listeners.put(keyCode, new HashMap<Object, Method>());
        }
        listeners.get(keyCode).put(obj, method);
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((keys.containsKey(e.getKeyCode()) && !keys.get(e.getKeyCode())) || !keys.containsKey(e.getKeyCode())) {
            keys.put(e.getKeyCode(), true);
            if (listeners.containsKey(e.getKeyCode())) {
                for (Object obj: listeners.get(e.getKeyCode()).keySet()) {
                    try {
                        listeners.get(e.getKeyCode()).get(obj).invoke(obj, null);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(KeyboardHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        keys.put(e.getKeyCode(), false);
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
}
