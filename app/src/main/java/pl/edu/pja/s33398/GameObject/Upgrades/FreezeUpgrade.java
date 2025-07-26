package pl.edu.pja.s33398.GameObject.Upgrades;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Enemies.AbstractEnemy;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.Model.GameModel;

import java.util.List;
import java.util.Random;

public class FreezeUpgrade extends AbstractUpgrade {
    private static final Random random = new Random();

    public FreezeUpgrade() {
        this.setImages("freeze", 5);
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
                            ((AbstractEnemy) enemy).freeze();
                            Thread.sleep(random.nextInt(5000, 10000));
                            ((AbstractEnemy) enemy).unfreeze();
                        } catch (InterruptedException ignored) {
                        }
                    }).start();
                }
            }
        }
    }
}
