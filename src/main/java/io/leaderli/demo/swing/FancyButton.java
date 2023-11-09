package io.leaderli.demo.swing;

import javax.swing.*;

import java.awt.*;

import static io.leaderli.demo.swing.SwingUtil.image;
import static io.leaderli.demo.swing.SwingUtil.newJFrame;

public class FancyButton extends JButton {
    public FancyButton(Icon icon, Icon pressed, Icon rollover) {
        super(icon);
        setFocusPainted(false);
        setRolloverEnabled(true);
        setRolloverIcon(rollover);
        setPressedIcon(pressed);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    public static void main(String[] args) {

        FancyButton b1 = new FancyButton(image("redcube"), image("redpaw"), image("reddiamond"));
        FancyButton b2 = new FancyButton(image("bluecube"), image("bluepaw"), image("bluediamond"));
        JFrame frame = newJFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(b1);
        contentPane.add(b2);
        frame.setVisible(true);
    }
}
