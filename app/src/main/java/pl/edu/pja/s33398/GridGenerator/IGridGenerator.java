package pl.edu.pja.s33398.GridGenerator;

import pl.edu.pja.s33398.Model.GameGrid;

public interface IGridGenerator {
    public GameGrid generate(int rows, int columns);
}
