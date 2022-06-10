package model;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.*;
import pathfinding.PathFindMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PathMazeModel implements Renderable<Tile> {

    private final Grid<Tile> grid;
    private Point startCell;
    private Point goalCell;
    private InteractMode interactMode;
    private int renderWidth;
    private int renderHeight;
    private int playSpeed = 1;
    private final Random random = new Random();

    private MazeManager mazeManager;
    private PathFinderManager pathFinderManager;

    public PathMazeModel() {
        grid = new Grid<>(50, 50, new Tile(TileState.NORMAL));
        interactMode = InteractMode.WALL;
        renderWidth = 500;
        renderHeight = 500;
        mazeManager = new MazeManager();
        pathFinderManager = new PathFinderManager();
        randomStartAndGoal();
    }

    public void generateMaze(MazeMode mazeMode) {
        mazeManager.generateMaze(grid, mazeMode);
        interactMode = InteractMode.MAZE_GEN;
        if (playSpeed == 10) {
            fastMazeGeneration();
        }
    }

    public void randomStartAndGoal() {
        List<Point> freeTiles = getTilesWithState(TileState.NORMAL);

        startCell = moveCell(freeTiles.remove(random.nextInt(freeTiles.size())), startCell, TileState.START);
        goalCell = moveCell(freeTiles.remove(random.nextInt(freeTiles.size())), goalCell, TileState.GOAL);
    }

    private List<Point> getTilesWithState(TileState state) {
        List<Point> tiles = new ArrayList<>();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Point cell = new Point(x, y);
                if (grid.getCell(cell).getTileState() == state) {
                    tiles.add(cell);
                }
            }
        }
        return tiles;
    }

    public void resetGrid() {
        grid.fill(new Tile(TileState.NORMAL));
        mazeManager = new MazeManager();
        pathFinderManager = new PathFinderManager();
        interactMode = InteractMode.WALL;
        randomStartAndGoal();
    }

    public void fastMazeGeneration() {
        mazeManager.fastMazeGeneration(grid);
    }

    public void update() {
        if (!mazeManager.update(playSpeed, grid)) {
            if (interactMode == InteractMode.MAZE_GEN) {
                mazeGenerationComplete();
            }
        }
        if (!pathFinderManager.update(grid, playSpeed)) {
            if (interactMode == InteractMode.PATH_FIND) {
                pathFindComplete();
            }
        }
    }

    private void clearPath() {

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Point cell = new Point(x, y);
                TileState cellState = grid.getCell(cell).getTileState();
                if (cellState == TileState.VISITED || cellState == TileState.PATH) {
                    grid.setCell(cell, new Tile(TileState.NORMAL));
                }
            }
        }
    }

    protected void mazeGenerationComplete() {
        interactMode = InteractMode.WALL;
        randomStartAndGoal();
    }

    private void pathFindComplete() {
        interactMode = InteractMode.WALL;
    }

    public void findPath(PathFindMode pathMode) {

        clearPath();

        pathFinderManager.findPath(pathMode, startCell, goalCell, grid);
        interactMode = InteractMode.PATH_FIND;

        if (playSpeed == 10) {
            fastPath();
        }
    }

    public void fastPath() {
        pathFinderManager.fastPath(grid);
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

    private Point moveCell(Point newPosition, Point oldPosition, TileState state) {
        if (oldPosition != null) {
            grid.setCell(oldPosition, new Tile(TileState.NORMAL));
        }
        grid.setCell(newPosition, new Tile(state));
        return newPosition;
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

    public void setSpeed(int speed) {
        playSpeed = speed;
        System.out.println(playSpeed);
    }
}
