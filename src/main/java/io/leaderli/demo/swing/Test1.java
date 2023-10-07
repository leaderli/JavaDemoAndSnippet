package io.leaderli.demo.swing;

import javax.swing.*;

public class Test1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        JButton button = new JButton("press");
        button.setToolTipText("fuck");
        frame.add(button, 0);
        button.putClientProperty("a", 1);
        System.out.println(button.getClientProperty("a"));
        button.getActionMap().put("download", new SampleAction("download", null));
        button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F8"),"download");
    }
}
