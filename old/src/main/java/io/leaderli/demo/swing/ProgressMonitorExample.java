package io.leaderli.demo.swing;

import cn.hutool.core.util.RandomUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgressMonitorExample extends JFrame implements ActionListener {
    static ProgressMonitor progressMonitor;
    static int counter;

    public ProgressMonitorExample() {
        super("demo");
        setSize(250, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressMonitor = new ProgressMonitor(null, "monitor", "initializing", 0, 100);
        Timer timer = new Timer(500, this);
        timer.start();
        setVisible(true);
    }

    public static void main(String[] args) {
        UIManager.put("ProgressMonitor.progressText", "This is progress?");
        UIManager.put("OptionPane.cancelButtonText", "Go Away");
        new ProgressMonitorExample();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        SwingUtilities.invokeLater(() -> {
            if (progressMonitor.isCanceled()) {

                progressMonitor.close();
                System.exit(1);
            }
            progressMonitor.setProgress(counter);
            progressMonitor.setNote("Operation is " + counter + "% complete");

            counter += RandomUtil.randomInt(10);
        });
    }
}
