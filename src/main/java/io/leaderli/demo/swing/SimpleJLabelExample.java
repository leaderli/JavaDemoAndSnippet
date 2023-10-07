package io.leaderli.demo.swing;

import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class SimpleJLabelExample {
    public static void main(String[] args) {
        JLabel label = new JLabel("A Very simple Text Label");

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
