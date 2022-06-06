package grid;

import java.awt.*;

public interface IGrid<E> {

    /**
     * @return total amount of columns in the grid
     */
    int getWidth();

    /**
     * @return total amount of rows in the grid
     */
    int getHeight();

    void setCell(Point position, E value);

    E getCell(Point position);

    boolean inBounds(Point position);


    void fill(E tile);
}
