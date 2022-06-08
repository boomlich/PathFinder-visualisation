package maze.algoritms;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.MazeGenerator;
import maze.NeighburCells;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDFS extends MazeGenerator {

    private final List<Point> cellStack = new ArrayList<>();
    private Point currentCell;
    private Point chosenCell;

    public RandomDFS(Grid<Tile> grid, Point startPoint) {
        super(grid, true);
        visitCell(startPoint);
        generateMaze();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public List<Point> generateMaze() {

        while (!cellStack.isEmpty()) {
            currentCell = cellStack.remove(cellStack.size() - 1);
            List<Point> unvisitedNeighbours = getUnvisitedNeighbours(currentCell);
            if (!unvisitedNeighbours.isEmpty()) {
                cellStack.add(currentCell);
                chosenCell = selectRandomNeighbour(unvisitedNeighbours);

                Point wallCell = new Point((currentCell.x + chosenCell.x) / 2, (currentCell.y + chosenCell.y) / 2);
                path.add(wallCell);

                visitCell(chosenCell);
            }
        }
        return path;
    }

    private void visitCell(Point cell) {
        visited.add(cell);
        cellStack.add(cell);
        path.add(cell);
    }

    private List<Point> getUnvisitedNeighbours(Point cell) {
        List<Point> unvisitedNeighbours = new ArrayList<>();
        for (NeighburCells neighbourOffset: NeighburCells.ALL_VALUES()) {
            Point neighbour = new Point(cell.x + neighbourOffset.x, cell.y + neighbourOffset.y);
            if (grid.inBounds(neighbour)) {
                if (!visited.contains(neighbour)) {
                    unvisitedNeighbours.add(neighbour);
                }
            }
        }
        return unvisitedNeighbours;
    }

    private Point selectRandomNeighbour(List<Point> neighbours) {
        Random random = new Random();
        return neighbours.get(random.nextInt(neighbours.size()));
    }
}
