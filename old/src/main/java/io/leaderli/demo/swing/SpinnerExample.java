package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SpinnerExample {
    public static void main(String[] args) {
        SwingUtil.startFrame(new GridLayout(0, 2),
                new JLabel("basic spinner"),
                new JSpinner(),
                new JLabel("data spinner"),
                new JSpinner(new SpinnerDateModel()),
                new JLabel("list spinner"),
                new JSpinner(new SpinnerListModel(Arrays.asList("one", "two", "three"))),
                new JLabel("number spinner"),
                new JSpinner(new SpinnerNumberModel(0, 0, 100, 5)),
                new JLabel("rollover list spinner"),
                new JSpinner(new RolloverSpinnerListModel<>(Arrays.asList('a', 'b', 'c', 'd', 'e')))
        );


    }
}
