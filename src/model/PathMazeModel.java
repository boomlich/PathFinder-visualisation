package model;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.*;
import maze.algoritms.RandomDFS;
import maze.algoritms.RandomKruskal;
import maze.algoritms.RandomPrim;
import maze.algoritms.RecursiveDivision;
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
    private MazeMode mazeMode;
    private boolean inverted;
    private InteractMode interactMode;
    private int renderWidth;
    private int renderHeight;

    public PathMazeModel() {
        grid = new Grid<>(50, 50, new Tile(TileState.NORMAL));
        setStartCell(5, 5);
        pathFinder = new Pathfinder();
        mazeMode = MazeMode.PRIM;
        interactMode = InteractMode.WALL;
        renderWidth = 500;
        renderHeight = 500;
    }

    public void generateMaze() {

        MazeGenerator mazeGenerator = null;
        inverted = true;
        if (mazeMode == MazeMode.DFS) {
            mazeGenerator = new RandomDFS(grid, startCell);
        } else if (mazeMode == MazeMode.KRUSKAL) {
            mazeGenerator = new RandomKruskal(grid, startCell);
        } else if (mazeMode == MazeMode.PRIM) {
            mazeGenerator = new RandomPrim(grid, startCell);
        } else if (mazeMode == MazeMode.DIVISION) {
            mazeGenerator = new RecursiveDivision(grid, startCell);
            inverted = false;
        }

        assert mazeGenerator != null;
        mazePath = mazeGenerator.generateMaze();
        currentTile = mazePath.remove(0);
    }

    public void setMazeMode(MazeMode mazeMode) {
        this.mazeMode = mazeMode;
    }

    public void resetGrid() {
        System.out.println("reset");
        mazePath.clear();
        grid.fill(new Tile(TileState.NORMAL));
    }

    public void fastMazeGeneration() {
        while (!mazePath.isEmpty()) {
            mazeGenerationStep();
        }
    }

    public void update() {

        if (!mazePath.isEmpty()) {
            mazeGenerationStep();
        }
    }

    private void mazeGenerationStep() {
        if (inverted) {
            grid.setCell(currentTile, new Tile(TileState.NORMAL));
        } else {
            grid.setCell(currentTile, new Tile(TileState.WALL));
        }
        currentTile = mazePath.remove(0);
        grid.setCell(currentTile, new Tile(TileState.CURRENT));
    }

    @Override
    public Grid<Tile> getGrid() {
        return grid;
    }

    @Override
    public int getRenderWidth() {
        return renderWidth;
    }

    @Override
    public int getRenderHeight() {
        return renderHeight;
    }

    private void setStartCell(int x, int y) {
        if (startCell != null) {
            grid.setCell(startCell, new Tile(TileState.NORMAL));
        }
        startCell = new Point(x, y);
        grid.setCell(startCell, new Tile(TileState.START));
    }

    public void clickCell(int x, int y) {
        Point cell = new Point(x, y);
        if (grid.inBounds(cell)) {
            if (interactMode == InteractMode.WALL) {
                grid.setCell(cell, new Tile(TileState.WALL));
            } else if (interactMode == InteractMode.NORMAL){
                grid.setCell(cell, new Tile(TileState.NORMAL));
            }
        }
    }

    public void setInteractMode(InteractMode mode) {
        interactMode = mode;

    }

    public InteractMode getInteractMode() {
        return interactMode;
    }
}
