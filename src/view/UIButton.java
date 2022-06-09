package view;

import javax.swing.*;

public class UIButton extends JButton {

    public UIButton(String text, String command) {
        setText(text);
        setFocusable(false);
        setActionCommand(command);
    }
}
