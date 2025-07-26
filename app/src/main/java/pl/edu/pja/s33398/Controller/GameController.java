package pl.edu.pja.s33398.Controller;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Enum.Direction;
import pl.edu.pja.s33398.GameObject.Player;
import pl.edu.pja.s33398.Highscores.ScoreSaver;
import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GameModel;
import pl.edu.pja.s33398.Threads.AnimationThread;
import pl.edu.pja.s33398.Threads.GameThread;
import pl.edu.pja.s33398.Threads.TimeThread;
import pl.edu.pja.s33398.View.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {
    public static final KeyStroke quitBind = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK);

    private Runnable onQuit;

    private GameModel model;
    private GameView view;
    private TimeThread timeThread;
    private GameThread gameThread;
    private AnimationThread animationThread;

    public GameController(GameModel model, GameView view, Runnable onQuit) {
        this.model = model;
        this.view = view;
        this.view.onDispose(() -> stopGame());
        this.onQuit = onQuit;

        this.setupBinds();

        this.gameThread = new GameThread(this.model, this.view, this);
        this.gameThread.start();

        this.animationThread = new AnimationThread(this.model, this.view);
        this.animationThread.start();

        this.timeThread = new TimeThread(this.model, this.view);
        this.timeThread.start();
    }

    private void setupBinds() {
        this.view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Player player = model.getGrid().getPlayer();
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> player.setPreferredDirection(Direction.UP);
                    case KeyEvent.VK_DOWN -> player.setPreferredDirection(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> player.setPreferredDirection(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> player.setPreferredDirection(Direction.RIGHT);
                }
            }
        });

        JRootPane rootPane = this.view.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(quitBind, "quit");
        rootPane.getActionMap().put("quit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
    }

    public void stopGame() {
        SwingUtilities.invokeLater(() -> {
            this.gameThread.interrupt();
            this.animationThread.interrupt();
            ScoreSaver.save(this.model.getScore());
            this.onQuit.run();
        });
    }

    public void die(GameGrid grid) {
        Coordinates playerCoordinates = grid.getPlayerCoordinates();
        Coordinates playerSpawn = grid.getPlayerSpawn();

        this.model.subtractLife();
        if (this.model.getLives() > 0 && playerSpawn != null) {
            Player player = grid.getPlayer();
            System.out.println(playerSpawn);
            System.out.println(playerCoordinates);
            grid.getGridCell(playerSpawn).setPlayerLayer(player);
            grid.getGridCell(playerCoordinates).setPlayerLayer(null);
        } else {
            this.view.dispose();
        }
    }
}
