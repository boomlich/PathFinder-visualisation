package view;

import grid.Grid;
import grid.Tile;
import grid.TileState;
import model.Renderable;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Viewer extends JComponent {

    {
        this.setFocusable(true);
    }

    private final Renderable<Tile> model;

    public Viewer(Renderable<Tile> model) {
        this.model = model;

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(500, 600));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        drawGrid(g2D, 20, 20, model.getRenderWidth(), model.getRenderHeight());
    }

    private void drawGrid(Graphics2D g2D, double offsetX, double offsetY, double width, double height) {
        Grid<Tile> grid = model.getGrid();
        int gridWidth = grid.getWidth();
        int gridHeight = grid.getHeight();

        double tileWidth = width / gridWidth;
        double tileHeight = height / gridHeight;

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                double x = offsetX + tileWidth * j;
                double y = offsetY + tileHeight * i;
                drawTile(g2D, x, y, tileWidth, tileHeight, grid.getCell(new Point(j, i)));
            }
        }
    }

    private void drawTile(Graphics2D g2D, double x, double y, double width, double height, Tile tile) {
        g2D.setColor(Color.green);
        Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
        if (tile.getTileState() == TileState.NORMAL) {
            g2D.setColor(Color.lightGray);
        } else if (tile.getTileState() == TileState.WALL) {
            g2D.setColor(Color.DARK_GRAY);
        } else if (tile.getTileState() == TileState.START) {
            g2D.setColor(Color.ORANGE);
        } else if (tile.getTileState() == TileState.CURRENT) {
            g2D.setColor(Color.RED);
        } else if (tile.getTileState() == TileState.SEARCH) {
            g2D.setColor(Color.CYAN);
        } else if (tile.getTileState() == TileState.VISITED) {
            g2D.setColor(Color.GREEN.darker().darker());
        } else if (tile.getTileState() == TileState.PATH) {
            g2D.setColor(Color.ORANGE);
        } else if (tile.getTileState() == TileState.GOAL) {
            g2D.setColor(Color.RED);
        }
        g2D.fill(rect);

        g2D.setColor(Color.BLACK);
        g2D.draw(rect);
    }
}
