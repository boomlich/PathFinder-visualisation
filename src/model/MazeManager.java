package model;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import maze.MazeGenerator;
import maze.MazeMode;
import maze.algoritms.RandomDFS;
import maze.algoritms.RandomKruskal;
import maze.algoritms.RandomPrim;
import maze.algoritms.RecursiveDivision;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazeManager {

    private boolean inverted;
    private List<Point> mazePath = new ArrayList<>();
    Point currentTile;

    public void generateMaze(Grid<Tile> grid, MazeMode mazeMode) {

        // Select maze-generation algorithm
        MazeGenerator mazeGenerator = null;
        inverted = true;
        if (mazeMode == MazeMode.DFS) {
            mazeGenerator = new RandomDFS(grid);
        } else if (mazeMode == MazeMode.KRUSKAL) {
            mazeGenerator = new RandomKruskal(grid);
        } else if (mazeMode == MazeMode.PRIM) {
            mazeGenerator = new RandomPrim(grid);
        } else if (mazeMode == MazeMode.DIVISION) {
            mazeGenerator = new RecursiveDivision(grid);
            inverted = false;
        }

        assert mazeGenerator != null;
        mazePath = mazeGenerator.generateMaze();
        currentTile = mazePath.remove(0);
    }

    private void mazeGenerationStep(Grid<Tile> grid) {
        changeCellState(grid, currentTile, inverted);
        currentTile = mazePath.remove(0);
        grid.setCell(currentTile, new Tile(TileState.CURRENT));

        if (mazePath.isEmpty()) {
            changeCellState(grid, currentTile, inverted);
        } else {
            grid.setCell(currentTile, new Tile(TileState.CURRENT));
        }
    }

    private void changeCellState(Grid<Tile> grid, Point cell, boolean inverted) {
        if (inverted) {
            grid.setCell(currentTile, new Tile(TileState.NORMAL));
        } else {
            grid.setCell(currentTile, new Tile(TileState.WALL));
        }
    }

    /**
     * Generate maze instantly
     *
     * @param grid
     */
    public void fastMazeGeneration(Grid<Tile> grid) {
        while (notCompletedMaze()) {
            mazeGenerationStep(grid);
        }
    }

    /**
     * @param steps number of cells to display per update
     * @return false if nothing more to update (maze finished)
     */
    public boolean update(int steps, Grid<Tile> grid) {
        for (int i = 0; i < steps; i++) {
            if (notCompletedMaze()) {
                mazeGenerationStep(grid);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean notCompletedMaze() {
        return !mazePath.isEmpty();
    }

}
