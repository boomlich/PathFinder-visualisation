package maze;

import grid.Grid;
import grid.Tile;
import grid.TileState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MazeGenerator {

    protected Grid<Tile> grid;
    protected List<Point> visited;
    protected List<Point> path;
    protected Random random = new Random();

    public MazeGenerator(Grid<Tile> grid, boolean fillWalls) {
        this.grid = grid;
        if (fillWalls) {
            this.grid.fill(new Tile(TileState.WALL));
        }
        path = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public void update() {

    }

    public abstract List<Point> generateMaze();


    protected List<Point> selectCrossNeighbour(Point cell, boolean horizontal) {

        List<Point> neighbours = new ArrayList<>();
        Point a;
        Point b;
        if (horizontal) {
            a = new Point(cell.x - 1, cell.y);
            b = new Point(cell.x + 1, cell.y);
        } else {
            a = new Point(cell.x, cell.y - 1);
            b = new Point(cell.x, cell.y + 1);
        }

        if (grid.inBounds(a)) {
            neighbours.add(a);
        }
        if (grid.inBounds(b)) {
            neighbours.add(b);
        }

        return neighbours;
    }

}
