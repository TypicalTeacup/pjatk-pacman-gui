package pl.edu.pja.s33398.Model;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Enemies.AbstractEnemy;
import pl.edu.pja.s33398.GameObject.Food;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.GameObject.Player;

import java.util.ArrayList;
import java.util.List;

public class GameGrid {
    private final int rows, columns;
    private final GridCell[][] grid;
    private Coordinates playerSpawn;
    private Coordinates enemySpawn;

    public GameGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        this.grid = new GridCell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                this.grid[row][column] = new GridCell();
            }
        }
    }

    public GameObject getTopObject(int rowIndex, int columnIndex) {
        return this.grid[rowIndex][columnIndex].getTopObject();
    }

    public int getRowCount() {
        return this.rows;
    }

    public int getColumnCount() {
        return this.columns;
    }

    public GameGrid copy() {
        GameGrid copy = new GameGrid(this.rows, this.columns);
        copy.playerSpawn = this.playerSpawn;
        copy.enemySpawn = this.enemySpawn;
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                GridCell cell = this.grid[row][column];
                copy.setGridCell(row, column, cell.copy());
            }
        }
        return copy;
    }

    public void setGridCell(int rowIndex, int columnIndex, GridCell gridCell) {
        this.grid[rowIndex][columnIndex] = gridCell;
    }

    public Coordinates getEnemySpawn() {
        return this.enemySpawn;
    }

    public void setEnemySpawn(Coordinates enemySpawn) {
        this.enemySpawn = enemySpawn;
    }

    public Coordinates getPlayerSpawn() {
        return this.playerSpawn;
    }

    public void setPlayerSpawn(Coordinates playerSpawn) {
        this.playerSpawn = playerSpawn;
    }

    public Player getPlayer() {
        Coordinates playerCoordinates = this.getPlayerCoordinates();
        return (Player) (this.grid[playerCoordinates.row()][playerCoordinates.column()].getPlayerLayer());
    }

    public Coordinates getPlayerCoordinates() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                GridCell cell = this.grid[row][column];
                if (cell.getPlayerLayer() instanceof Player) {
                    return new Coordinates(row, column);
                }
            }
        }
        return null;
    }

    public List<Coordinates> getEnemyCoordinates() {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                GridCell cell = this.grid[row][column];
                if (cell.getEnemyLayer() instanceof AbstractEnemy) {
                    coordinates.add(new Coordinates(row, column));
                }
            }
        }
        return coordinates;
    }

    public boolean hasPlayerCollision() {
        Coordinates playerCoordinates = this.getPlayerCoordinates();

        return this.getGridCell(playerCoordinates).hasCollision();
    }

    public GridCell getGridCell(Coordinates coordinates) {
        return this.getGridCell(coordinates.row(), coordinates.column());
    }

    public GridCell getGridCell(int rowIndex, int columnIndex) {
        return this.grid[rowIndex][columnIndex];
    }

    public boolean hasFood() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                GridCell cell = this.grid[row][column];
                if (cell.getFoodLayer() instanceof Food) return true;
            }
        }
        return false;
    }
}
