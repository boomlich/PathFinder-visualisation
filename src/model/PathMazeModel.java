package model;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.*;
import maze.algoritms.RandomDFS;
import maze.algoritms.RandomKruskal;
import maze.algoritms.RandomPrim;
import maze.algoritms.RecursiveDivision;
import pathfinding.PathDijkstra;
import pathfinding.PathFindMode;
import pathfinding.Pathfinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PathMazeModel implements Renderable<Tile> {

    private final Grid<Tile> grid;
    private Pathfinder pathFinder;
    private Point startCell;
    private List<Point> mazePath = new ArrayList<>();
    private List<Point> pathSearch = new ArrayList<>();
    private List<Point> shortestPath = new ArrayList<>();
    private Point currentTile;
    private MazeMode mazeMode;
    private PathFindMode pathMode;
    private boolean inverted;
    private InteractMode interactMode;
    private int renderWidth;
    private int renderHeight;
    private int playSpeed = 1;
    private final Random random = new Random();
    private List<Point> freeTiles;

    public PathMazeModel() {
        grid = new Grid<>(50, 50, new Tile(TileState.NORMAL));
        setStartCell(5, 5);
        mazeMode = MazeMode.PRIM;
        interactMode = InteractMode.WALL;
        renderWidth = 500;
        renderHeight = 500;
        freeTiles = new ArrayList<>();
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
        freeTiles = getTilesWithState(TileState.NORMAL);
        currentTile = mazePath.remove(0);
        interactMode = InteractMode.MAZE_GEN;

        if (playSpeed == 10) {
            fastMazeGeneration();
        }
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

    public void setMazeMode(MazeMode mazeMode) {
        this.mazeMode = mazeMode;
    }

    public void resetGrid() {
        mazePath.clear();
        shortestPath.clear();
        pathSearch.clear();
        grid.fill(new Tile(TileState.NORMAL));
        interactMode = InteractMode.WALL;
    }

    public void fastMazeGeneration() {
        while (!mazePath.isEmpty()) {
            mazeGenerationStep();
        }
    }

    public void update() {

        for (int i = 0; i < playSpeed; i++) {
            if (interactMode == InteractMode.PATH_FIND) {
                pathFinderStep();
            } else if (interactMode == InteractMode.MAZE_GEN) {
                mazeGenerationStep();
            }
        }
    }

    private void mazeGenerationStep() {
        if (!mazePath.isEmpty()) {
            if (inverted) {
                grid.setCell(currentTile, new Tile(TileState.NORMAL));
            } else {
                grid.setCell(currentTile, new Tile(TileState.WALL));
            }
            currentTile = mazePath.remove(0);
            grid.setCell(currentTile, new Tile(TileState.CURRENT));
        } else {
            interactMode = InteractMode.WALL;
            Point startPoint = freeTiles.remove(random.nextInt(freeTiles.size()));
            Point endPoint = freeTiles.remove(random.nextInt(freeTiles.size()));
        }
    }

    private void pathFinderStep() {
        if (!pathSearch.isEmpty()) {
            grid.setCell(currentTile, new Tile(TileState.VISITED));
            currentTile = pathSearch.remove(0);
            grid.setCell(currentTile, new Tile(TileState.SEARCH));
        } else if (!shortestPath.isEmpty()) {
            currentTile = shortestPath.remove(0);
            grid.setCell(currentTile, new Tile(TileState.PATH));
        } else {
            interactMode = InteractMode.WALL;
        }
    }

    public void findPath() {

        Point start = new Point(1, 2);
        Point end = new Point(41, 48);

        if (pathMode == PathFindMode.DIJKSTRA) {
            pathFinder = new PathDijkstra(start, end, grid);
        }

        shortestPath = pathFinder.findPath();
        pathSearch = pathFinder.getSearchPath();
        currentTile = pathSearch.remove(0);

        interactMode = InteractMode.PATH_FIND;

        if (playSpeed == 10) {
            fastPath();
        }
    }

    public void fastPath() {
        while (!shortestPath.isEmpty()) {
            pathFinderStep();
        }
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

    private int generateRandomPoint() {
        List<Point> validPoints = new ArrayList<>();


        return 0;
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

    public void setPathMode(PathFindMode pathMode) {
        this.pathMode = pathMode;
    }

    public void setSpeed(int speed) {
        playSpeed = speed;
        System.out.println(playSpeed);
    }
}
