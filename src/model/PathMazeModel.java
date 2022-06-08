package model;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.*;
import pathfinding.Pathfinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PathMazeModel implements Renderable<Tile> {

    private final Grid<Tile> grid;
    private Pathfinder pathFinder;
    private Point startCell;
    private List<Point> mazePath = new ArrayList<>();
    private Point currentTile;

    public PathMazeModel() {
        grid = new Grid<>(50, 50, new Tile(TileState.NORMAL));
        setStartCell(5, 5);
        pathFinder = new Pathfinder();
    }

    public void generateMaze() {

//        MazeGenerator mazeGenerator = new RandomKruskal(grid, startCell);

//        MazeGenerator mazeGenerator = new RandomPrim(grid, startCell);

        MazeGenerator mazeGenerator = new RecursiveDivision(grid, startCell);


//        MazeGenerator mazeGenerator = new RandomDFS(grid, startCell);
        mazePath = mazeGenerator.generateMaze();
        currentTile = mazePath.remove(0);
    }

    public void update() {
//        System.out.println("updated");
//        if (!mazePath.isEmpty()) {
//            grid.setCell(currentTile, new Tile(TileState.NORMAL));
//            currentTile = mazePath.remove(0);
//            grid.setCell(currentTile, new Tile(TileState.CURRENT));
//        }
        if (!mazePath.isEmpty()) {
            grid.setCell(currentTile, new Tile(TileState.WALL));
            currentTile = mazePath.remove(0);
            grid.setCell(currentTile, new Tile(TileState.CURRENT));
        }
    }

    @Override
    public Grid<Tile> getGrid() {
        return grid;
    }

    private void setStartCell(int x, int y) {
        if (startCell != null) {
            grid.setCell(startCell, new Tile(TileState.NORMAL));
        }
        startCell = new Point(x, y);
        grid.setCell(startCell, new Tile(TileState.START));
    }
}
