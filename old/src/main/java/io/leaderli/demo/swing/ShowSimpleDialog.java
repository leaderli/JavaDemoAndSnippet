package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowSimpleDialog {

    public static void main(String[] args) {
        JFrame frame = SwingUtil.newJFrame();


        frame.setVisible(true);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "Click OK after you read this", "Click OK", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private static void showDialog(JFrame frame) {
        JDialog d = new JDialog(frame, "Click OK", true);
        d.setSize(200, 150);
        JLabel l = new JLabel("Click OK after you read this", JLabel.CENTER);
        d.getContentPane().setLayout(new BorderLayout());
        d.getContentPane().add(l, BorderLayout.CENTER);
        JButton b = new JButton("OK");
        b.addActionListener(e -> {
            d.setVisible(false);
            d.dispose();
        });

        JPanel p = new JPanel();
        p.add(b);
        d.getContentPane().add(p, BorderLayout.SOUTH);
        d.setLocationRelativeTo(frame);
        d.setVisible(true);
    }
}

