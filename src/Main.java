import controller.Controller;
import model.PathMazeModel;
import view.Viewer;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        PathMazeModel model = new PathMazeModel();
        Viewer viewer = new Viewer(model);
        Controller controller = new Controller(model, viewer);

        JFrame frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Pathfinder");
        frame.setVisible(true);

        frame.add(viewer);


    }
}
