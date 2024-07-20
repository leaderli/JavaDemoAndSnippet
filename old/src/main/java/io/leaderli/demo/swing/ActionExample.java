package io.leaderli.demo.swing;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ActionExample extends JPanel {

    public JMenuBar menuBar;
    public JToolBar toolBar;


    public ActionExample() {
        super(true);

        menuBar = new JMenuBar();
        menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);


        toolBar = new JToolBar();
        toolBar.setBorder(new EtchedBorder());

        ImageIcon icon = new ImageIcon("60079.gif");
        Image image = icon.getImage();
        image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);

        SampleAction sampleAction = new SampleAction("Download", icon);

        JMenuItem menuItem = new JMenuItem(sampleAction);
        menu.add(menuItem);

        JButton button = new JButton(sampleAction);
        toolBar.add(button);

        sampleAction.setEnabled(false);

    }

    public static void main(String[] args) {
        ActionExample actionExample = new ActionExample();

        JFrame frame = new JFrame("action example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(actionExample.menuBar);
        frame.getContentPane().add(actionExample.toolBar, BorderLayout.NORTH);
        frame.setSize(200, 200);
        frame.setVisible(true);

    }
}
