package controller;

import maze.MazeMode;
import model.PathMazeModel;
import view.Viewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener, ActionListener {

    PathMazeModel model;
    Viewer viewer;

    public Controller(PathMazeModel model, Viewer viewer) {
        this.model = model;
        this.viewer = viewer;
        viewer.addKeyListener(this);

        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        char key = e.getKeyChar();

        if (key == KeyEvent.VK_ENTER) {
            model.generateMaze();
        } else if (key == KeyEvent.VK_SPACE) {
            model.fastMazeGeneration();
        } else if (key == KeyEvent.VK_1) {
            model.setMazeMode(MazeMode.DFS);
        } else if (key == KeyEvent.VK_2) {
            model.setMazeMode(MazeMode.KRUSKAL);
        } else if (key == KeyEvent.VK_3) {
            model.setMazeMode(MazeMode.PRIM);
        } else if (key == KeyEvent.VK_4) {
            model.setMazeMode(MazeMode.DIVISION);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.update();
        viewer.repaint();
//        System.out.println("updated");
    }
}
