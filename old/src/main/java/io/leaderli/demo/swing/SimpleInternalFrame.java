package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleInternalFrame extends Frame {

    JButton openButton, javaButton, winButton;
    JLayeredPane desktop;
    JInternalFrame internalFrame;

    public SimpleInternalFrame() {
        super("demo");
        setSize(500, 400);
        openButton = new JButton("Open");
        javaButton = new JButton("Metal");
        winButton = new JButton("Windows");

        Panel panel = new Panel();
        panel.add(openButton);
        panel.add(javaButton);
        panel.add(winButton);
        add(panel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        openButton.addActionListener(new OpenListener());
        LnFListener lnf = new LnFListener(this);
        javaButton.addActionListener(lnf);
        winButton.addActionListener(lnf);

        desktop = new JDesktopPane();
        desktop.setOpaque(true);
        desktop.setDoubleBuffered(true);
        add(desktop, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SimpleInternalFrame sif = new SimpleInternalFrame();
        sif.setVisible(true);
    }

    private class OpenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (internalFrame == null || internalFrame.isClosed()) {
                System.out.println("internal");
                internalFrame = new JInternalFrame("internal frame", true, true, true, true);
                internalFrame.setBounds(50, 50, 200, 100);
                internalFrame.setDoubleBuffered(true);
                desktop.add(internalFrame, 1);
                internalFrame.setVisible(true);
            }

        }
    }
}
