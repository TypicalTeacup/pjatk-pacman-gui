package pl.edu.pja.s33398.GridGenerator;

import pl.edu.pja.s33398.GameObject.Wall;
import pl.edu.pja.s33398.Model.GameGrid;

import java.util.Random;

public class RandomWallGenerator implements IGridGenerator {
    public final Random rand = new Random();

    @Override
    public GameGrid generate(int rows, int columns) {
        GameGrid grid = new GameGrid(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // walls around maze
                if (i == 0 || j == 0 || i == rows - 1 || j == columns - 1) {
                    grid.getGridCell(i, j).setWallLayer(new Wall());
                } else {
                    if (rand.nextBoolean()) {
                        grid.getGridCell(i, j).setWallLayer(new Wall());
                    }
                }
            }
        }
        return grid;
    }
}
