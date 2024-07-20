package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class SimpleListPanel extends JPanel {

    Integer[] label = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    JList<Integer> jList;

    public SimpleListPanel() {
        this.setLayout(new BorderLayout());
        jList = new JList<>(label);
        jList.setSelectionInterval(3,7);

        JScrollPane jScrollPane = new JScrollPane(jList);
        JButton button = new JButton("print");
        button.addActionListener(e -> System.out.println(jList.getSelectedValuesList()));
        add(jScrollPane, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtil.startFrameWithContent(new SimpleListPanel());
    }
}
