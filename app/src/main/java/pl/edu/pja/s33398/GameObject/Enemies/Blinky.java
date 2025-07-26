package pl.edu.pja.s33398.GameObject.Enemies;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Model.GameGrid;


public class Blinky extends AbstractEnemy {
    public Blinky() {
        this.setImages("blinky");
    }

    @Override
    public Coordinates updateTarget(GameGrid grid, Coordinates coordinates) {
        return grid.getPlayerCoordinates();
    }
}
