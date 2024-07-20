package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class SwingUtil {


    public static ImageIcon image(String name) {
        String filename;
        if (name.endsWith(".gif")) {
            filename = "src/main/resources/" + name;
        } else {
            filename = "src/main/resources/" + name + ".gif";
        }
        return new ImageIcon(filename);
    }

    public static JFrame newJFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(210, 210);
        frame.setLocation(900, 400);
        return frame;
    }

    public static void startFrameWithContent(Container container) {
        JFrame frame = newJFrame();
        frame.setContentPane(container);
        frame.setVisible(true);
    }

    public static void startFrame(Component... components) {
        startFrame(null, components);
    }

    public static void startFrame(LayoutManager layoutManager, Component... components) {
        JFrame frame = newJFrame();

        Container contentPane = frame.getContentPane();
        if (layoutManager != null) {
            contentPane.setLayout(layoutManager);
        }
        for (Component component : components) {
            contentPane.add(component);
        }
        frame.setVisible(true);
    }
}
