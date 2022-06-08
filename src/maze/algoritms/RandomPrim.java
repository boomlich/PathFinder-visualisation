package maze.algoritms;

import grid.Grid;
import grid.Tile;
import maze.MazeGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomPrim extends MazeGenerator {

    List<Point> freeTiles = new ArrayList<>();
    List<Point> wallTiles = new ArrayList<>();


    public RandomPrim(Grid<Tile> grid, Point startingPoint) {
        super(grid, true);

        for (int y = 0; y < grid.getHeight(); y += 2) {
            for (int x = 0; x < grid.getWidth(); x += 2) {
                freeTiles.add(new Point(x, y));
            }
        }
    }

    private void markVisited(Point cell) {
        visited.add(cell);
        path.add(cell);
    }

    @Override
    public List<Point> generateMaze() {

        Point startCell = freeTiles.get(random.nextInt(freeTiles.size()));
        markVisited(startCell);
        wallTiles.addAll(identifyCellWalls(startCell));

        while (!wallTiles.isEmpty()) {
            Point currentWall = wallTiles.get(random.nextInt(wallTiles.size()));
            List<Point> neighbours = selectCrossNeighbour(currentWall, currentWall.y % 2 == 0);

            if (neighbours.size() == 2) {
                boolean aVisited = visited.contains(neighbours.get(0));
                boolean bVisited = visited.contains(neighbours.get(1));


                if (aVisited && !bVisited) {
                    visitCell(currentWall, neighbours.get(1));
                } else if (!aVisited && bVisited) {
                    visitCell(currentWall, neighbours.get(0));
                } else if (aVisited) {
                    wallTiles.remove(currentWall);
                }
            } else {
                wallTiles.remove(currentWall);
            }
        }
        return path;
    }

    private void visitCell(Point wall, Point neighbour) {
        visited.add(neighbour);
        path.add(wall);
        path.add(neighbour);

        wallTiles.addAll(identifyCellWalls(neighbour));
        wallTiles.remove(wall);
    }

    private List<Point> identifyCellWalls(Point cell) {
        List<Point> directions = new ArrayList<>(Arrays.asList(
                new Point(cell.x + 1, cell.y),
                new Point(cell.x - 1, cell.y),
                new Point(cell.x, cell.y + 1),
                new Point(cell.x, cell.y - 1)
        ));
        List<Point> walls = new ArrayList<>();
        for (Point direction: directions) {
            if (grid.inBounds(direction)) {
                if (!path.contains(direction)) {
                    walls.add(direction);
                }
            }
        }
        return walls;
    }
}
