package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupDebugger implements ActionListener {
    private JFrame parent;

    public PopupDebugger(JFrame parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(parent, e.getActionCommand());
    }
}
