package io.leaderli.demo.swing;

import io.leaderli.litool.core.util.ThreadUtil;

import javax.swing.*;

public class ProgressBarExample extends JPanel {
    JProgressBar jProgressBar;
    static final int MIN = 0;
    static final int MAX = 100;


    public ProgressBarExample() {
        jProgressBar = new JProgressBar();
        jProgressBar.setMinimum(MIN);
        jProgressBar.setMaximum(MAX);
        add(jProgressBar);
    }

    public void updateBar(int newValue) {
        jProgressBar.setValue(newValue);
    }

    public static void main(String[] args) {
        ProgressBarExample it = new ProgressBarExample();
        JFrame frame = SwingUtil.newJFrame();
        frame.setContentPane(it);
        frame.setVisible(true);
        for (int i = MIN; i <= MAX; i++) {
            int percent = i;
            SwingUtilities.invokeLater(() -> it.updateBar(percent));
            ThreadUtil.sleep(100);
        }
    }
}
