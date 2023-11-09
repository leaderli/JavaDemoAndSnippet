package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class SliderExample extends JPanel {

    public SliderExample() {
        super(true);
        this.setLayout(new BorderLayout());
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(10));
        slider.addChangeListener(e -> {
            JSlider jSlider = (JSlider) e.getSource();
            System.out.println(e + " " + jSlider.getValue());
        });
        add(slider, BorderLayout.CENTER);
    }


    public static void main(String[] args) {

        JFrame frame = SwingUtil.newJFrame();
        frame.setContentPane(new SliderExample());
        frame.setVisible(true);
    }
}
