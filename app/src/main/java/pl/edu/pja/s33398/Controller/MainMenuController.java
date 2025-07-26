package pl.edu.pja.s33398.Controller;

import pl.edu.pja.s33398.GridGenerator.MazeGenerator;
import pl.edu.pja.s33398.Model.GameModel;
import pl.edu.pja.s33398.View.GameView;
import pl.edu.pja.s33398.View.HighScoresView;
import pl.edu.pja.s33398.View.MainMenuView;

import javax.swing.*;

public class MainMenuController {
    private final MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;

        this.view.newGameButton.addActionListener(e -> {
            final Integer rows = this.getIntegerFromUser("Podaj wysokość: ", 20);
            if (rows == null) {
                return;
            }
            final Integer columns = this.getIntegerFromUser("Podaj szerokość: ", 20);
            if (columns == null) {
                return;
            }

            SwingUtilities.invokeLater(() -> {
                GameModel gameModel = new GameModel(rows, columns, new MazeGenerator());
                GameView gameView = new GameView(gameModel);
                new GameController(gameModel, gameView, () -> view.setVisible(true));
            });
            this.view.setVisible(false);
        });

        this.view.exitButton.addActionListener(e -> {
            System.exit(0);
        });

        this.view.highScoresButton.addActionListener(e -> new HighScoresView());
    }

    private Integer getIntegerFromUser(String message, int defaultValue) {
        while (true) {
            String input = JOptionPane.showInputDialog(message, defaultValue);

            if (input == null) {
                return null;
            }

            try {
                int value = Integer.parseInt(input);
                if (value < 10 || value > 100) {
                    throw new IllegalArgumentException();
                }
                if (value % 2 == 0) {
                    return value + 1;
                }
                return value;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Podaj liczbę od 10 do 100", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
