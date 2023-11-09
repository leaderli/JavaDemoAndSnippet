package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class IconSpinner {
    public static void main(String[] args) {
        BookEntry[] nums = ListExample.books;

        JSpinner jSpinner = new JSpinner(new SpinnerListModel(nums));
        jSpinner.setEditor(new IconEditor(jSpinner));
        SwingUtil.startFrame(new GridLayout(0, 2),
                new Label("Icon spinner"),
                jSpinner
        );
    }
}
