package io.leaderli.demo.swing;

import javax.swing.*;

public class JButtonEvents {
    public static void main(String[] args) {
        JButton jb = new JButton("Press Me");
        jb.addActionListener(e -> System.out.println("action:" + e));
        jb.addItemListener(e -> System.out.println("item:" + e));
        jb.addChangeListener(e -> System.out.println("change:" + e));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(jb);
        frame.setLocation(900, 400);
        frame.setVisible(true);
    }
}
