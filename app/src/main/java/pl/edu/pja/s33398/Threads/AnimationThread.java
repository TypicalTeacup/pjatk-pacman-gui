package pl.edu.pja.s33398.Threads;

import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GameModel;
import pl.edu.pja.s33398.Model.GridCell;
import pl.edu.pja.s33398.View.GameView;

public class AnimationThread extends Thread {
    private final GameModel gameModel;
    private final GameView gameView;

    public AnimationThread(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        // 8 FPS
        long intervalNanos = 125_000_000L;
        try {
            while (!this.isInterrupted()) {
                long startNanos = System.nanoTime();

                GameGrid grid = this.gameModel.getGrid();
                for (int row = 0; row < this.gameModel.getRowCount(); row++) {
                    for (int column = 0; column < this.gameModel.getColumnCount(); column++) {
                        GridCell cell = grid.getGridCell(row, column);
                        cell.animate();
                    }
                }
                this.gameModel.fireTableDataChanged();

                long endNanos = System.nanoTime();
                long elapsedNanos = endNanos - startNanos;
                long sleepNanos = intervalNanos - elapsedNanos;
                if (sleepNanos > 0) {
                    Thread.sleep(sleepNanos / 1_000_000L, (int) (sleepNanos / 1_000_000L));
                } else {
                    System.out.println("WARNING: Animation thread was " + Math.abs(sleepNanos) + " nanoseconds too long");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Animation thread interrupted");
        }
    }
}
