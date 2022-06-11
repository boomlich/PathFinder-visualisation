package model;

import controller.Controllable;
import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.*;
import pathfinding.PathFindMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PathMazeModel implements Renderable<Tile>, Controllable {

    private Grid<Tile> grid;
    private Point startCell;
    private Point goalCell;
    private InteractMode interactMode;
    private int renderWidth;
    private int renderHeight;
    private int playSpeed = 1;
    private final Random random = new Random();

    private MazeManager mazeManager;
    private PathFinderManager pathFinderManager;

    private boolean mouseHeld;
    private TileState cellDrawMode;
    private boolean pathActive;

    public PathMazeModel() {
        init(50, 25, 1000, 500);
    }

    private void init(int x, int y, int width, int height) {
        grid = new Grid<>(x, y, new Tile(TileState.NORMAL));
        interactMode = InteractMode.WALL;
        renderWidth = width;
        renderHeight = height;
        mazeManager = new MazeManager();
        pathFinderManager = new PathFinderManager();
        startCell = null;
        goalCell = null;
        randomStartAndGoal(false);
        mouseHeld = false;
        pathActive = false;
    }

    @Override
    public void generateMaze(MazeMode mazeMode) {
        mazeManager.generateMaze(grid, mazeMode);
        interactMode = InteractMode.MAZE_GEN;
        if (playSpeed == 10) {
            fastMazeGeneration();
        }
    }

    public void randomStartAndGoal(boolean replaceOld) {
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

    @Override
    public void resetGrid() {
        grid.fill(new Tile(TileState.NORMAL));
        mazeManager = new MazeManager();
        pathFinderManager = new PathFinderManager();
        interactMode = InteractMode.WALL;
        randomStartAndGoal(false);
        mouseHeld = false;
        pathActive = false;
    }

    @Override
    public void fastMazeGeneration() {
        mazeManager.fastMazeGeneration(grid);
    }

    @Override
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

    @Override
    public void clearPath() {
        pathActive = false;
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Point cell = new Point(x, y);
                TileState cellState = grid.getCell(cell).getTileState();
                if (cellState == TileState.VISITED || cellState == TileState.PATH || cellState == TileState.SEARCH) {
                    grid.setCell(cell, new Tile(TileState.NORMAL));
                }
            }
        }
    }

    protected void mazeGenerationComplete() {
        interactMode = InteractMode.WALL;
        randomStartAndGoal(false);
    }

    private void pathFindComplete() {
        interactMode = InteractMode.WALL;
    }

    @Override
    public void findPath(PathFindMode pathMode) {
        clearPath();

        pathFinderManager.findPath(pathMode, startCell, goalCell, grid);
        interactMode = InteractMode.PATH_FIND;
        if (playSpeed == 10) {
            fastPath();
        }
        pathActive = true;
    }

    @Override
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

    private boolean moveSpecialCell(Point newPosition, Point oldPos, TileState cellState) {
        TileState state = grid.getCell(newPosition).getTileState();
        if (state == TileState.NORMAL || state == TileState.VISITED) {
            moveCell(newPosition, oldPos, cellState);
            return true;
        }
        return false;
    }


    private Point moveCell(Point newPosition, Point oldPosition, TileState state) {
        if (oldPosition != null) {
            grid.setCell(oldPosition, new Tile(TileState.NORMAL));
        }
        grid.setCell(newPosition, new Tile(state));
        return newPosition;
    }

    @Override
    public void clickCell(int x, int y) {

        Point cell = new Point(x, y);
        if (grid.inBounds(cell)) {

            if (!mouseHeld) {
                mouseHeld = true;
                cellDrawMode = grid.getCell(cell).getTileState();
            }

            if (cellDrawMode == TileState.START) {
                if (moveSpecialCell(cell, startCell, TileState.START)) {
                    startCell = cell;
                }
            } else if (cellDrawMode == TileState.GOAL) {
                if (moveSpecialCell(cell, goalCell, TileState.GOAL)) {
                    goalCell = cell;
                }
            }
            else {
                drawCell(cell);
            }
        }
        if (pathActive) {
            clearPath();
        }
    }


    @Override
    public void mouseReleased() {
        mouseHeld = false;
    }

    private void drawCell(Point cell) {
        if (interactMode == InteractMode.WALL) {
            grid.setCell(cell, new Tile(TileState.WALL));
        } else if (interactMode == InteractMode.NORMAL){
            grid.setCell(cell, new Tile(TileState.NORMAL));
        }
    }

    @Override
    public void setInteractMode(InteractMode mode) {
        interactMode = mode;
    }

    @Override
    public InteractMode getInteractMode() {
        return interactMode;
    }

    @Override
    public void setSpeed(int speed) {
        playSpeed = speed;
        System.out.println(playSpeed);
    }

    @Override
    public void setGridResolution(int width, int height) {
        int renderWidth = 500;
        if (height * 2 == width) {
            renderWidth = 1000;
        }
        init(width, height, renderWidth, 500);
    }
}
