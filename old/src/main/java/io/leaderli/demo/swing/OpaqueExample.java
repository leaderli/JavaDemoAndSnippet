package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class OpaqueExample {

    public static void main(String[] args) {
        SwingUtil.startFrame(new FlowLayout(),
                createNested(true),
                createNested(false)
        );
    }

    public static JPanel createNested(boolean opaque) {

        JPanel outer = new JPanel(new FlowLayout());
        JPanel inner = new JPanel(new FlowLayout());
        outer.setBackground(Color.white);
        inner.setBackground(Color.black);

        inner.setOpaque(opaque);
        inner.setBorder(BorderFactory.createLineBorder(Color.gray));
        inner.add(new JButton("Button"));
        outer.add(inner);
        return outer;
    }
}
