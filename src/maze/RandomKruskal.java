package maze;

import grid.Grid;
import grid.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RandomKruskal extends MazeGenerator{

    private final List<HashSet<Point>> freeTiles;
    private final List<Point> wallTiles;

    public RandomKruskal(Grid<Tile> grid, Point startingPoint) {
        super(grid, true);

        freeTiles = new ArrayList<>();
        wallTiles = new ArrayList<>();

        int i = 0;
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {

                // Store wall and free tiles
                if (y % 2 == 0) {
                    if (x % 2 == 0) {
                        Point freeTile = new Point(x, y);
                        freeTiles.add(new HashSet<>(List.of(freeTile)));
                    } else {
                        Point wallTile = new Point(x, y);
                        wallTiles.add(wallTile);
                    }
                } else {
                    if (x % 2 == 0) {
                        Point wallTile = new Point(x, y);
                        wallTiles.add(wallTile);
                    }
                }
            }
        }
    }

    @Override
    public List<Point> generateMaze() {

        while (freeTiles.size() > 1) {
            Point currentWall = wallTiles.get(random.nextInt(wallTiles.size()));
            List<Point> wallNeighbours = selectCrossNeighbour(currentWall, random.nextBoolean());

            if (wallNeighbours.size() == 2) {
                // Find the sets of the cells the wall divides
                HashSet<Point> setA = identifySet(freeTiles, wallNeighbours.get(0));
                HashSet<Point> setB = identifySet(freeTiles, wallNeighbours.get(1));

                if (!setA.equals(setB)) {
                    // Merge the two disjointed sets and wall
                    setA.addAll(setB);
                    setA.add(currentWall);
                    freeTiles.remove(setB);

                    //
                    for (Point cell: wallNeighbours) {
                        addNewCellToPath(cell);
                    }
                    path.add(currentWall);
                }
            }
        }
        return path;
    }

    private void addNewCellToPath(Point cell) {
        if (!path.contains(cell)) {
            path.add(cell);
        }
    }

    private HashSet<Point> identifySet(List<HashSet<Point>> allSets, Point point) {
        HashSet<Point> identifiedSet = new HashSet<>();
        for (HashSet<Point> set: allSets) {
            if (set.contains(point)) {
                return set;
            }
        }
        return identifiedSet;
    }
}
