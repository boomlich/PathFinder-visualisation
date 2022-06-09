package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class UI extends JPanel {

    JPanel pathFinding;
    JPanel mazeGeneration;
    JPanel speedOptions;

    public UI (){


        this.setBackground(Color.GREEN);
        this.setPreferredSize(new Dimension(200, 100));

        JPanel pathContainer = new JPanel();
        pathContainer.setLayout(new BoxLayout(pathContainer, BoxLayout.Y_AXIS));
        JLabel pathTitle = new JLabel("Pathfinding algorithms");
        pathTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pathContainer.add(pathTitle);

        JPanel mazeContainer = new JPanel();
        mazeContainer.setLayout(new BoxLayout(mazeContainer, BoxLayout.Y_AXIS));
        JLabel mazeTitle = new JLabel("Maze generation algorithms");
        mazeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mazeContainer.add(mazeTitle);

        JPanel speedContainer = new JPanel();
        speedContainer.setLayout(new BoxLayout(speedContainer, BoxLayout.Y_AXIS));
        JLabel speedTitle = new JLabel("Animation speed");
        speedTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        speedContainer.add(speedTitle);


        pathFinding = new JPanel();
        mazeGeneration = new JPanel();

        pathFinding.setLayout(new FlowLayout());
        pathFinding.setLayout(new FlowLayout());

        pathFinding.add(new UIButton("Dijkstra", "PATH_DIJKSTRA"));
        pathFinding.add(new UIButton("A*", "PATH_ASTAR"));
        pathFinding.add(new UIButton("Breadth-First", "PATH_BFS"));

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


        pathContainer.add(pathFinding);
        mazeContainer.add(mazeGeneration);
        speedContainer.add(speedOptions);


        this.add(pathContainer);
        this.add(mazeContainer);
        this.add(speedContainer);
    }

    public void setActionListener(ActionListener controller) {
        addActionListenerToComp(pathFinding, controller);
        addActionListenerToComp(mazeGeneration, controller);
        addActionListenerToComp(speedOptions, controller);
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
