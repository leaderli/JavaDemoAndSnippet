package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;

public class ScrollBarExample extends JPanel {

    JLabel label;

    public ScrollBarExample() {
        super(true);
        label = new JLabel();
        setLayout(new BorderLayout());

        JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 300);
        JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);
        hbar.setUnitIncrement(2);
        hbar.setBlockIncrement(1);

        AdjustmentListener adjustmentListener = e -> {
            label.setText("new Value is " + e.getValue());
            repaint();
        };
        hbar.addAdjustmentListener(adjustmentListener);
        vbar.addAdjustmentListener(adjustmentListener);

        add(hbar, BorderLayout.SOUTH);
        add(vbar, BorderLayout.EAST);
        add(label, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        JFrame frame = SwingUtil.newJFrame();
        frame.setContentPane(new ScrollBarExample());
        frame.setVisible(true);
    }
}
