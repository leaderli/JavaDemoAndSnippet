package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class RootExample {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JRootPane root = f.getRootPane();
        Container content = root.getContentPane();
        content.add(new JButton("Hello"));

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        bar.add(menu);
        menu.add("Open");
        menu.add("Close");
        root.setJMenuBar(bar);
        f.pack();
        f.setLocation(900,450);
        f.setVisible(true);
    }
}
