package pl.edu.pja.s33398.GridGenerator;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Enum.Direction;
import pl.edu.pja.s33398.GameObject.Enemies.Blinky;
import pl.edu.pja.s33398.GameObject.Enemies.Clyde;
import pl.edu.pja.s33398.GameObject.Enemies.Inky;
import pl.edu.pja.s33398.GameObject.Enemies.Pinky;
import pl.edu.pja.s33398.GameObject.Food;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.GameObject.Wall;
import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GridCell;

import java.util.*;

public class MazeGenerator implements IGridGenerator {
    private final Random random = new Random();
    private GameGrid grid;
    private boolean[][] visited;
    private int rows;
    private int columns;


    @Override
    public GameGrid generate(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new GameGrid(rows, columns);
        this.visited = new boolean[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                grid.getGridCell(row, column).setWallLayer(new Wall());
            }
        }

        // start from middle
        dfs(new Coordinates(1, 1));

        int spawnColumn = columns / 2 + 1;
        Coordinates playerSpawn = new Coordinates(rows - 2, spawnColumn);
        Coordinates enemySpawn = new Coordinates(1, spawnColumn);

        grid.setPlayerSpawn(playerSpawn);
        grid.setEnemySpawn(enemySpawn);

        grid.getGridCell(playerSpawn).setPlayerLayer(new Player());

        grid.getGridCell(enemySpawn).setBlinkyLayer(new Blinky());
        grid.getGridCell(enemySpawn).setInkyLayer(new Inky());
        grid.getGridCell(enemySpawn).setPinkyLayer(new Pinky());
        grid.getGridCell(enemySpawn).setClydeLayer(new Clyde());

        fixup();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Coordinates coords = new Coordinates(row, column);
                if (coords.equals(playerSpawn)) continue;
                if (coords.equals(enemySpawn)) continue;

                if (grid.getGridCell(coords).getWallLayer() == null) grid.getGridCell(coords).setFoodLayer(new Food());
            }
        }

        return grid;
    }

    private void dfs(Coordinates coords) {
        visited[coords.row()][coords.column()] = true;
        this.grid.getGridCell(coords).setWallLayer(null);

        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions, random);

        for (Direction direction : directions) {
            Coordinates newCellCoords = direction.getRelativeCoordinates(coords, 2);
            Coordinates wallCoords = direction.getRelativeCoordinates(coords, 1);

            if (isValidCell(newCellCoords) && !visited[newCellCoords.row()][newCellCoords.column()]) {
                this.grid.getGridCell(wallCoords).setWallLayer(null);
                dfs(newCellCoords);
            }
        }
    }

    private void fixup() {
        for (int row = 1; row < rows - 1; row += 2) {
            for (int column = 1; column < columns - 1; column += 2) {
                Coordinates coords = new Coordinates(row, column);
                GridCell cell = this.grid.getGridCell(row, column);
                if (cell.getWallLayer() == null) {
                    getRidOfDeadEnd(coords);
                }
            }
        }

        // break some walls
        for (int row = 2; row < rows - 1; row += 2) {
            for (int column = 2; column < columns - 1; column += 2) {
                Coordinates coords = new Coordinates(row, column);
                GridCell cell = this.grid.getGridCell(coords);
                if (cell.getWallLayer() instanceof Wall) {
                    tryBreakingWall(coords);
                }
            }
        }
    }

    private boolean canBreakWall(Coordinates coords) {
        GridCell upCell = grid.getGridCell(Direction.UP.getRelativeCoordinates(coords, 1));
        GridCell downCell = grid.getGridCell(Direction.DOWN.getRelativeCoordinates(coords, 1));
        GridCell leftCell = grid.getGridCell(Direction.LEFT.getRelativeCoordinates(coords, 1));
        GridCell rightCell = grid.getGridCell(Direction.RIGHT.getRelativeCoordinates(coords, 1));

        int wallsAround = 0;
        wallsAround += upCell.getWallLayer() instanceof Wall ? 1 : 0;
        wallsAround += downCell.getWallLayer() instanceof Wall ? 1 : 0;
        wallsAround += leftCell.getWallLayer() instanceof Wall ? 1 : 0;
        wallsAround += rightCell.getWallLayer() instanceof Wall ? 1 : 0;

        boolean hasVerticalNeighbors = upCell.getWallLayer() instanceof Wall && downCell.getWallLayer() instanceof Wall;
        boolean hasHorizontalNeighbors = leftCell.getWallLayer() instanceof Wall && rightCell.getWallLayer() instanceof Wall;

        return (hasVerticalNeighbors ^ hasHorizontalNeighbors) && wallsAround == 2;
    }

    private void tryBreakingWall(Coordinates coords) {
        if (canBreakWall(coords) && random.nextInt(3) == 0) {
            grid.getGridCell(coords).setWallLayer(null);
        }
    }

    private void getRidOfDeadEnd(Coordinates coords) {
        Coordinates[] neighbourCoordsArray = {
                Direction.UP.getRelativeCoordinates(coords, 1),
                Direction.DOWN.getRelativeCoordinates(coords, 1),
                Direction.LEFT.getRelativeCoordinates(coords, 1),
                Direction.RIGHT.getRelativeCoordinates(coords, 1)
        };

        int wallsAround = 0;
        for (Coordinates neighbourCoords : neighbourCoordsArray) {
            if (grid.getGridCell(neighbourCoords).getWallLayer() instanceof Wall)
                wallsAround++;
        }

        if (wallsAround >= 3) {
            List<GridCell> validWallsToBreak = new ArrayList<>();
            for (Coordinates neighbourCoords : neighbourCoordsArray) {
                if (isValidCell(neighbourCoords) && grid.getGridCell(neighbourCoords).getWallLayer() instanceof Wall) {
                    validWallsToBreak.add(grid.getGridCell(neighbourCoords));
                }
            }
            GridCell wallToBreak = validWallsToBreak.get(random.nextInt(validWallsToBreak.size()));
            wallToBreak.setWallLayer(null);
        }
    }

    private boolean isValidCell(Coordinates coords) {
        return coords.row() > 0 && coords.column() > 0 && coords.row() < rows - 1 && coords.column() < columns - 1;
    }
}