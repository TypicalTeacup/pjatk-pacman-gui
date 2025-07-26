package pl.edu.pja.s33398.GameObject;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Model.GameGrid;

import java.util.List;

public class Wall extends GameObject {
    //    private static Color wallColor = new Color(138, 173, 244);
    //
    //    @Override
    //    public void render(Graphics g, int width, int height) {
    //        g.setColor(wallColor);
    //        g.fillRect(0, 0, width, height);
    //    }


    @Override
    public List<String> getImagePaths() {
        return List.of("src/main/resources/wall.png");
    }

    @Override
    public void update(GameGrid grid, Coordinates coordinates) {
    }

    @Override
    public void animate() {

    }

}
