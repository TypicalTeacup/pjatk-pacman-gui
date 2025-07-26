package pl.edu.pja.s33398.Threads;

import pl.edu.pja.s33398.Controller.GameController;
import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.Enemies.*;
import pl.edu.pja.s33398.GameObject.Food;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.GameObject.Upgrades.AbstractUpgrade;
import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GameModel;
import pl.edu.pja.s33398.Model.GridCell;
import pl.edu.pja.s33398.View.GameView;

import java.util.List;

public class GameThread extends Thread {
    private GameModel gameModel;
    private GameView gameView;
    private GameController gameController;
    private int counter = 0;

    public GameThread(GameModel gameModel, GameView gameView, GameController gameController) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        long intervalMillis = 250;
        try {
            Thread.sleep(1000);
            while (!this.isInterrupted()) {
                this.counter++;
                this.counter %= 2;
                long startTime = System.currentTimeMillis();
                GameGrid nextGrid = this.gameModel.getGrid().copy();

                Player player = nextGrid.getPlayer();
                Coordinates playerCoordinates = this.gameModel.getGrid().getPlayerCoordinates();

                if (counter == 0) {
                    player.update(nextGrid, playerCoordinates);

                    List<Coordinates> enemyCoordinatesList = nextGrid.getEnemyCoordinates();
                    for (Coordinates enemyCoordinates : enemyCoordinatesList) {
                        List<GameObject> enemies = nextGrid.getGridCell(enemyCoordinates).getEnemies();
                        for (GameObject gameObject : enemies) {
                            gameObject.update(nextGrid, enemyCoordinates);
                        }
                    }

                } else if (player.isSpedUp) {
                    player.update(nextGrid, playerCoordinates);
                }

                playerCoordinates = nextGrid.getPlayerCoordinates();
                // collision
                GridCell playerCell = nextGrid.getGridCell(playerCoordinates);
                if (nextGrid.hasPlayerCollision()) {
                    GameObject enemyLayer = playerCell.getEnemyLayer();
                    if (enemyLayer instanceof AbstractEnemy) {
                        AbstractEnemy enemy = (AbstractEnemy) enemyLayer;
                        if (enemy.isEatable) {
                            this.gameModel.addScore(20);
                            GridCell targetCell = nextGrid.getGridCell(nextGrid.getEnemySpawn());
                            switch (enemy) {
                                case Blinky blinky -> {
                                    targetCell.setBlinkyLayer(blinky);
                                    playerCell.setBlinkyLayer(null);
                                }
                                case Inky inky -> {
                                    targetCell.setInkyLayer(inky);
                                    playerCell.setInkyLayer(null);
                                }
                                case Pinky pinky -> {
                                    targetCell.setPinkyLayer(pinky);
                                    playerCell.setPinkyLayer(null);
                                }
                                case Clyde clyde -> {
                                    targetCell.setClydeLayer(clyde);
                                    playerCell.setClydeLayer(null);
                                }
                                default -> {
                                }
                            }
                        } else {
                            this.gameController.die(nextGrid);
                        }
                    }
                    if (playerCell.getFoodLayer() instanceof Food) {
                        playerCell.setFoodLayer(null);
                        this.gameModel.addScore(1);
                        this.gameView.updateInfoPanel();
                    }
                    GameObject upgradeLayer = playerCell.getUpgradeLayer();
                    if (upgradeLayer instanceof AbstractUpgrade) {
                        ((AbstractUpgrade) upgradeLayer).activate(this.gameModel, playerCoordinates);
                        playerCell.setUpgradeLayer(null);
                    }
                }

                // magnet upgrade
                if (player.isMagnetActive) {
                    for (int rowOffset = -2; rowOffset <= 2; rowOffset++) {
                        for (int colOffset = -2; colOffset <= 2; colOffset++) {
                            int row = Math.clamp(playerCoordinates.row() + rowOffset, 0, nextGrid.getRowCount() - 1);
                            int column = Math.clamp(playerCoordinates.column() + colOffset, 0, nextGrid.getColumnCount() - 1);
                            GridCell cell = nextGrid.getGridCell(row, column);
                            if (cell.getFoodLayer() instanceof Food) {
                                cell.setFoodLayer(null);
                                this.gameModel.addScore(1);
                            }
                        }
                    }
                    this.gameView.updateInfoPanel();
                }

                if (!nextGrid.hasFood()) {
                    this.gameModel.addScore(this.gameModel.getLives() * 100);
                    this.gameView.dispose();
                    break;
                }

                this.gameModel.setGrid(nextGrid);
                this.gameModel.fireTableDataChanged();

                long elapsedTime = System.currentTimeMillis() - startTime;
                long sleepTime = intervalMillis - elapsedTime;

                if (sleepTime > 0) Thread.sleep(sleepTime);
                else System.out.println("WARNING: Update took " + Math.abs(sleepTime) + " millis too long");

            }
        } catch (InterruptedException e) {
            System.out.println("Game thread interrupted");
        }
    }
}
