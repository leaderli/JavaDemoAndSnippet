package io.leaderli.demo.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BoundedExample {
    public static void main(String[] args) {
        DefaultBoundedRangeModel model = new DefaultBoundedRangeModel();
        model.addChangeListener(new MyChangeListener());
        model.setMaximum(5);
        model.setValue(10);
        model.setValue(11);

    }
}

class MyChangeListener implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {

        System.out.println("changed:" + e.getSource());
    }
}
