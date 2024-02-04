package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {

    public BaseFrame() throws HeadlessException {
        this("");
    }

    public BaseFrame(String title) throws HeadlessException {
        super(title);
        setSize(450, 200);
        setLocation(900, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
