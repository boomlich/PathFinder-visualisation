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
            model.setPathMode(PathFindMode.DIJKSTRA);
            model.findPath();

        } else if (command.equals("PATH_ASTAR")) {

        } else if (command.equals("PATH_BFS")) {

        } else if (command.equals("MAZE_DFS")) {
            model.setMazeMode(MazeMode.DFS);
            model.generateMaze();
        } else if (command.equals("MAZE_KRUSKAL")) {
            model.setMazeMode(MazeMode.KRUSKAL);
            model.generateMaze();
        } else if (command.equals("MAZE_PRIM")) {
            model.setMazeMode(MazeMode.PRIM);
            model.generateMaze();
        } else if (command.equals("MAZE_DIV")) {
            model.setMazeMode(MazeMode.DIVISION);
            model.generateMaze();
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
