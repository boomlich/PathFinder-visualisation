package maze;

import grid.Grid;
import grid.Tile;
import grid.TileState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MazeGenerator {

    protected Grid<Tile> grid;
    protected List<Point> visited;
    protected List<Point> path;

    public MazeGenerator(Grid<Tile> grid) {
        this.grid = grid;
        this.grid.fill(new Tile(TileState.WALL));
        path = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public void update() {

    }

    public abstract List<Point> generateMaze();

}
