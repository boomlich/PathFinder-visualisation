package model;

import grid.Grid;

import java.util.List;

public interface Renderable<E> {

    Grid<E> getGrid();

    int getRenderWidth();

    int getRenderHeight();
}
