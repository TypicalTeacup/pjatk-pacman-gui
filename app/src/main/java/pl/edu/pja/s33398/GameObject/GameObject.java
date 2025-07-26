package pl.edu.pja.s33398.GameObject;

import pl.edu.pja.s33398.Coordinates;
import pl.edu.pja.s33398.Model.GameGrid;

import java.util.List;

public abstract class GameObject {
    public abstract List<String> getImagePaths();
    //public abstract void render(Graphics g, int width, int height);

    public abstract void update(GameGrid grid, Coordinates coordinates);

    public abstract void animate();
}
