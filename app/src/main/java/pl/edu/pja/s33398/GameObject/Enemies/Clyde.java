package pl.edu.pja.s33398.GameObject.Enemies;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Model.GameGrid;

public class Clyde extends AbstractEnemy {
    public Clyde() {
        this.setImages("clyde");
    }

    @Override
    public Coordinates updateTarget(GameGrid grid, Coordinates coordinates) {
        Coordinates player = grid.getPlayerCoordinates();
        int distanceSquared = coordinates.getDistanceSquared(player);
        if (distanceSquared < 64) {
            return new Coordinates(grid.getRowCount() - 1, 0);
        }
        return player;
    }
}
