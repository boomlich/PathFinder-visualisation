package controller;

import model.InteractMode;
import model.PathMazeModel;
import view.Viewer;

import javax.swing.text.View;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {

    private int prevX, prevY;
    private final PathMazeModel model;
    private final Viewer viewer;
    private InteractMode prevInteractMode;

    public MouseController(PathMazeModel model, Viewer viewer) {
        this.model = model;
        this.viewer = viewer;
        prevX = -1;
        prevY = -1;
    }

    private int calculateTileSize(int renderSize, int gridSize) {
        return 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {



    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevInteractMode = model.getInteractMode();
            model.setInteractMode(InteractMode.NORMAL);
        }
        interactWithGrid(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        prevX = -1;
        prevY = -1;

        if (e.getButton() == MouseEvent.BUTTON3) {
            model.setInteractMode(prevInteractMode);
        }
        model.mouseReleased();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        interactWithGrid(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private void interactWithGrid(Point mouse) {
        int indexX = (mouse.x - viewer.getGridOffsetX()) / (model.getRenderWidth() / model.getGrid().getWidth());
        int indexY = (mouse.y - 20) / (model.getRenderHeight() / model.getGrid().getHeight());

        if (indexX != prevX || indexY != prevY) {
            model.clickCell(indexX, indexY);
            prevX = indexX;
            prevY = indexY;
        }
    }
}
