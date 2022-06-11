package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class UI extends JPanel {

    private JPanel pathFinding;
    private JPanel mazeGeneration;
    private JPanel speedOptions;
    private JComboBox<String> resolutionBox;
    private JPanel resetButtons;

    public UI (){

        this.setPreferredSize(new Dimension(100, 75));


        UIPanel pathContainer = new UIPanel("Pathfinding");
        UIPanel mazeContainer = new UIPanel("Maze generation");
        UIPanel speedContainer = new UIPanel("Animation speed");
        UIPanel resolutionPanel = new UIPanel("Grid size");
        UIPanel resetPanel = new UIPanel("Reset");



        pathFinding = new JPanel();
        mazeGeneration = new JPanel();

        pathFinding.setLayout(new FlowLayout());
        pathFinding.setLayout(new FlowLayout());

        pathFinding.add(new UIButton("Dijkstra", "PATH_DIJKSTRA"));
        pathFinding.add(new UIButton("A*", "PATH_ASTAR"));

        mazeGeneration.add(new UIButton("Depth-First", "MAZE_DFS"));
        mazeGeneration.add(new UIButton("Kruskal", "MAZE_KRUSKAL"));
        mazeGeneration.add(new UIButton("Prim", "MAZE_PRIM"));
        mazeGeneration.add(new UIButton("Division", "MAZE_DIV"));



        JRadioButton speedNormal = new JRadioButton("1x");
        speedNormal.setActionCommand("SPEED_1x");
        speedNormal.setSelected(true);
        speedNormal.setFocusable(false);

        JRadioButton speedDouble = new JRadioButton("2x");
        speedDouble.setActionCommand("SPEED_2x");
        speedDouble.setFocusable(false);

        JRadioButton speedFive = new JRadioButton("5x");
        speedFive.setActionCommand("SPEED_5x");
        speedFive.setFocusable(false);

        JRadioButton speedInstant = new JRadioButton("Instant");
        speedInstant.setActionCommand("SPEED_INSTANT");
        speedInstant.setFocusable(false);


        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(speedNormal);
        buttonGroup.add(speedDouble);
        buttonGroup.add(speedFive);
        buttonGroup.add(speedInstant);

        speedOptions = new JPanel();
        speedOptions.add(speedNormal);
        speedOptions.add(speedDouble);
        speedOptions.add(speedFive);
        speedOptions.add(speedInstant);



        String[] resolutionStrings = {"10x10", "20x10", "25x25", "50x25",  "50x50", "100x50"};
        resolutionBox = new JComboBox<>(resolutionStrings);
        resolutionBox.setSelectedIndex(3);


        resetButtons = new JPanel();
        resetButtons.add(new UIButton("Path", "RESET_PATH"));
        resetButtons.add(new UIButton("Grid", "RESET_GRID"));



        pathContainer.add(pathFinding);
        mazeContainer.add(mazeGeneration);
        speedContainer.add(speedOptions);
        resolutionPanel.add(resolutionBox);
        resetPanel.add(resetButtons);

        this.add(resetPanel);
        this.add(pathContainer);
        this.add(mazeContainer);
        this.add(speedContainer);
        this.add(resolutionPanel);

    }

    public void setActionListener(ActionListener controller) {
        addActionListenerToComp(pathFinding, controller);
        addActionListenerToComp(mazeGeneration, controller);
        addActionListenerToComp(speedOptions, controller);
        addActionListenerToComp(resetButtons, controller);
        resolutionBox.addActionListener(controller);
    }

    private void addActionListenerToComp(JPanel jPanel, ActionListener controller) {
        for (Component button: jPanel.getComponents()) {
            if (button instanceof JButton) {
                ((JButton) button).addActionListener(controller);
            } else if (button instanceof JRadioButton) {
                ((JRadioButton) button).addActionListener(controller);
            }
        }
    }
}
