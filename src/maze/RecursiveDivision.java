package maze;

import grid.Grid;
import grid.Tile;
import grid.TileState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RecursiveDivision extends MazeGenerator{

    private int minSize = 2;
    List<Integer> horizontalWalls = new ArrayList<>();
    List<Integer> verticalWalls = new ArrayList<>();


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

    private List<Integer> getMatchingWalls(int start, int end, List<Integer> potentialWalls) {
        List<Integer> match = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (verticalWalls.contains(i)) {
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

            List<Point> linePoints = new ArrayList<>();

            int line;
            if (chamberWidth > chamberHeight) {
                // Vertical line
                line = availableVerticalWalls.get(random.nextInt(availableVerticalWalls.size()));
                List<Point> notInVertical = new ArrayList<>();
                for (int i = heightStart; i < heightEnd; i++) {
                    Point cell = new Point(line, i);
                    linePoints.add(cell);
                    if (!availableHorizontalWalls.contains(cell.y)) {
                        notInVertical.add(cell);
                    }
                }
                linePoints.remove(notInVertical.get(random.nextInt(notInVertical.size())));
                path.addAll(linePoints);

                subChamberA.addAll(List.of(widthStart, line, heightStart, heightEnd));
                subChamberB.addAll(List.of(line + 1, widthEnd, heightStart, heightEnd));

            } else {
                // Horizontal
                line = availableHorizontalWalls.get(random.nextInt(availableHorizontalWalls.size()));
                List<Point> notInHorizontal = new ArrayList<>();
                for (int i = widthStart; i < widthEnd; i++) {
                    Point cell = new Point(i, line);
                    linePoints.add(cell);
                    if (!availableVerticalWalls.contains(cell.x)) {
                        notInHorizontal.add(cell);
                    }
                }
                linePoints.remove(notInHorizontal.get(random.nextInt(notInHorizontal.size())));


//                linePoints.remove(linePoints.get(random.nextInt(linePoints.size())));
                path.addAll(linePoints);

                subChamberA.addAll(List.of(widthStart, widthEnd, heightStart, line));
                subChamberB.addAll(List.of(widthStart, widthEnd, line + 1, heightEnd));
            }


            int areaA = calcChamberArea(subChamberA);
            int areaB = calcChamberArea(subChamberB);

            if (areaA < areaB) {
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
}
