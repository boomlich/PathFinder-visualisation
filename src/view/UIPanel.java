package view;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel {

    public UIPanel(String title) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel resolutionTitle = new JLabel(title);
        resolutionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(resolutionTitle);
    }
}
