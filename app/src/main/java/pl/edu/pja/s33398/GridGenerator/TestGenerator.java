package pl.edu.pja.s33398.GridGenerator;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Enemies.Blinky;
import pl.edu.pja.s33398.GameObject.Enemies.Clyde;
import pl.edu.pja.s33398.GameObject.Enemies.Inky;
import pl.edu.pja.s33398.GameObject.Enemies.Pinky;
import pl.edu.pja.s33398.GameObject.Food;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.GameObject.Wall;
import pl.edu.pja.s33398.Model.GameGrid;

public class TestGenerator implements IGridGenerator {


    @Override
    public GameGrid generate(int rows, int columns) {
        GameGrid grid = new GameGrid(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // walls around maze
                if (i == 0 || j == 0 || i == rows - 1 || j == columns - 1) {
                    grid.getGridCell(i, j).setWallLayer(new Wall());
                } else {
                    grid.getGridCell(i, j).setFoodLayer(new Food());
                }
            }
        }

        int pacmanRow = rows / 2;
        int pacmanCol = columns / 2;
        int wallCol = columns / 3;

//        for (int row = 2; row < rows; row++) {
//            grid.getGridCell(row, wallCol).setWallLayer(new Wall());
//            grid.getGridCell(row, wallCol).setFoodLayer(null);
//        }

        Coordinates pacmanCoords = new Coordinates(pacmanRow, pacmanCol);
        Coordinates enemyCoords = new Coordinates(pacmanRow, 1);

        grid.setPlayerSpawn(pacmanCoords);
        grid.getGridCell(pacmanCoords).setPlayerLayer(new Player());
        grid.setEnemySpawn(enemyCoords);
        grid.getGridCell(enemyCoords).setBlinkyLayer(new Blinky());
        grid.getGridCell(enemyCoords).setInkyLayer(new Inky());
        grid.getGridCell(enemyCoords).setPinkyLayer(new Pinky());
        grid.getGridCell(enemyCoords).setClydeLayer(new Clyde());

        return grid;
    }
}
