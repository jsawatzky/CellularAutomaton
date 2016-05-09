package cellularautomaton.game.cell;

import cellularautomaton.game.Game;

import java.awt.*;
import java.util.Random;

public class Cell {
    
    private Game parent;

    public boolean state = false;
    public int age = 0;
    
    public int x, y;

    private Shape lastShape = null;
    
    private Random r = new Random();

    int reproduce = 3;
    int overpopulation = 4;
    int loneliness = 1;

    public Cell(int x, int y, Game parent) {

        this.x = x;
        this. y = y;
        this.parent = parent;

        if (parent.getShape() == CellShape.OCTAGON) {
            reproduce = 2;
            overpopulation = 3;
            loneliness = 0;
        }

    }

    public Cell getNextGen() {
        
        Cell newCell = new Cell(x, y, parent);
        newCell.state = state;
        newCell.age = age;
        
        switch (parent.getType()) {

            case CLASSIC:
                
                /*
                 * Any live cell with fewer than two live neighbours dies, as if caused by under-population.
                 * Any live cell with two or three live neighbours lives on to the next generation.
                 * Any live cell with more than three live neighbours dies, as if by over-population.
                 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                 */

                int neighbors = CellUtils.getNumLiveNeighbors(this, parent);

                if (state) {
                    newCell.state = neighbors > loneliness && neighbors < overpopulation;
                } else {
                    if (neighbors == reproduce) {
                        newCell.enable();
                    }
                }
                
                break;
            
            case FOREST:

                /*
                 * Any live cell which the average age of surrounding cells is larger by at least two, the cell will die from lack of sunlight
                 * Any live cell with an age of 8 or greater will die from age
                 * Any Live cell which has a neighbor dead cell that at some point contained a live cell will age twice in one generation due to the absorption of nutrients from the dead cell
                 * Any live cell has a 20% chance of ageing every generation
                 * Any live cell of age 8 or higher has a 5% chance of dying every generation
                 * Any dead cell with a neighbor with an age of 6 or greater has a 50% chance of becoming alive from a seed from the neighboring tree
                 * Any dead cell which contained a live cell at some point has a 5% chance of becoming alive due to a seed from the old cell
                 */

                int neighborAvgAge = CellUtils.getAvgNeighborAge(this, parent);
                int maxNeighborAge = CellUtils.getMaxNeighborAge(this, parent);
                boolean hasDeadNeighbor = CellUtils.hasDeadNeighbor(this, parent);

                if (state) {
                    if (neighborAvgAge >= age+6) {
                        newCell.state = false;
                    }
                    if (age >= 8) {
                        if (r.nextDouble() < 0.05) {
                            newCell.state = false;
                        }
                    } else if (r.nextDouble() < 0.2) {
                        newCell.age += hasDeadNeighbor ? 2 : 1;
                    }
                } else {
                    if (maxNeighborAge >= 6) {
                        if (r.nextDouble() < 0.5) {
                            newCell.state = true;
                            newCell.age = 1;
                        }
                    }
                    if (age > 0) {
                        if (r.nextDouble() < 0.05) {
                            newCell.state = true;
                            newCell.age = 1;
                        }
                    }
                }
                
                break;
        }

        return newCell;

    }
    
    public Shape getShape(int sideLength, int width, int height, int xPadding, int yPadding) {

        Shape shape = null;

        switch (parent.getShape()) {

            case SQUARE:

                shape =  new Rectangle((x*width)+xPadding, (y*height)+yPadding, sideLength, sideLength);

                break;

            case HEXAGON:

                int startX = (x * (sideLength+width/4)) + xPadding + width/4;
                int startY = (y * height + (x%2) * height/2)+yPadding;

                int[] xPoints = new int[]{startX,
                        startX + sideLength,
                        startX+sideLength+(width/4),
                        startX+sideLength,
                        startX,
                        startX-(width/4)};
                int[] yPoints = {startY,
                        startY,
                        startY+(height/2),
                        startY+2*(height/2),
                        startY+2*(height/2),
                        startY+(height/2)};

                shape =  new Polygon(xPoints, yPoints, 6);

                break;

            case OCTAGON:

                int startX2 = (x*width) + ((width-sideLength)/2) + xPadding;
                int startY2 = (y*height) + yPadding;

                int[] xPoints2 = {startX2,
                        startX2 + sideLength,
                        startX2 + sideLength + ((width - sideLength) / 2),
                        startX2 + sideLength + ((width - sideLength) / 2),
                        startX2 + sideLength,
                        startX2,
                        startX2 - ((width - sideLength) / 2),
                        startX2 - ((width - sideLength) / 2)};
                int[] yPoints2 = {startY2,
                        startY2,
                        startY2 + ((height - sideLength) / 2),
                        startY2 + ((height - sideLength) / 2) + sideLength,
                        startY2 + height,
                        startY2 + height,
                        startY2 + ((height - sideLength) / 2) + sideLength,
                        startY2 + ((height - sideLength) / 2)};

                shape =  new Polygon(xPoints2, yPoints2, 8);

                break;

        }

        lastShape = shape;
        return shape;
        
    }

    public void enable() {
        state = true;
        age = 1;
    }

    public Shape getLastShape() {
        return lastShape;
    }

}
