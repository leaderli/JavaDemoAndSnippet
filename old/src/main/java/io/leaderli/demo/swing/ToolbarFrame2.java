package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


public class ToolbarFrame2 extends Frame {
    // This time, let's use JButtons!
    JButton cutButton, copyButton, pasteButton;
    JButton javaButton, macButton, motifButton, winButton;

    public ToolbarFrame2() {
        super("Toolbar Example (Swing)");
        setSize(450, 250);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ActionListener printListener = ae -> System.out.println(ae.getActionCommand());
// JPanel works similarly to Panel, so we'll use it.
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        File file = new File("cut.gif");
        cutButton = new JButton(new ImageIcon("cut.gif", "cut"));
        cutButton.setActionCommand("cut");
        cutButton.addActionListener(printListener);
        toolbar.add(cutButton);
        copyButton = new JButton("Copy");
        copyButton.addActionListener(printListener);
        toolbar.add(copyButton);
        pasteButton = new JButton("Paste");
        pasteButton.addActionListener(printListener);
        toolbar.add(pasteButton);
        add(toolbar, BorderLayout.NORTH);
// Add the L&F controls.
        JPanel lnfPanel = new JPanel();
        LnFListener lnfListener = new LnFListener(this);
        macButton = new JButton("Mac");
        macButton.addActionListener(lnfListener);
        lnfPanel.add(macButton);
        javaButton = new JButton("Metal");
        javaButton.addActionListener(lnfListener);
        lnfPanel.add(javaButton);
        motifButton = new JButton("Motif");
        motifButton.addActionListener(lnfListener);
        lnfPanel.add(motifButton);
        winButton = new JButton("Windows");
        winButton.addActionListener(lnfListener);
        lnfPanel.add(winButton);
        add(lnfPanel, BorderLayout.SOUTH);
    }

    public static void main(String args[]) {
        ToolbarFrame2 tf2 = new ToolbarFrame2();
        tf2.setVisible(true);
    }
}
