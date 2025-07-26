package pl.edu.pja.s33398.GameObject.Enemies;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Enum.Direction;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GridCell;

import java.util.List;

public class Inky extends AbstractEnemy {
    public Inky() {
        this.setImages("inky");
    }

    @Override
    public Coordinates updateTarget(GameGrid grid, Coordinates coordinates) {
        Player player = grid.getPlayer();
        Coordinates playerCoordinates = grid.getPlayerCoordinates();
        Direction playerDirection = player.getMoveDirection();

        Coordinates intermediateTile = playerDirection.getRelativeCoordinates(playerCoordinates, 2);
        // find blinky
        List<Coordinates> enemyCoordinatesList = grid.getEnemyCoordinates();
        Coordinates blinkyCoordinates = null;
        blinkyFinder:
        for (Coordinates enemyCoordinates : enemyCoordinatesList) {
            GridCell cell = grid.getGridCell(enemyCoordinates);
            List<GameObject> enemiesInCell = cell.getEnemies();
            for (GameObject gameObject : enemiesInCell) {
                if (gameObject instanceof Blinky) {
                    blinkyCoordinates = enemyCoordinates;
                    break blinkyFinder;
                }
            }
        }

        if (blinkyCoordinates == null) return null;

        int rowOffset = blinkyCoordinates.row() - intermediateTile.row();
        int columnOffset = blinkyCoordinates.column() - intermediateTile.column();
        return new Coordinates(intermediateTile.row() - rowOffset, intermediateTile.column() - columnOffset);

    }
}
