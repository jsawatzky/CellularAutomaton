package cellularautomaton.game;

import cellularautomaton.game.cell.*;
import jacoblibrary.gui.Application;
import jacoblibrary.gui.Graphics;
import jacoblibrary.utils.font.FontUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Game extends Graphics {

    private int gridSizeX = 25;
    private int gridSizeY = 25;

    private CellShape shape = CellShape.SQUARE;
    private GameType type = GameType.CLASSIC;

    private Cell[][] cells = new Cell[gridSizeX][gridSizeY];

    private boolean drawGridLines = false;

    private boolean stopped = true;

    private int gen = 0;
    private boolean empty = true;

    private JLabel title = null;
    private JSlider FPS = null;

    public Game(Application parent) {

        super(parent);

        setBackground(Color.BLACK);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, this);
            }
        }

        try {
            parent.mouse.addListener(parent.mouse.CLICK_EVENT, MouseEvent.BUTTON1, this, Game.class.getMethod("onLeftClick", int.class, int.class));
            parent.mouse.addListener(parent.mouse.CLICK_EVENT, MouseEvent.BUTTON3, this, Game.class.getMethod("onRightClick", int.class, int.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {

        if (!stopped) {

            Cell[][] nextCells = new Cell[gridSizeX][gridSizeY];
            int total = 0;

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j] == null) {
                        break;
                    }
                    nextCells[i][j] = cells[i][j].getNextGen();
                    if (nextCells[i][j].state) {
                        total += 1;
                    }
                }
            }

            cells = nextCells;
            gen += 1;

            if (total == 0) {
                stopped = true;
                empty = true;
            }

        }
        title.setText(type.toString() + " Game of Life - Generation " + gen);

    }

    @Override
    public void render(Graphics2D g) {

        int maxCellWidth = this.getWidth() / gridSizeX;
        int maxCellHeight = this.getHeight() / gridSizeY;

        int sideLength = CellUtils.getSideLength(maxCellWidth, maxCellHeight, this);
        Dimension cellSize = CellUtils.getCellSize(sideLength, this);

        Dimension gridSize = CellUtils.getGridSize(cellSize, this);

        if (gridSize.width == 0 || gridSize.height == 0) {
            g.setColor(Color.WHITE);
            g.setFont(FontUtils.scaleFontToBounds(new Font("Times New Roman", Font.PLAIN, 1), "Grid too large to display! Please shrink the grid size to veiw the animation.", this.getPreferredSize(), this, false));
            g.drawString("Grid too large to display! Please shrink the grid size to veiw the animation.", (getWidth()/2)-(g.getFontMetrics(g.getFont()).stringWidth("Grid too large to display! Please shrink the grid size to veiw the animation.")/2), getHeight()/2);
        } else {

            int xPadding = (getWidth() - gridSize.width) / 2;
            int yPadding = (getHeight() - gridSize.height) / 2;

            for (Cell[] cellColumn : cells) {
                for (Cell cell : cellColumn) {
                    if (cell == null) {
                        break;
                    }

                    Color color = CellUtils.getCellColor(cell, this);

                    g.setColor(color);

                    Shape cellShape = cell.getShape(sideLength, cellSize.width, cellSize.height, xPadding, yPadding);

                    g.fill(cellShape);
                    if (drawGridLines) {
                        g.setColor(Color.BLUE);
                        g.draw(cellShape);
                    }

                }
            }

            g.setColor(Color.GRAY);
            g.drawRect(xPadding, yPadding, gridSize.width, gridSize.height);

        }

    }

    public void step() {

        Cell[][] nextCells = new Cell[gridSizeX][gridSizeY];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                nextCells[i][j] = cells[i][j].getNextGen();
            }
        }

        cells = nextCells;

    }

    public void reset() {

        pause();

        cells = new Cell[gridSizeX][gridSizeY];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, this);
            }
        }

        gen = 0;
        empty = true;

    }

    public void fill(CellLayout layout) {

        reset();

        empty = false;

        switch (layout) {

            case CHECKERBOARD:

                for (int i = 0; i < cells.length; i++) {
                    for (int j = 0; j < cells[i].length; j++) {
                        if ((i+j)%2 == 0) {
                            cells[i][j].enable();
                        }
                    }
                }

                break;

            case RANDOM:

                Random r = new Random();
                for (int i = 0; i < cells.length; i++) {
                    for (int j = 0; j < cells[i].length; j++) {
                        if (r.nextInt(2) == 0) {
                            cells[i][j].enable();
                        }
                    }
                }

                break;

            default:

                Scanner in = null;
                try {
                    in = new Scanner(new File("res/" + layout.toString() + ".txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                int xSize = in.nextInt();
                int ySize = in.nextInt();
                boolean tileable = in.nextBoolean();
                String loc = in.next();
                in.nextLine();

                char[][] init = new char[xSize][ySize];
                for (int j = 0; j < ySize; j++) {
                    char[] values = in.nextLine().toCharArray();
                    for (int i = 0; i < xSize; i++) {
                        init[i][j] = values[i];
                    }
                }

                if (xSize > cells.length || ySize > cells[0].length) {
                    return;
                }

                if (tileable) {

                    int xGap = (cells.length%xSize)/2;
                    int yGap = (cells[0].length%ySize)/2;

                    for (int i = xGap; i < cells.length-xGap; i += xSize) {
                        if (i > cells.length-xSize) { break; }
                        for (int j = yGap; j < cells[i].length-yGap; j += ySize) {
                             if (j > cells[0].length-xSize) { break;}
                            for (int celli = 0; celli < init.length; celli++) {
                                for (int cellj = 0; cellj < init[celli].length; cellj++) {
                                    if (init[celli][cellj] == '1') {
                                        cells[i+celli][j+cellj].enable();
                                    }
                                }
                            }
                        }
                    }

                } else {

                    if (loc.equalsIgnoreCase("center")) {

                        int xStart = (cells.length-xSize)/2;
                        int yStart = (cells[0].length-ySize)/2;

                        for (int celli = 0; celli < init.length; celli++) {
                            for (int cellj = 0; cellj < init[celli].length; cellj++) {
                                if (init[celli][cellj] == '1') {
                                    cells[xStart+celli][yStart+cellj].enable();
                                }
                            }
                        }

                    } else if (loc.equalsIgnoreCase("top-left")) {

                        for (int celli = 0; celli < init.length; celli++) {
                            for (int cellj = 0; cellj < init[celli].length; cellj++) {
                                if (init[celli][cellj] == '1') {
                                    cells[celli][cellj].enable();
                                }
                            }
                        }

                    }

                }

                break;

        }

    }

    public void onLeftClick(int x, int y) {

        if (stopped) {
            x -= getX();
            y -= getY();
            for (Cell[] cell : cells) {
                for (Cell aCell : cell) {
                    if (aCell.getLastShape().contains(x, y)) {
                        if (empty) {
                            gen = 0;
                            empty = false;
                        }
                        if (type == GameType.FOREST) {
                            if (aCell.state && aCell.age < 8) {
                                aCell.age += 1;
                            } else if (!aCell.state && aCell.age > 0) {
                                aCell.age -= 1;
                            } else if (!aCell.state) {
                                aCell.state = true;
                                aCell.age = 1;
                            }
                        } else {
                            aCell.state = true;
                            aCell.age = 1;
                        }
                        return;
                    }
                }
            }
        }

    }

    public void onRightClick(int x, int y) {

        if (stopped) {
            x -= getX();
            y -= getY();
            for (Cell[] cell : cells) {
                for (Cell aCell : cell) {
                    if (aCell.getLastShape().contains(x, y)) {
                        if (type == GameType.FOREST) {
                            if (empty) {
                                gen = 0;
                                empty = false;
                            }
                            if (aCell.state && aCell.age > 1) {
                                aCell.age -= 1;
                            } else if (aCell.age == 1) {
                                aCell.state = false;
                                aCell.age = 0;
                            } else {
                                if (aCell.age < 8) {
                                    aCell.age += 1;
                                }
                            }
                        } else {
                            aCell.state = false;
                            aCell.age = 0;
                        }
                        return;
                    }
                }
            }
        }

    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public void setGridSizeX(int gridSizeX) {
        stopped = true;
        this.gridSizeX = gridSizeX;
        reset();
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setGridSizeY(int gridSizeY) {
        stopped = true;
        this.gridSizeY = gridSizeY;
        reset();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public CellShape getShape() {
        return shape;
    }

    public void setShape(CellShape shape) {
        this.shape = shape;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public void setDrawGridLines(boolean drawGridLines) {
        this.drawGridLines = drawGridLines;
    }

    @Override
    public void pause() {
        stopped = true;
        this.setFPS(100);
    }

    @Override
    public void resume() {
        stopped = false;
        this.setFPS(FPS.getValue());
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public void setFPSSlider(JSlider fps) {
        FPS = fps;
    }
}
