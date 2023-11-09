package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class GlassExample extends JFrame {

    JPanel glass = new JPanel(new GridLayout(0, 1));
    JProgressBar waiter = new JProgressBar(0, 100);
    Timer timer;

    public GlassExample() {
        super("GlassPanel Demo");
        setSize(500, 300);
        setLocation(900, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel(new GridLayout(2, 1));
        controlPanel.add(new JLabel("Please wait ..."));
        controlPanel.add(waiter);

        glass.setOpaque(false);
        glass.add(new JLabel());
        glass.add(new JLabel());
        glass.add(controlPanel);
        glass.add(new JLabel());
        glass.add(new JLabel());
        glass.addMouseListener(new MouseAdapter() {
        });
        glass.addMouseMotionListener(new MouseMotionAdapter() {
        });
        setGlassPane(glass);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);

        JButton red = new JButton("Red");
        JButton blue = new JButton("Blue");
        JButton green = new JButton("Green");

        mainPanel.add(red);
        mainPanel.add(blue);
        mainPanel.add(green);
        mainPanel.add(new JLabel(SwingUtil.image("oreilly.gif")));

        PopupDebugger pd = new PopupDebugger(this);
        red.addActionListener(pd);
        blue.addActionListener(pd);
        green.addActionListener(pd);

        JButton start = new JButton("Start the big operation");
        start.addActionListener(e -> {
            glass.setVisible(true);
            startTimer();
        });

        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(start, BorderLayout.SOUTH);

    }

    private void startTimer() {

        if (timer == null) {
            timer = new Timer(1000, new ActionListener() {
                int progress = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    progress += 10;
                    waiter.setValue(progress);
                    if (progress >= 100) {
                        progress = 0;
                        timer.stop();
                        glass.setVisible(false);
                        waiter.setValue(0);
                    }

                }
            });
        }
        if(timer.isRunning()){
            timer.stop();
        }

        timer.start();

    }

    public static void main(String[] args) {
        GlassExample ge = new GlassExample();
        ge.setVisible(true);
    }
}
