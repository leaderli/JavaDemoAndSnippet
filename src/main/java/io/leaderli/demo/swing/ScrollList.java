package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class ScrollList extends BaseFrame {
    public ScrollList() throws HeadlessException {
        super("JScrollPane Demonstration");

        String categories[] = {"Household", "Office", "Extended Family",
                "Company (US)", "Company (World)", "Team",
                "Will", "Birthday Card List", "High School",
                "Country", "Continent", "Planet"};

        JList<String> list = new JList<>(categories);
        JScrollPane scrollPane = new JScrollPane(list);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        ScrollList s = new ScrollList();
        s.setVisible(true);
    }
}
