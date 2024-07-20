package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LnFListener  implements ActionListener {
    Frame frame;

    public LnFListener(Frame f) {
        frame = f;
    }

    public void actionPerformed(ActionEvent e) {
        String lnfName = null;
        if (e.getActionCommand().equals("Mac")) {
            lnfName = "com.apple.mrj.swing.MacLookAndFeel";
        } else if (e.getActionCommand().equals("Metal")) {
            lnfName = "javax.swing.plaf.metal.MetalLookAndFeel";
        } else if (e.getActionCommand().equals("Motif")) {
            lnfName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        } else if (e.getActionCommand().equals("Windows")) {
            lnfName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else {
            System.err.println("Unrecognized L&F request action: " +
                    e.getActionCommand());
            return;
        }
        try {
            UIManager.setLookAndFeel(lnfName);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (UnsupportedLookAndFeelException ex1) {
            System.err.println("Unsupported LookAndFeel: " + lnfName);
        } catch (ClassNotFoundException ex2) {
            System.err.println("LookAndFeel class not found: " + lnfName);
        } catch (InstantiationException ex3) {
            System.err.println("Could not load LookAndFeel: " + lnfName);
        } catch (IllegalAccessException ex4) {
            System.err.println("Cannot use LookAndFeel: " + lnfName);
        }
    }
}
