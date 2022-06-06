package controller;

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

        model.generateMaze();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.update();
        viewer.repaint();
    }
}
