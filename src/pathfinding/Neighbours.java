package pathfinding;

import java.util.Arrays;

public enum Neighbours {

    NORTH(0, -1, 10),
    NORTHEAST(1, -1, 14),
    EAST(1, 0, 10),
    SOUTHEAST(1, 1, 14),
    SOUTH(0, 1, 10),
    SOUTHWEST(-1, 1, 14),
    WEST(-1, 0, 10),
    NORTHWEST(-1, -1, 14);

    public final int x, y, cost;

    Neighbours(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public static java.util.List<Neighbours> ALL_VALUES() {
        return Arrays.asList(Neighbours.values());
    }
}
