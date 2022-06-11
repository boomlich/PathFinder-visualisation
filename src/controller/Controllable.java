package controller;

import maze.MazeMode;
import model.InteractMode;
import pathfinding.PathFindMode;

public interface Controllable {


    void generateMaze(MazeMode mazeMode);

    void resetGrid();

    void fastMazeGeneration();

    void update();

    void clearPath();

    void findPath(PathFindMode pathMode);

    void fastPath();

    void clickCell(int x, int y);

    void mouseReleased();

    void setInteractMode(InteractMode mode);

    InteractMode getInteractMode();

    void setSpeed(int speed);

    void setGridResolution(int width, int height);

}
