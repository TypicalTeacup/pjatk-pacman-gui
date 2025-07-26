package pl.edu.pja.s33398.View;

import pl.edu.pja.s33398.Highscores.HighscoresSingleton;
import pl.edu.pja.s33398.Highscores.Score;
import pl.edu.pja.s33398.Renderer.ScoreRenderer;

import javax.swing.*;
import java.awt.*;

public class HighScoresView extends JFrame {
    private JPanel panel;
    private JList<Score> list;

    public HighScoresView() {
        super();
        this.setupFrame();
        this.setupList();

        this.setVisible(true);
    }

    private void setupFrame() {
        this.setTitle("PACMAN - Highscores");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300, 600);
        this.setLocationRelativeTo(null);

        JLabel title = new JLabel("High Scores");
        title.setName("header");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);
    }

    private void setupList() {
        HighscoresSingleton highscoresSingleton = HighscoresSingleton.getInstance();
        DefaultListModel<Score> model = new DefaultListModel<>();
        highscoresSingleton.getScores().forEach(model::addElement);
        this.list = new JList<>(model);
        this.list.setCellRenderer(new ScoreRenderer());
        this.add(new JScrollPane(list), BorderLayout.CENTER);
    }
}
