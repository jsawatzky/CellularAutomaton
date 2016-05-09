package jacoblibrary.utils.size;

import java.awt.Dimension;

public class DimensionUtils {
    
    public static Dimension scale(Dimension old, double x, double y) {
        
        return new Dimension((int) (old.width*x), (int) (old.height*y));
        
    }
    
}
