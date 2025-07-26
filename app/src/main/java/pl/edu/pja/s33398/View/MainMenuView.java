package pl.edu.pja.s33398.View;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    public JButton newGameButton, highScoresButton, exitButton;
    private JPanel panel;

    public MainMenuView() {
        super();
        this.setupFrame();
        this.addButtons();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setupFrame() {
        this.setTitle("PACMAN - Main Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        this.setLayout(new GridBagLayout()); // centers the panel
        this.panel = new JPanel();
        this.panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(this.panel);
    }

    private void addButtons() {
        this.newGameButton = new JButton("New game");
        this.newGameButton.setName("Button");
        this.newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.add(this.newGameButton);

        this.highScoresButton = new JButton("High Scores");
        this.newGameButton.setName("Button");
        this.highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.add(this.highScoresButton);

        this.exitButton = new JButton("Exit");
        this.newGameButton.setName("Button");
        this.exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.add(this.exitButton);
    }
}
