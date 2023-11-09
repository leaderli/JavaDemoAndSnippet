package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class ButtonExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton ok = new JButton("OK");
        ok.addActionListener(System.out::println);
        ok.addItemListener(System.out::println);
        ok.addChangeListener(System.out::println);
        JButton cancel = new JButton("Cancel");

        JPanel buttons = new JPanel();
        buttons.add(ok);
        buttons.add(cancel);
        JLabel msg = new JLabel("Is this ok?", JLabel.CENTER);
        JRootPane rootPane = frame.getRootPane();
        rootPane.setDefaultButton(ok);

        Container contentPane = frame.getContentPane();
        contentPane.add(msg, BorderLayout.CENTER);
        contentPane.add(buttons, BorderLayout.SOUTH);


        frame.setLocation(900, 450);
        frame.setSize(210, 210);
        frame.setVisible(true);
    }
}
