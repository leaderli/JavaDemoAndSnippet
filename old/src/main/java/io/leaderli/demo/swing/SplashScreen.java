package io.leaderli.demo.swing;

import io.leaderli.litool.core.resource.ResourceUtil;
import io.leaderli.litool.core.util.ThreadUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class SplashScreen extends JWindow {

    private int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() throws IOException {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        int width = 450;
        int height = 115;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        setBounds(x, y, width, height);

        URL resourceAsStream = ResourceUtil.getResource("oreilly.gif");
        ImageIcon image = new ImageIcon(ImageIO.read(resourceAsStream));
        JLabel label = new JLabel(image);

        JLabel copyright = new JLabel("Copyright 2002, O'Reilly & Associates", JLabel.CENTER);

        copyright.setFont(new Font("Sans-serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyright, BorderLayout.SOUTH);

        Color oraRed = new Color(156, 20, 20, 255);
        content.setBorder(BorderFactory.createLineBorder(oraRed, 10));

        setVisible(true);

        ThreadUtil.sleep(duration);
        setVisible(false);
    }

    public void showSplashAndExit(){
        try {
            showSplash();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen(1000);

        splashScreen.showSplashAndExit();
    }
}
