package pathfinding;

import java.util.Arrays;

public enum Neighbours {

    NORTH(0, -1, 1),
    NORTHEAST(1, -1, 1.41),
    EAST(1, 0, 1),
    SOUTHEAST(1, 1, 1.41),
    SOUTH(0, 1, 1),
    SOUTHWEST(-1, 1, 1.41),
    WEST(-1, 0, 1),
    NORTHWEST(-1, -1, 1.41);

    public final int x, y;
    public final double cost;

    Neighbours(int x, int y, double cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public static java.util.List<Neighbours> ALL_VALUES() {
        return Arrays.asList(Neighbours.values());
    }
}
