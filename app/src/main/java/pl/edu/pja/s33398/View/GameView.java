package pl.edu.pja.s33398.View;

import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.Model.GameModel;
import pl.edu.pja.s33398.Renderer.GameObjectRenderer;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameView extends JFrame {
    private final GameModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel timeLabel;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private Runnable onDispose;

    public GameView(GameModel model) {
        this.model = model;

        this.setupFrame();
        this.setVisible(true);
        this.setupTable();

        // positioning
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void setupFrame() {
        this.setTitle("PACMAN - Game");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void setupTable() {
        this.table = new JTable(model) {
            @Override
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };

        // visual
        this.table.setTableHeader(null);
        this.table.setDefaultRenderer(GameObject.class, new GameObjectRenderer());
        this.table.setShowGrid(false);
        this.table.setIntercellSpacing(new Dimension(0, 0));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.add(this.table);

        this.scrollPane = new JScrollPane(centerPanel);
        this.add(this.scrollPane, BorderLayout.CENTER);

        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));

        Font topPanelFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);

        this.timeLabel = new JLabel("Time: 00:00");
        this.timeLabel.setFont(topPanelFont);
        topPanel.add(this.timeLabel);

        this.scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        this.scoreLabel.setFont(topPanelFont);
        topPanel.add(scoreLabel);

        this.livesLabel = new JLabel("Lives: 3", SwingConstants.CENTER);
        this.livesLabel.setFont(topPanelFont);
        topPanel.add(livesLabel);
        topPanel.setName("header");

        add(topPanel, BorderLayout.NORTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeSquareCells();
            }
        });

        // make table cells not selectable
        this.table.setFocusable(false);
        this.table.setRowSelectionAllowed(false);

        resizeSquareCells();
    }

    private void resizeSquareCells() {
        int tableWidth = this.scrollPane.getViewport().getWidth();
        int tableHeight = this.scrollPane.getViewport().getHeight();

        int columnCount = this.table.getColumnCount();
        int rowCount = this.table.getRowCount();

        if (columnCount == 0 || rowCount == 0) return;

        int maxCellWidth = tableWidth / columnCount;
        int maxCellHeight = tableHeight / rowCount;

        int squareSize = Math.max(Math.min(maxCellWidth, maxCellHeight), 16);

        table.setRowHeight(squareSize);
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(squareSize);
        }

        table.revalidate();
    }

    public void updateInfoPanel() {
        this.timeLabel.setText("Time: " + this.model.getTime());
        this.scoreLabel.setText("Score: " + this.model.getScore());
        this.livesLabel.setText("Lives: " + this.model.getLives());
    }

    public void onDispose(Runnable onDispose) {
        this.onDispose = onDispose;
    }

    @Override
    public void dispose() {
        super.dispose();
        this.onDispose.run();
    }
}
