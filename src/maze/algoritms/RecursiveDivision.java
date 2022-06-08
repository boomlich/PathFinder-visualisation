package maze.algoritms;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.MazeGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecursiveDivision extends MazeGenerator {

    private List<Integer> horizontalWalls = new ArrayList<>();
    private List<Integer> verticalWalls = new ArrayList<>();


    public RecursiveDivision(Grid<Tile> grid, Point startPoint) {
        super(grid, false);

        for (int i = 1; i < grid.getWidth(); i+=2) {
            verticalWalls.add(i);
        }
        for (int i = 1; i < grid.getHeight(); i+= 2) {
            horizontalWalls.add(i);
        }

        grid.fill(new Tile(TileState.NORMAL));
    }

    @Override
    public List<Point> generateMaze() {
        createChamber(0, grid.getWidth(), 0, grid.getHeight());
        return path;
    }

    /**
     *
     * @param start start coordinate of the chamber
     * @param end end value of the width/height of the chamber
     * @param potentialWalls all available vertical/horizontal wall's in the grid
     * @return the vertical/horizontal walls inside the chamber
     */
    private List<Integer> getMatchingWalls(int start, int end, List<Integer> potentialWalls) {
        List<Integer> match = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (potentialWalls.contains(i)) {
                match.add(i);
            }
        }
        return match;
    }

    private void createChamber(int widthStart, int widthEnd, int heightStart, int heightEnd) {

        int chamberWidth = widthEnd - widthStart;
        int chamberHeight = heightEnd - heightStart;

        if (chamberWidth > 2 && chamberHeight > 2) {

            List<Integer> subChamberA = new ArrayList<>();
            List<Integer> subChamberB = new ArrayList<>();

            List<Integer> availableVerticalWalls = getMatchingWalls(widthStart, widthEnd, verticalWalls);
            List<Integer> availableHorizontalWalls = getMatchingWalls(heightStart, heightEnd, horizontalWalls);

            int line;

            // Determine vertical or horizontal division
            if (chamberWidth > chamberHeight) {
                line = createSubChambers(heightStart, heightEnd, true, availableVerticalWalls, availableHorizontalWalls);

                subChamberA.addAll(List.of(widthStart, line, heightStart, heightEnd));
                subChamberB.addAll(List.of(line + 1, widthEnd, heightStart, heightEnd));

            } else {
                line = createSubChambers(widthStart, widthEnd, false, availableHorizontalWalls, availableVerticalWalls);

                subChamberA.addAll(List.of(widthStart, widthEnd, heightStart, line));
                subChamberB.addAll(List.of(widthStart, widthEnd, line + 1, heightEnd));
            }

            // Recursively divide sub-chambers
            if (calcChamberArea(subChamberA) < calcChamberArea(subChamberB)) {
                createChamber(subChamberA.get(0), subChamberA.get(1), subChamberA.get(2), subChamberA.get(3));
                createChamber(subChamberB.get(0), subChamberB.get(1), subChamberB.get(2), subChamberB.get(3));
            } else {
                createChamber(subChamberB.get(0), subChamberB.get(1), subChamberB.get(2), subChamberB.get(3));
                createChamber(subChamberA.get(0), subChamberA.get(1), subChamberA.get(2), subChamberA.get(3));
            }
        }
    }

    private int calcChamberArea(List<Integer> chamber) {
        return (chamber.get(1) - chamber.get(0)) * (chamber.get(3) - chamber.get(2));
    }

    /**
     *
     * Create a random line along the vertical or horizontal axis inside the bounds of the chamber.
     *
     * @param mainStart start of the width/height of the primary container
     * @param mainEnd start of the width/height of the primary container
     * @param vertical true if subchamber is to be divided with a vertical line
     * @param mainWalls list of all x or y values of the vertical/horizontal wall-cells
     * @param otherWalls list of all x or y values of the horizontal/vertical wall-cells
     * @return x- or y-value coordinate of the division line
     */
    private int createSubChambers(int mainStart, int mainEnd, boolean vertical, List<Integer> mainWalls, List<Integer> otherWalls) {

        int line = mainWalls.get(random.nextInt(mainWalls.size()));
        List<Point> lineCells = new ArrayList<>();
        List<Point> notInOtherWalls = new ArrayList<>();

        int wallIndex;
        Point cell = new Point(0, 0);

        for (int i = mainStart; i < mainEnd; i++) {
            if (vertical) {
                cell = new Point(line, i);
                wallIndex = cell.y;
            } else {
                cell = new Point(i, line);
                wallIndex = cell.x;
            }
            lineCells.add(cell);
            if (!otherWalls.contains(wallIndex)) {
                notInOtherWalls.add(cell);
            }
        }
        lineCells.remove(notInOtherWalls.get(random.nextInt(notInOtherWalls.size())));
        path.addAll(lineCells);

        return line;
    }
}
