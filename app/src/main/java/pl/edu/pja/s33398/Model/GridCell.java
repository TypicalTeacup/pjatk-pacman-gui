package pl.edu.pja.s33398.Model;

import pl.edu.pja.s33398.GameObject.Enemies.*;
import pl.edu.pja.s33398.GameObject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GridCell {
    private GameObject blinkyLayer, inkyLayer, pinkyLayer, clydeLayer;
    private GameObject playerLayer, upgradeLayer, foodLayer, wallLayer;

    public GameObject getTopObject() {
        GameObject enemy = this.getEnemyLayer();
        if (enemy != null) {
            return enemy;
        }

        if (this.playerLayer != null) {
            return this.playerLayer;
        }

        if (this.upgradeLayer != null) {
            return this.upgradeLayer;
        }

        if (this.foodLayer != null) {
            return this.foodLayer;
        }

        return this.wallLayer;
    }

    public GameObject getEnemyLayer() {
        if (blinkyLayer != null) {
            return this.blinkyLayer;
        }
        if (inkyLayer != null) {
            return this.inkyLayer;
        }
        if (pinkyLayer != null) {
            return this.pinkyLayer;
        }
        return this.clydeLayer;
    }

    public GridCell copy() {
        GridCell cell = new GridCell();
        cell.blinkyLayer = this.blinkyLayer;
        cell.inkyLayer = this.inkyLayer;
        cell.pinkyLayer = this.pinkyLayer;
        cell.clydeLayer = this.clydeLayer;
        cell.playerLayer = this.playerLayer;
        cell.upgradeLayer = this.upgradeLayer;
        cell.foodLayer = this.foodLayer;
        cell.wallLayer = this.wallLayer;
        return cell;
    }

    public boolean hasCollision() {
        int objects = 0;

        for (GameObject layer : new GameObject[]{
                blinkyLayer,
                inkyLayer,
                pinkyLayer,
                clydeLayer,
                playerLayer,
                upgradeLayer,
                foodLayer,
                wallLayer
        })
            if (layer != null) objects++;

        return objects > 1;
    }

    public void setEnemyLayer(GameObject object, Class<? extends AbstractEnemy> enemyClass) {
        String className = "";
        if (object != null) {
            className = object.getClass().getSimpleName();
        } else {
            className = "null";
        }
        if (enemyClass.equals(Blinky.class)) {
            this.blinkyLayer = object;
            System.out.println("Blinky layer set to " + className);
        } else if (enemyClass.equals(Inky.class)) {
            this.inkyLayer = object;
            System.out.println("Inky layer set to " + className);
        } else if (enemyClass.equals(Pinky.class)) {
            this.pinkyLayer = object;
            System.out.println("Pinky layer set to " + className);
        } else if (enemyClass.equals(Clyde.class)) {
            this.clydeLayer = object;
            System.out.println("Clyde layer set to " + className);
        }
    }

    public GameObject getPlayerLayer() {
        return this.playerLayer;
    }

    public void setPlayerLayer(GameObject object) {
        this.playerLayer = object;
    }

    public GameObject getUpgradeLayer() {
        return this.upgradeLayer;
    }

    public void setUpgradeLayer(GameObject upgradeLayer) {
        this.upgradeLayer = upgradeLayer;
    }

    public GameObject getFoodLayer() {
        return this.foodLayer;
    }

    public void setFoodLayer(GameObject object) {
        this.foodLayer = object;
    }

    public GameObject getWallLayer() {
        return this.wallLayer;
    }

    public void setWallLayer(GameObject object) {
        this.wallLayer = object;
    }

    public List<GameObject> getEnemies() {
        List<GameObject> enemies = new ArrayList<>();
        for (GameObject layer : new GameObject[]{
                this.blinkyLayer,
                this.inkyLayer,
                this.pinkyLayer,
                this.clydeLayer,
        }) {
            if (layer != null) enemies.add(layer);
        }
        return enemies;
    }

    public void setBlinkyLayer(AbstractEnemy blinky) {
        this.blinkyLayer = blinky;
    }

    public void setInkyLayer(AbstractEnemy inky) {
        this.inkyLayer = inky;
    }

    public void setPinkyLayer(AbstractEnemy pinky) {
        this.pinkyLayer = pinky;
    }

    public void setClydeLayer(AbstractEnemy clyde) {
        this.clydeLayer = clyde;
    }

    public void animate() {
        for (GameObject layer : new GameObject[]{
                this.blinkyLayer,
                this.inkyLayer,
                this.pinkyLayer,
                this.clydeLayer,
                this.playerLayer,
                this.upgradeLayer,
                this.foodLayer,
                this.wallLayer
        }) {
            if (layer != null) layer.animate();
        }
    }
}
