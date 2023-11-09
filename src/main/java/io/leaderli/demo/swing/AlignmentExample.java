package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AlignmentExample {
    public static void main(String[] args) {
        JLabel label1 = new JLabel("BottomRight", SwingConstants.RIGHT);
        JLabel label2 = new JLabel("CenterLeft", SwingConstants.LEFT);
        JLabel label3 = new JLabel("TopCenter", SwingConstants.CENTER);


        File file = new File("src/main/resources/smile.gif");
        System.out.println(file.getAbsoluteFile());

        System.out.println(file.exists());
        JLabel label0 = new JLabel(new ImageIcon("src/main/resources/smile.gif"));
        label1.setVerticalAlignment(SwingConstants.BOTTOM);
        label2.setVerticalAlignment(SwingConstants.CENTER);
        label3.setVerticalAlignment(SwingConstants.TOP);


        label1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label3.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JFrame frame = new JFrame("AlignmentExample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        jPanel.add(label0);
        jPanel.add(label1);
        jPanel.add(label2);
        jPanel.add(label3);
        jPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        frame.setContentPane(jPanel);
        frame.setSize(400,400);
        frame.setLocation(400,400);
        frame.setVisible(true);
    }
}
