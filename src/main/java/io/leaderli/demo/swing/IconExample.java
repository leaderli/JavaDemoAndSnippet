package io.leaderli.demo.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class IconExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JLabel label1 = new JLabel(new RectIcon(20, 50));
//        JLabel label2 = new JLabel(new RectIcon(50, 20));
//        JLabel label3 = new JLabel("Rect", new RectIcon(50, 20), SwingConstants.CENTER);
//        label3.setHorizontalTextPosition(SwingConstants.CENTER);

        JSlider width = new JSlider(JSlider.HORIZONTAL, 1, 150, 75);
        JSlider height = new JSlider(JSlider.VERTICAL, 1, 150, 75);

        class DynamicIcon implements Icon {

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.fill3DRect(x, y, getIconWidth(), getIconHeight(), true);

            }

            @Override
            public int getIconWidth() {
                return width.getValue();
            }

            @Override
            public int getIconHeight() {
                return height.getValue();
            }
        }

        JLabel label4 = new JLabel(new DynamicIcon());
        class Updater implements ChangeListener {

            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(width.getWidth()+" "+ height.getHeight());
                label4.repaint();
            }
        }
        Updater updater = new Updater();
        width.addChangeListener(updater);
        height.addChangeListener(updater);


        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(label4);
        contentPane.add(width, BorderLayout.NORTH);
        contentPane.add(height, BorderLayout.WEST);
        frame.setLocation(500, 400);
        frame.setSize(210, 210);
        frame.setVisible(true);
    }
}

class RectIcon implements Icon {

    private int width, height;

    public RectIcon(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawRect(x, y, width - 1, height - 1);
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
