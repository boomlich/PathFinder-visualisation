import controller.Controller;
import model.PathMazeModel;
import view.UI;
import view.Viewer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        PathMazeModel model = new PathMazeModel();
        Viewer viewer = new Viewer(model);
        UI ui = new UI();
        Controller controller = new Controller(model, viewer, ui);


        JFrame frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Pathfinder");
        frame.setVisible(true);

        frame.add(ui, BorderLayout.NORTH);
        frame.add(viewer, BorderLayout.SOUTH);

    }
}
