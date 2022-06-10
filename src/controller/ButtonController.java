package controller;

import maze.MazeMode;
import model.PathMazeModel;
import pathfinding.PathFindMode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

public class ButtonController implements ActionListener {

    PathMazeModel model;

    public ButtonController(PathMazeModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command == "PATH_DIJKSTRA") {
            model.findPath(PathFindMode.DIJKSTRA);

        } else if (command.equals("PATH_ASTAR")) {
            System.out.println("ASTAR");
            model.findPath(PathFindMode.ASTAR);
        } else if (command.equals("PATH_BFS")) {

        } else if (command.equals("MAZE_DFS")) {
            model.generateMaze(MazeMode.DFS);
        } else if (command.equals("MAZE_KRUSKAL")) {
            model.generateMaze(MazeMode.KRUSKAL);
        } else if (command.equals("MAZE_PRIM")) {
            model.generateMaze(MazeMode.PRIM);
        } else if (command.equals("MAZE_DIV")) {
            model.generateMaze(MazeMode.DIVISION);
        } else if (command.equals("SPEED_1x")) {
            model.setSpeed(1);
        } else if (command.equals("SPEED_2x")) {
            model.setSpeed(2);
        } else if (command.equals("SPEED_5x")) {
            System.out.println("speed 5");
            model.setSpeed(5);
        } else if (command.equals("SPEED_INSTANT")) {
            System.out.println("instatn");
            model.setSpeed(10);
            model.fastMazeGeneration();
            model.fastPath();
        }
    }
}
