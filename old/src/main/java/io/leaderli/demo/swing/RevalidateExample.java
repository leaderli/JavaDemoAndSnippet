package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RevalidateExample extends JFrame {

    public RevalidateExample() {

        super("Revalidation Demo");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Font font = new Font("Dialog", Font.PLAIN, 10);

        JButton add = new JButton("add");
        add.setFont(font);

        add.addActionListener(new ActionListener() {
            int size = 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                add.setFont(new Font("Dialog", Font.PLAIN, ++size));
                add.revalidate();
            }
        });

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(add);
    }

    public static void main(String[] args) {
        RevalidateExample re = new RevalidateExample();
        re.setVisible(true);
    }
}
