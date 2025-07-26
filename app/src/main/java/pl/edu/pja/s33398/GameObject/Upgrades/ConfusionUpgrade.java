package pl.edu.pja.s33398.GameObject.Upgrades;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Enemies.AbstractEnemy;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.Model.GameModel;

import java.util.List;

public class ConfusionUpgrade extends AbstractUpgrade {
    public ConfusionUpgrade() {
        this.setImages("confusion", 4);
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
                            ((AbstractEnemy) enemy).confuse();
                            Thread.sleep(7500);
                            ((AbstractEnemy) enemy).unconfuse();
                        } catch (InterruptedException ignored) {
                        }
                    }).start();
                }
            }
        }
    }
}
