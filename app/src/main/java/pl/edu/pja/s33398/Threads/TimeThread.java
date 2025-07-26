package pl.edu.pja.s33398.Threads;

import pl.edu.pja.s33398.Model.GameModel;
import pl.edu.pja.s33398.View.GameView;

public class TimeThread extends Thread {
    private GameModel gameModel;
    private GameView gameView;

    public TimeThread(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.isInterrupted()) return;
                Thread.sleep(1000);
                gameModel.tickTime();
                gameView.updateInfoPanel();
            }
        } catch (InterruptedException e) {
            System.out.println("Time thread interrupted");
        }
    }
}
