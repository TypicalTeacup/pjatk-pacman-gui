package pl.edu.pja.s33398.Model;

import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.GridGenerator.IGridGenerator;

import javax.swing.table.AbstractTableModel;

public class GameModel extends AbstractTableModel {
    private final int rows, columns;
    private GameGrid grid;
    private int score;
    private int lives;
    private int time;

    public GameModel(int rows, int columns, IGridGenerator gridGenerator) {
        super();
        this.rows = rows;
        this.columns = columns;

        this.score = 0;
        this.lives = 3;
        this.time = 0;

        this.grid = gridGenerator.generate(rows, columns);
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void subtractLife() {
        this.lives--;
    }

    public void tickTime() {
        this.time++;
    }

    public String getTime() {
        int minutes = this.time / 60;
        int seconds = this.time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return GameObject.class;
    }

    public GameGrid getGrid() {
        return this.grid;
    }

    public synchronized void setGrid(GameGrid grid) {
        this.grid = grid;
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return this.rows;
    }

    @Override
    public int getColumnCount() {
        return this.columns;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.grid.getTopObject(rowIndex, columnIndex);
    }

}
