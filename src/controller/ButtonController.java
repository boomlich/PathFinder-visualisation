package controller;

import maze.MazeMode;
import model.PathMazeModel;
import pathfinding.PathFindMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ButtonController implements ActionListener {

    PathMazeModel model;

    public ButtonController(PathMazeModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        JComboBox<?> dropDown = null;
        if (e.getSource() instanceof JComboBox<?>) {
            dropDown = (JComboBox<?>) e.getSource();
        }

        if (dropDown != null) {
            command = (String) dropDown.getSelectedItem();
            switch (Objects.requireNonNull(command)) {
                case "10x10" -> model.setGridResolution(10, 10);
                case "20x10" -> model.setGridResolution(20, 10);
                case "25x25" -> model.setGridResolution(25, 25);
                case "50x25" -> model.setGridResolution(50, 25);
                case "50x50" -> model.setGridResolution(50, 50);
                case "100x50" -> model.setGridResolution(100, 50);
            }
        }

        if (command.equals("PATH_DIJKSTRA")) {
            model.findPath(PathFindMode.DIJKSTRA);
        } else if (command.equals("PATH_ASTAR")) {
            model.findPath(PathFindMode.ASTAR);
        }  else if (command.equals("MAZE_DFS")) {
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
            model.setSpeed(10);
            model.fastMazeGeneration();
            model.fastPath();
        } else if (command.equals("RESET_GRID")) {
            model.resetGrid();
        } else if (command.equals("RESET_PATH")) {
            model.clearPath();
        }
    }
}
