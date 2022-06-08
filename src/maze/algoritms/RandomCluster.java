package maze.algoritms;

import grid.Grid;
import grid.Tile;
import maze.MazeGenerator;

import java.awt.*;
import java.util.List;

public class RandomCluster extends MazeGenerator {
    public RandomCluster(Grid<Tile> grid, boolean fillWalls) {
        super(grid, fillWalls);
    }

    @Override
    public List<Point> generateMaze() {
        return null;
    }
}
