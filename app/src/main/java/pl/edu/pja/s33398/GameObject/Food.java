package pl.edu.pja.s33398.GameObject;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Model.GameGrid;

import java.util.List;

public class Food extends GameObject {
//    @Override
//    public void render(Graphics g, int width, int height) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
//        g2d.drawImage(image, 0, 0, width, height, null);
//    }

    @Override
    public List<String> getImagePaths() {
        return List.of("src/main/resources/food.png");
    }

    @Override
    public void update(GameGrid grid, Coordinates coordinates) {
    }

    @Override
    public void animate() {
    }
}
