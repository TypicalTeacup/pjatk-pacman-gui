package pl.edu.pja.s33398.Renderer;

import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.GraphicsHelper;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameObjectRenderer implements TableCellRenderer {
    private static final Color BACKGROUND_COLOR = new Color(36, 39, 58);
    private static final int MAX_LAYERS = 5;
    private JPanel panel;
    private List<JLabel> labels = new ArrayList<>(MAX_LAYERS);
    private GameObject gameObject;

    public GameObjectRenderer() {
        this.panel = new JPanel(null);
        for (int i = 0; i < MAX_LAYERS; i++) {
            JLabel label = new JLabel();
            panel.add(label);
            labels.add(label);
        }
    }

    //@Override
    //protected void paintComponent(Graphics g) {
    //    super.paintComponent(g);
    //
    //    g.setColor(BACKGROUND_COLOR);
    //    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    //
    //    if (gameObject != null) {
    //        gameObject.render(g, this.getWidth(), this.getHeight());
    //    }
    //}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setOpaque(true);

        for (int i = 0; i < MAX_LAYERS; i++) {
            labels.get(i).setIcon(null);
        }

        if (value == null) {
            this.gameObject = null;
            return panel;
        }
        if (value instanceof GameObject) {
            this.gameObject = (GameObject) value;
        }

        int width = table.getColumnModel().getColumn(column).getWidth();
        int height = table.getRowHeight();

        List<String> paths = gameObject.getImagePaths();

        for (int i = 0; i < Math.min(paths.size(), MAX_LAYERS); i++) {
            String path = paths.get(i);
            JLabel label = labels.get(i);
            ImageIcon imageIcon;
            try {
                imageIcon = GraphicsHelper.getImageIcon(path, width, height);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            label.setIcon(imageIcon);
            label.setBounds(0, 0, width, height);
        }

        return panel;
    }

}
