package grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grid<E> implements IGrid<E>, Iterable<E> {

    private final List<E> grid;
    private final int width, height;

    public Grid(int width, int height, E value) {
        if (width < 1 && height < 1) {
            throw new IllegalArgumentException("Grid dimensions must be greater than 1");
        }
        grid = new ArrayList<>();
        this.width = width;
        this.height = height;
        fill(value);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private int cellIndex(Point position) {
        return position.y * getWidth() + position.x;
    }

    @Override
    public void setCell(Point position, E value) {
        grid.set(cellIndex(position), value);
    }

    @Override
    public E getCell(Point position) {
        return grid.get(cellIndex(position));
    }

    @Override
    public boolean inBounds(Point position) {
        return position.x >= 0 && position.x < getWidth() &&
                position.y >= 0 && position.y < getHeight();
    }

    @Override
    public void fill(E value) {
        grid.clear();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                grid.add(value);
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return grid.iterator();
    }
}
