package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DialogDesktop extends JFrame {


    public DialogDesktop(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JDesktopPane desk = new JDesktopPane();
        setContentPane(desk);

        JPanel p = new JPanel(new GridBagLayout());

        desk.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension deskSize = desk.getSize();
                p.setBounds(0, 0, deskSize.width, deskSize.height);
                p.validate();
            }
        });

        desk.add(p);


        JButton input = new JButton("Input");
        JButton confirm = new JButton("Confirm");
        JButton message = new JButton("Message");
        p.add(input);
        p.add(confirm);
        p.add(message);


        input.addActionListener(e -> JOptionPane.showInternalInputDialog(desk, "EnterName"));
        confirm.addActionListener(e -> JOptionPane.showInternalConfirmDialog(desk, "Is this OK"));
        message.addActionListener(e -> JOptionPane.showInternalMessageDialog(desk, "The end"));
    }

    public static void main(String[] args) {
        DialogDesktop desktop = new DialogDesktop("Desktop");
        desktop.setSize(350, 250);
        desktop.setVisible(true);
    }
}
