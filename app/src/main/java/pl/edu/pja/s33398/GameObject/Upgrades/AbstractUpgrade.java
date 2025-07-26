package pl.edu.pja.s33398.GameObject.Upgrades;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.GameObject.GameObject;
import pl.edu.pja.s33398.Model.GameGrid;
import pl.edu.pja.s33398.Model.GameModel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUpgrade extends GameObject {
    private List<String> frames;
    private int animationFrame;

    protected void setImages(String folderName, int frames) {
        this.frames = new ArrayList<>();
        for (int i = 0; i < frames; i++) {
            this.frames.add("src/main/resources/upgrade/" + folderName + "/" + (i + 1) + ".png");
        }
    }

    @Override
    public List<String> getImagePaths() {
        return List.of(frames.get(animationFrame));
    }

//    @Override
//    public void render(Graphics g, int width, int height) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
//        g.drawImage(frames.get(animationFrame), 0, 0, width, height, null);
//    }

    @Override
    public void update(GameGrid grid, Coordinates coordinates) {
    }

    @Override
    public void animate() {
        this.animationFrame++;
        this.animationFrame = animationFrame % frames.size();
    }

    public abstract void activate(GameModel model, Coordinates coordinates);
}
