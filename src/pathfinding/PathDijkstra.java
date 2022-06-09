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

    private List<Point> visited;
    private HashSet<Point> unvisited;

    private HashMap<Point, Double> lengthTo;
    private HashMap<Point, Point> fromWho;
    private Point start;
    private Point goal;
    private Grid<Tile> grid;

    public PathDijkstra(Point start, Point goal, Grid<Tile> grid) {


        visited = new ArrayList<>();
        unvisited = new HashSet<>();
        fromWho = new HashMap<>();
        lengthTo = new HashMap<>();

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Point cell = new Point(x, y);
                if (grid.getCell(cell).getTileState() != TileState.WALL) {
                    unvisited.add(cell);
                    lengthTo.put(cell, 1000000000000000.0);
                }
            }
        }
        lengthTo.put(start, 0.0);
        this.start = start;
        this.goal = goal;
        this.grid = grid;

    }

    @Override
    public List<Point> findPath() {

        Point currentPoint = new Point(0, 0);
        int i = 0;
        while (!visited.contains(goal) && i < 10000) {
            currentPoint = findClosestNextPoint();
            visited.add(currentPoint);
            unvisited.remove(currentPoint);

            for (Neighbours neighbour: Neighbours.ALL_VALUES()) {
                Point neighbourCell = new Point(currentPoint.x + neighbour.x, currentPoint.y + neighbour.y);
                if (grid.inBounds(neighbourCell) && !visited.contains(neighbourCell)) {

                    double length = lengthTo.get(currentPoint) + neighbour.cost;

                    // Check if cell already has a length. If shorter length then replace
                    if (fromWho.containsKey(neighbourCell)) {
                        if (length < lengthTo.get(neighbourCell)) {
                            lengthTo.put(neighbourCell, length);
                        }
                    } else {
                        lengthTo.put(neighbourCell, lengthTo.get(currentPoint) + neighbour.cost);
                        fromWho.put(neighbourCell, currentPoint);
                    }
                }
            }
            i++;
            System.out.println(i);
        }

        path.add(currentPoint);
        while (!path.contains(start)) {
            currentPoint = fromWho.get(currentPoint);
            path.add(currentPoint);
        }

        return path;
    }

    private Point findClosestNextPoint() {

        Point nextPoint = new Point(0, 0);
        double distance = 10000000000000.0;

        for (Point unvisitedCell: unvisited) {
            if (lengthTo.get(unvisitedCell) < distance) {
                distance = lengthTo.get(unvisitedCell);
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
