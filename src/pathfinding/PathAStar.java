package pathfinding;

import grid.Grid;
import grid.Tile;
import java.awt.*;

public class PathAStar extends PathDijkstra {

    public PathAStar(Point start, Point goal, Grid<Tile> grid) {
        super(start, goal, grid);
    }

    protected void initiateCellInSet(Point cell) {
        super.initiateCellInSet(cell);
        lengthToGoal.put(cell, getDistanceTo(cell, goal));
    }

    @Override
    protected Point findNextCell() {
        Point nextPoint = new Point(0, 0);
        int lowestFCost = 100000000;
        int lowestHCost = 100000000;

        // Find the cell with the lowest F-cost and H-cost
        for (Point unvisitedCell: unvisited) {
            int cellFCost = lengthFromStart.get(unvisitedCell) + lengthToGoal.get(unvisitedCell);
            if (cellFCost == lowestFCost) {
                if (lengthToGoal.get(unvisitedCell) < lowestHCost) {
                    lowestHCost = lengthToGoal.get(unvisitedCell);
                    nextPoint = unvisitedCell;
                }
            } else if (cellFCost < lowestFCost){
                lowestFCost = cellFCost;
                lowestHCost = lengthToGoal.get(unvisitedCell);
                nextPoint = unvisitedCell;
            }
        }
        return nextPoint;
    }

    /**
     * @param start
     * @param end
     * @return the total distance from start-point to the end-point
     */
    private int getDistanceTo(Point start, Point end) {

        int absX = Math.abs(start.x - end.x);
        int absY = Math.abs(start.y - end.y);

        int linear = Math.abs(absX - absY);
        int diagonal = Math.min(absX, absY);

        return linear * 10 + diagonal * 14;
    }
}
