package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class JToggleButtonExample {

    public static void main(String[] args) {
        JFrame frame = SwingUtil.newJFrame();
        JToggleButton jToggleButton = new JToggleButton("press");
        jToggleButton.addItemListener(e -> System.out.println(jToggleButton.getModel().isSelected()));

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(jToggleButton);
        frame.setVisible(true);
    }
}
