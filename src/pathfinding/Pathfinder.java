package pathfinding;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Pathfinder{

    List<Point> path = new ArrayList<>();

    public List<Point> findPath() {
        return path;
    }

    public List<Point> getSearchPath() {
        return null;
    }
}
