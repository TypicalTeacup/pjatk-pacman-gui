package pl.edu.pja.s33398.GameObject.Upgrades;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Enemies.AbstractEnemy;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.Model.GameModel;

import java.util.List;
import java.util.Random;

public class PowerPelletUpgrade extends AbstractUpgrade {
    private static final Random random = new Random();

    public PowerPelletUpgrade() {
        this.setImages("powerpellet", 1);
    }

    @Override
    public void activate(GameModel model, Coordinates coordinates) {
        List<Coordinates> enemyCoordinatesList = model.getGrid().getEnemyCoordinates();
        for (Coordinates enemyCoordinates : enemyCoordinatesList) {
            List<GameObject> enemies = model.getGrid().getGridCell(enemyCoordinates).getEnemies();
            for (GameObject enemy : enemies) {
                if (enemy instanceof AbstractEnemy) {
                    new Thread(() -> {
                        try {
                            ((AbstractEnemy) enemy).setEatable(true);
                            Thread.sleep(20_000);
                            ((AbstractEnemy) enemy).setEatable(false);
                        } catch (InterruptedException ignored) {
                        }
                    }).start();
                }
            }
        }
    }
}
