package cellularautomaton.game.cell;

import cellularautomaton.game.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class CellUtils {
    
    public static int getNumLiveNeighbors(Cell cell, Game game) {

        int total = 0;
        
        Cell[][] cells = game.getCells();
        int x = cell.x;
        int y = cell.y;

        if (game.getShape() == CellShape.OCTAGON) {
            try { if (cells[x-1][y].state) { total++; } } catch (Exception e) {}
            try { if (cells[x+1][y].state) { total++; } } catch (Exception e) {}
            try { if (cells[x][y-1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x][y+1].state) { total++; } } catch (Exception e) {}
        }else if (game.getShape() == CellShape.HEXAGON) {
            try { if (cells[x][y-1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x][y+1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x-1][y].state) { total++; } } catch (Exception e) {}
            try { if (cells[x+1][y].state) { total++; } } catch (Exception e) {}
            if (x % 2 == 0) {
                try { if (cells[x+1][y-1].state) { total++; } } catch (Exception e) {}
                try { if (cells[x-1][y-1].state) { total++; } } catch (Exception e) {}
            } else {
                try { if (cells[x+1][y+1].state) { total++; } } catch (Exception e) {}
                try { if (cells[x-1][y+1].state) { total++; } } catch (Exception e) {}
            }
        } else {
            try { if (cells[x-1][y].state) { total++; } } catch (Exception e) {}
            try { if (cells[x+1][y].state) { total++; } } catch (Exception e) {}
            try { if (cells[x][y-1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x][y+1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x-1][y-1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x+1][y-1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x+1][y+1].state) { total++; } } catch (Exception e) {}
            try { if (cells[x-1][y+1].state) { total++; } } catch (Exception e) {}
        }

        return total;
        
    }
    
    public static int getAvgNeighborAge(Cell cell, Game game) {

        int sides = 0;
        int total = 0;
    
        Cell[][] cells = game.getCells();
        int x = cell.x;
        int y = cell.y;
    
        if (game.getShape() == CellShape.OCTAGON) {
            sides = 4;
            try { if (cells[x-1][y].state) { total += cells[x-1][y].age; } } catch (Exception e) {}
            try { if (cells[x+1][y].state) { total += cells[x+1][y].age; } } catch (Exception e) {}
            try { if (cells[x][y-1].state) { total += cells[x][y-1].age; } } catch (Exception e) {}
            try { if (cells[x][y+1].state) { total += cells[x][y+1].age; } } catch (Exception e) {}
        }else if (game.getShape() == CellShape.HEXAGON) {
            sides = 6;
            try { if (cells[x][y-1].state) { total += cells[x][y-1].age; } } catch (Exception e) {}
            try { if (cells[x][y+1].state) { total += cells[x][y+1].age; } } catch (Exception e) {}
            try { if (cells[x-1][y].state) { total += cells[x-1][y].age; } } catch (Exception e) {}
            try { if (cells[x+1][y].state) { total += cells[x+1][y].age; } } catch (Exception e) {}
            if (x % 2 == 0) {
                try { if (cells[x+1][y-1].state) { total += cells[x+1][y-1].age; } } catch (Exception e) {}
                try { if (cells[x-1][y-1].state) { total += cells[x-1][y-1].age; } } catch (Exception e) {}
            } else {
                try { if (cells[x+1][y+1].state) { total += cells[x+1][y+1].age; } } catch (Exception e) {}
                try { if (cells[x-1][y+1].state) { total += cells[x-1][y+1].age; } } catch (Exception e) {}
            }
        } else {
            sides = 8;
            try { if (cells[x-1][y].state) { total += cells[x-1][y].age; } } catch (Exception e) {}
            try { if (cells[x+1][y].state) { total += cells[x+1][y].age; } } catch (Exception e) {}
            try { if (cells[x][y-1].state) { total += cells[x][y-1].age; } } catch (Exception e) {}
            try { if (cells[x][y+1].state) { total += cells[x][y+1].age; } } catch (Exception e) {}
            try { if (cells[x-1][y-1].state) { total += cells[x-1][y-1].age; } } catch (Exception e) {}
            try { if (cells[x+1][y-1].state) { total += cells[x+1][y-1].age; } } catch (Exception e) {}
            try { if (cells[x+1][y+1].state) { total += cells[x+1][y+1].age; } } catch (Exception e) {}
            try { if (cells[x-1][y+1].state) { total += cells[x-1][y+1].age; } } catch (Exception e) {}
        }
        
        return total/sides;
        
    }

    public static int getMaxNeighborAge(Cell cell, Game game) {

        ArrayList<Integer> ages = new ArrayList<>();

        Cell[][] cells = game.getCells();
        int x = cell.x;
        int y = cell.y;

        if (game.getShape() == CellShape.OCTAGON) {
            try { ages.add(cells[x-1][y].age); } catch (Exception e) {}
            try { ages.add(cells[x+1][y].age); } catch (Exception e) {}
            try { ages.add(cells[x][y-1].age); } catch (Exception e) {}
            try { ages.add(cells[x][y+1].age); } catch (Exception e) {}
        }else if (game.getShape() == CellShape.HEXAGON) {
            try { ages.add(cells[x][y-1].age); } catch (Exception e) {}
            try { ages.add(cells[x][y+1].age); } catch (Exception e) {}
            try { ages.add(cells[x-1][y].age); } catch (Exception e) {}
            try { ages.add(cells[x+1][y].age); } catch (Exception e) {}
            if (x % 2 == 0) {
                try { ages.add(cells[x+1][y-1].age); } catch (Exception e) {}
                try { ages.add(cells[x-1][y-1].age); } catch (Exception e) {}
            } else {
                try { ages.add(cells[x+1][y+1].age); } catch (Exception e) {}
                try { ages.add(cells[x-1][y+1].age); } catch (Exception e) {}
            }
        } else {
            try { ages.add(cells[x-1][y].age); } catch (Exception e) {}
            try { ages.add(cells[x+1][y].age); } catch (Exception e) {}
            try { ages.add(cells[x][y-1].age); } catch (Exception e) {}
            try { ages.add(cells[x][y+1].age); } catch (Exception e) {}
            try { ages.add(cells[x-1][y-1].age); } catch (Exception e) {}
            try { ages.add(cells[x+1][y-1].age); } catch (Exception e) {}
            try { ages.add(cells[x+1][y+1].age); } catch (Exception e) {}
            try { ages.add(cells[x-1][y+1].age); } catch (Exception e) {}
        }

        return Collections.max(ages);

    }
    
    public static boolean hasDeadNeighbor(Cell cell, Game game) {
    
        Cell[][] cells = game.getCells();
        int x = cell.x;
        int y = cell.y;
    
        if (game.getShape() == CellShape.OCTAGON) {
            try { if (cells[x-1][y].state && cells[x-1][y].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x+1][y].state && cells[x+1][y].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x][y-1].state && cells[x][y-1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x][y+1].state && cells[x][y+1].age > 0) { return true; } } catch (Exception e) {}
        }else if (game.getShape() == CellShape.HEXAGON) {
            try { if (cells[x][y-1].state && cells[x][y-1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x][y+1].state && cells[x][y+1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x-1][y].state && cells[x-1][y].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x+1][y].state && cells[x+1][y].age > 0) { return true; } } catch (Exception e) {}
            if (x % 2 == 0) {
                try { if (cells[x+1][y-1].state && cells[x+1][y-1].age > 0) { return true; } } catch (Exception e) {}
                try { if (cells[x-1][y-1].state && cells[x-1][y-1].age > 0) { return true; } } catch (Exception e) {}
            } else {
                try { if (cells[x+1][y+1].state && cells[x+1][y+1].age > 0) { return true; } } catch (Exception e) {}
                try { if (cells[x-1][y+1].state && cells[x-1][y+1].age > 0) { return true; } } catch (Exception e) {}
            }
        } else {
            try { if (cells[x-1][y].state && cells[x-1][y].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x+1][y].state && cells[x+1][y].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x][y-1].state && cells[x][y-1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x][y+1].state && cells[x][y+1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x-1][y-1].state && cells[x-1][y-1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x+1][y-1].state && cells[x+1][y-1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x+1][y+1].state && cells[x+1][y+1].age > 0) { return true; } } catch (Exception e) {}
            try { if (cells[x-1][y+1].state && cells[x-1][y+1].age > 0) { return true; } } catch (Exception e) {}
        }
    
        return false;
        
    }

    public static int getSideLength(int maxWidth, int maxHeight, Game game) {

        switch (game.getShape()) {

            case SQUARE:

                return Math.min(maxWidth, maxHeight);

            case HEXAGON:

                maxHeight = (game.getHeight()-(maxHeight/2))/game.getGridSizeY();

                double sideLength1 = maxHeight / (2 * Math.sin(Math.PI / 3));
                double sideLength2 = maxWidth / 2.0;

                return (int) Math.min(sideLength1, sideLength2);

            case OCTAGON:

                return (int) Math.min(maxWidth/2.414, maxHeight/2.414);

            default:

                return 0;

        }

    }

    public static Dimension getCellSize(int sideLength, Game game) {

        switch (game.getShape()) {

            case SQUARE:

                return new Dimension(sideLength, sideLength);

            case HEXAGON:

                return new Dimension(sideLength*2, (int) (2*sideLength*Math.sin(Math.PI / 3)));

            case OCTAGON:

                return new Dimension((int) (sideLength*2.414), (int) (sideLength*2.414));

            default:

                return new Dimension(0, 0);

        }

    }
    
    public static Dimension getGridSize(Dimension cellSize, Game game) {

        switch (game.getShape()) {

            case SQUARE:

                return new Dimension(cellSize.width*game.getGridSizeX(), cellSize.height*game.getGridSizeY());

            case HEXAGON:

                int width = (cellSize.width*game.getGridSizeX())-((cellSize.width/4)*(game.getGridSizeX()-1));
                int height = (cellSize.height*game.getGridSizeY())+(cellSize.width/2);

                return new Dimension(width, height);

            case OCTAGON:

                return new Dimension(cellSize.width*game.getGridSizeX(), cellSize.height*game.getGridSizeY());

            default:
                return new Dimension(0, 0);
        }

    }

    public static Color getCellColor(Cell cell, Game game) {

        switch (game.getType()) {

            case CLASSIC:
                if (cell.state) {
                    return Color.GREEN;
                } else {
                    return game.getBackground();
                }
            case FOREST:
                if (cell.state) {
                    return getColor(Color.GREEN, new Color(0, 70, 0), 8, cell.age);
                } else if (cell.age > 0) {
                    return new Color(91, 61, 30);
                } else {
                    return game.getBackground();
                }

        }

        return game.getBackground();

    }

    private static Color getColor(Color startColor, Color endColor, int ageLimit, int age) {

        int startRed = startColor.getRed();
        int startGreen = startColor.getGreen();
        int startBlue = startColor.getBlue();
        int endRed = endColor.getRed();
        int endGreen = endColor.getGreen();
        int endBlue = endColor.getBlue();

        int red = (startRed * (ageLimit - age) + endRed * (age - 1)) / (ageLimit - 1);
        int green = (startGreen * (ageLimit - age) + endGreen * (age - 1)) / (ageLimit - 1);
        int blue = (startBlue * (ageLimit - age) + endBlue * (age - 1)) / (ageLimit - 1);

        Color color = new Color(red, green, blue);

        return color;

    }

}
