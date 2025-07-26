package pl.edu.pja.s33398.Renderer;

import pl.edu.pja.s33398.Highscores.Score;

import javax.swing.*;
import java.awt.*;

public class ScoreRenderer extends JLabel implements ListCellRenderer<Score> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Score> list, Score value, int index, boolean isSelected, boolean hasFocus) {
        String format = "%d. %s (%d points)";
        this.setText(String.format(format, index + 1, value.name(), value.score()));
        switch (index) {
            case 0:
                this.setForeground(new Color(0xeed49f));
                break;
            case 1:
                this.setForeground(new Color(0xb7bdf8));
                break;
            case 2:
                this.setForeground(new Color(0xf5a97f));
                break;
            default:
                this.setForeground(new Color(0xcad3f5));
        }
        this.setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
}
