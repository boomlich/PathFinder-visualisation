package pathfinding;

import grid.Grid;
import grid.Tile;
import grid.TileState;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PathDijkstra extends Pathfinder{

    private final List<Point> visited;
    protected final HashSet<Point> unvisited;
    protected final HashMap<Point, Integer> lengthFromStart;
    protected final HashMap<Point, Integer> lengthToGoal;
    protected final HashMap<Point, Point> fromWho;
    private final Point start;
    protected final Point goal;
    private final Grid<Tile> grid;

    public PathDijkstra(Point start, Point goal, Grid<Tile> grid) {
        this.start = start;
        this.goal = goal;
        this.grid = grid;

        visited = new ArrayList<>();
        unvisited = new HashSet<>();
        fromWho = new HashMap<>();
        lengthFromStart = new HashMap<>();
        lengthToGoal = new HashMap<>();
        initiateAllSets();
    }

    private void initiateAllSets() {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Point cell = new Point(x, y);
                if (grid.getCell(cell).getTileState() != TileState.WALL) {
                    initiateCellInSet(cell);
                }
            }
        }
        lengthFromStart.put(start, 0);
    }

    protected void initiateCellInSet(Point cell) {
        unvisited.add(cell);
        lengthFromStart.put(cell, 100000000);
    }

    @Override
    public List<Point> findPath() {
        Point currentPoint = new Point(0, 0);
        int i = 0;
        int limit = 10000;
        while (!visited.contains(goal) && i < limit) {
            currentPoint = findNextCell();
            visited.add(currentPoint);
            unvisited.remove(currentPoint);

            for (Neighbours neighbour: Neighbours.ALL_VALUES()) {
                Point neighbourCell = new Point(currentPoint.x + neighbour.x, currentPoint.y + neighbour.y);
                if (grid.inBounds(neighbourCell) && unvisited.contains(neighbourCell)) {

                    int length = lengthFromStart.get(currentPoint) + neighbour.cost;

                    // If length to cell is lower than previous, then update its length
                    if (length < lengthFromStart.get(neighbourCell)) {
                        lengthFromStart.put(neighbourCell, length);
                        fromWho.put(neighbourCell, currentPoint);
                    }
                }
            }
            i++;
        }

        return extractPath(currentPoint, i < limit);
    }

    private List<Point> extractPath(Point currentPoint, boolean limitReached) {
        List<Point> path = new ArrayList<>();
        path.add(currentPoint);
        while (!path.contains(start) && limitReached) {
            currentPoint = fromWho.get(currentPoint);
            path.add(currentPoint);
        }
        return path;
    }

    protected Point findNextCell() {

        Point nextPoint = new Point(0, 0);
        int distance = 100000000;

        for (Point unvisitedCell: unvisited) {
            if (lengthFromStart.get(unvisitedCell) < distance) {
                distance = lengthFromStart.get(unvisitedCell);
                nextPoint = unvisitedCell;
            }
        }
        return nextPoint;
    }

    @Override
    public List<Point> getSearchPath() {
        return visited;
    }
}
