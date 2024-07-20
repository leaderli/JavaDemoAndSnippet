package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class SimpleSplitPane extends BaseFrame {
    String someText = "This is a simple text string that is long enough " +
            "to wrap over a few lines in the simple demo we're about to build. We'll " +
            "put two text areas side by side in a split pane.";
    ;

    public SimpleSplitPane(String title) throws HeadlessException {
        super(title);
        JTextArea jt1 = new JTextArea(someText);
        JTextArea jt2 = new JTextArea(someText);

        jt1.setLineWrap(true);
        jt2.setLineWrap(true);

        jt1.setMinimumSize(new Dimension(150, 150));
        jt2.setMinimumSize(new Dimension(150, 150));

        jt1.setPreferredSize(new Dimension(250, 200));

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jt1, jt2);
        sp.setDividerSize(1);

        getContentPane().add(sp, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SimpleSplitPane simpleSplitPaneFrame = new SimpleSplitPane("Simple SplitPane Frame");
        simpleSplitPaneFrame.setVisible(true);
    }
}
