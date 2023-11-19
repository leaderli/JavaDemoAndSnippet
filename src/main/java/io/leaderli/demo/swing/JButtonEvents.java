package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class JButtonEvents {
    public static void main(String[] args) {
        JButton jb = new JButton("Press Me");
        jb.addActionListener(e -> System.out.println("action:" + e));
        jb.addItemListener(e -> System.out.println("item:" + e));
        jb.addChangeListener(e -> System.out.println("change:" + e));
        JFrame frame = new JFrame() {
            @Override
            protected void processWindowEvent(WindowEvent e) {
                if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                    int exit = JOptionPane.showConfirmDialog(this, "Are you sure?");

                    if (exit == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    } else {
                        return;
                    }


                }
                super.processWindowEvent(e);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(jb);
        frame.setLocation(900, 400);
        frame.setVisible(true);
    }
}
