package pl.edu.pja.s33398.Highscores;

import javax.swing.*;

public class ScoreSaver {
    public static synchronized void save(int score) {
        HighscoresSingleton highscoresSingleton = HighscoresSingleton.getInstance();
        String name = JOptionPane.showInputDialog("Wynik: " + score + ". Podaj nazwę:");

        if (name == null) {
            return;
        }

        highscoresSingleton.addScore(score, name);
    }
}
