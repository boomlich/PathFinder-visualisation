package model;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import pathfinding.PathAStar;
import pathfinding.PathDijkstra;
import pathfinding.PathFindMode;
import pathfinding.Pathfinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PathFinderManager {


    private List<Point> shortestPath;
    private List<Point> pathSearch;
    private Point currentCell;

    public PathFinderManager() {
        shortestPath = new ArrayList<>();
        pathSearch = new ArrayList<>();
    }

    /**
     * Update the cell with new state, except if the cell has a target state.
     *
     * @param grid grid with all cells
     * @param cell  current cell
     * @param newState new state of the cell
     * @param exceptState state to avoid updating the cell if the current matches
     */
    private void updateCellWithStateException(Grid<Tile> grid, Point cell, TileState newState, TileState exceptState) {
        if (grid.getCell(cell).getTileState() != exceptState) {
            grid.setCell(cell, new Tile(newState));
        }
    }

    private void pathFinderStep(Grid<Tile> grid) {
        if (!pathSearch.isEmpty()) {
            updateCellWithStateException(grid, currentCell, TileState.VISITED, TileState.START);
            currentCell = pathSearch.remove(0);
            updateCellWithStateException(grid, currentCell, TileState.SEARCH, TileState.START);
        } else if (!shortestPath.isEmpty()) {
            currentCell = shortestPath.remove(0);
            grid.setCell(currentCell, new Tile(TileState.PATH));
        }
    }

    public void findPath(PathFindMode pathMode, Point start, Point goal, Grid<Tile> grid) {

        Pathfinder pathFinder = null;
        if (pathMode == PathFindMode.DIJKSTRA) {
            pathFinder = new PathDijkstra(start, goal, grid);
        } else if (pathMode == PathFindMode.ASTAR) {
            pathFinder = new PathAStar(start, goal, grid);
        }

        assert pathFinder != null;
        shortestPath = pathFinder.findPath();
        pathSearch = pathFinder.getSearchPath();
        currentCell = pathSearch.remove(0);

    }

    public void fastPath(Grid<Tile> grid) {
        while (!shortestPath.isEmpty()) {
            pathFinderStep(grid);
        }
    }

    public boolean update(Grid<Tile> grid, int steps) {
        for (int i = 0; i < steps; i++) {
            if (notCompletedPath()) {
                pathFinderStep(grid);
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean notCompletedPath() {
        return !shortestPath.isEmpty();
    }

}
