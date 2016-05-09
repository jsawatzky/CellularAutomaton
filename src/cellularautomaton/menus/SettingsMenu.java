package cellularautomaton.menus;

import cellularautomaton.game.Game;
import cellularautomaton.game.cell.CellLayout;
import cellularautomaton.game.cell.CellShape;
import cellularautomaton.game.GameType;
import jacoblibrary.gui.Application;
import jacoblibrary.gui.Menu;
import jacoblibrary.gui.components.Label;
import jacoblibrary.gui.components.Button;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SettingsMenu extends Menu implements ItemListener, ActionListener, ChangeListener {

    private Application parent;
    private Game game;

    private Label typeLabel, shapeLabel, layoutLabel, xSizeLabel, ySizeLabel, speedLabel;
    private Button fill, help;
    private JCheckBox showGrid;
    private JSpinner xSize, ySize;
    private JComboBox<GameType> type;
    private JComboBox<CellShape> shape;
    private JComboBox<CellLayout> layout;
    private JSlider speed;

    public SettingsMenu(Application parent, Game game) {

        super(parent);

        this.parent = parent;
        this.game = game;

        setBackground(Color.BLACK);

        try {
            fill = new Button("Fill", new Font("Times New Roman", Font.PLAIN, 1), this, SettingsMenu.class.getMethod("fillGame", null), 4, 0, 1, 1, 0.05, 0.35, new double[] {0.1, 0, 0.1, 0});
            help = new Button("Help", new Font("Times New Roman", Font.PLAIN, 1), this, SettingsMenu.class.getMethod("help", null), 7, 1, 2, 1, 0.21, 0.35, new double[] {0, 0.02, 0.1, 0.05});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        addComponent(fill, "fill");
        addComponent(help, "help");

        typeLabel = new Label("Game Type:", new Font("Times New Roman", Font.PLAIN, 1), SwingConstants.CENTER, Color.WHITE, 0, 0, 1, 1, 0.06, 0.35, new double[]{0.1, 0.05, 0.1, 0});
        addComponent(typeLabel, "typeLabel");
        type = new JComboBox<>(GameType.values());
        type.addActionListener(this);
        addComponent(type, "type", 1, 0, 1, 1, 0.15, 0.35, new double[]{0.1, 0, 0.1, 0});

        shapeLabel = new Label("Cell Shape:", new Font("Times New Roman", Font.PLAIN, 1), SwingConstants.CENTER, Color.WHITE, 0, 1, 1, 1, 0.06, 0.35, new double[]{0, 0.05, 0.1, 0});
        addComponent(shapeLabel, "shapeLabel");
        shape = new JComboBox<>(CellShape.values());
        shape.addActionListener(this);
        addComponent(shape, "shape", 1, 1, 1, 1, 0.15, 0.35, new double[]{0, 0, 0.1, 0});

        layoutLabel = new Label("First Gen:", new Font("Times New Roman", Font.PLAIN, 1), SwingConstants.CENTER, Color.WHITE, 2, 0, 1, 1, 0.06, 0.35, new double[]{0.1, 0.02, 0.1, 0});
        addComponent(layoutLabel, "layoutLabel");
        layout = new JComboBox<>(CellLayout.values());
        addComponent(layout, "layout", 3, 0, 1, 1, 0.1, 0.35, new double[]{0.1, 0, 0.1, 0});

        showGrid = new JCheckBox("Show Grid");
        showGrid.addItemListener(this);
        showGrid.setBackground(Color.BLACK);
        showGrid.setForeground(Color.WHITE);
        addComponent(showGrid, "showGrid", 2, 1, 3, 1, 0.21, 0.35, new double[]{0, 0.02, 0.1, 0});

        xSizeLabel = new Label("Grid Width:", new Font("Times New Roman", Font.PLAIN, 1), SwingConstants.CENTER, Color.WHITE, 5, 0, 1, 1, 0.06, 0.35, new double[]{0.1, 0.02, 0.1, 0});
        addComponent(xSizeLabel, "xSizeLabel");
        xSize = new JSpinner(new SpinnerNumberModel(25, 10, 500, 5));
        xSize.addChangeListener(this);
        addComponent(xSize, "xSize", 6, 0, 1, 1, 0.15, 0.35, new double[]{0.1, 0, 0.1, 0});

        ySizeLabel = new Label("Cell Shape:", new Font("Times New Roman", Font.PLAIN, 1), SwingConstants.CENTER, Color.WHITE, 5, 1, 1, 1, 0.06, 0.35, new double[]{0, 0.02, 0.1, 0});
        addComponent(ySizeLabel, "ySizeLabel");
        ySize = new JSpinner(new SpinnerNumberModel(25, 10, 500, 5));
        ySize.addChangeListener(this);
        addComponent(ySize, "ySize", 6, 1, 1, 1, 0.15, 0.35, new double[]{0, 0, 0.1, 0});

        speedLabel = new Label("Speed:", new Font("Times New Roman", Font.PLAIN, 1), SwingConstants.CENTER, Color.WHITE, 7, 0, 1, 1, 0.06, 0.35, new double[]{0.1, 0.02, 0.1, 0});
        addComponent(speedLabel, "speedLabel");
        speed = new JSlider(JSlider.HORIZONTAL, 1, 30, 5);
        speed.addChangeListener(this);
        speed.setBackground(Color.BLACK);
        speed.setForeground(Color.WHITE);
        addComponent(speed, "speed", 8, 0, 1, 1, 0.15, 0.35, new double[]{0.1, 0, 0.1, 0.05});
        game.setFPSSlider(speed);


    }

    public void fillGame() {
        game.fill((CellLayout) layout.getSelectedItem());
    }

    public void help() {

        JOptionPane.showMessageDialog(parent, "When the game is stopped:\n- Left click any cell to make it alive or increase it's age by 1.\n- Right click any cell to make it dead or decrease it's age by 1.", "Help", JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == showGrid) {
            game.setDrawGridLines(showGrid.isSelected());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == shape) {
            game.setShape((CellShape) shape.getSelectedItem());
        } else if (e.getSource() == type) {
            game.setType((GameType) type.getSelectedItem());
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == xSize) {
            game.setGridSizeX(((SpinnerNumberModel) xSize.getModel()).getNumber().intValue());
        } else if (e.getSource() == ySize) {
            game.setGridSizeY(((SpinnerNumberModel)ySize.getModel()).getNumber().intValue());
        }else if (e.getSource() == speed) {
            game.setFPS(speed.getValue());
        }
    }

}
