package maze;

import java.util.Arrays;
import java.util.List;

public enum NeighbourCells {
    NORTH(0, -2),
    EAST(2, 0),
    SOUTH(0, 2),
    WEST(-2, 0);

    public static List<NeighbourCells> ALL_VALUES() {
        return Arrays.asList(NeighbourCells.values());
    }

    public final int x, y;

    NeighbourCells(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
