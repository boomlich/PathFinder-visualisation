package maze;

import java.util.Arrays;
import java.util.List;

public enum NeighburCells {
    NORTH(0, -2),
    EAST(2, 0),
    SOUTH(0, 2),
    WEST(-2, 0);

    public static List<NeighburCells> ALL_VALUES() {
        return Arrays.asList(NeighburCells.values());
    }

    public final int x, y;

    NeighburCells(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
