package pl.edu.pja.s33398.GameObject.Enemies;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Enum.Direction;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.Model.GameGrid;

public class Pinky extends AbstractEnemy {
    public Pinky() {
        this.setImages("pinky");
    }

    @Override
    public Coordinates updateTarget(GameGrid grid, Coordinates coordinates) {
        Player player = grid.getPlayer();
        Coordinates playerCoordinates = grid.getPlayerCoordinates();

        Direction playerDirection = player.getMoveDirection();
        return playerDirection.getRelativeCoordinates(playerCoordinates, 4);
    }
}
