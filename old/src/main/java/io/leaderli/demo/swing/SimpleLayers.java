package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class SimpleLayers extends JFrame {

    public SimpleLayers() {
        super("LayeredPane Demonstration");
        setSize(200, 150);
        setLocation(900, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLayeredPane lp = getLayeredPane();

        JButton top = new JButton();
        top.setBackground(Color.white);
        top.setBounds(20, 20, 50, 50);

        JButton middle = new JButton();
        middle.setBackground(Color.gray);
        middle.setBounds(40, 40, 50, 50);

        JButton bottom = new JButton();
        bottom.setBackground(Color.black);
        bottom.setBounds(60, 60, 50, 50);

        lp.add(middle, new Integer(2));
        lp.add(top, new Integer(3));
        lp.add(bottom, new Integer(1));

    }

    public static void main(String[] args) {
        SimpleLayers sl = new SimpleLayers();

        sl.setVisible(true);
    }
}
