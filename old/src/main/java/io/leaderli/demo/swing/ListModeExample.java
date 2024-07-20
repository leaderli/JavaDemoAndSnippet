package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class ListModeExample extends JPanel {
    JList<Integer> list;
    DefaultListModel<Integer> model;
    int counter = 15;

    public ListModeExample() {
        setLayout(new BorderLayout());
        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(System.out::println);

        JScrollPane pane = new JScrollPane(list);
        Button add = new Button("add");
        Button remove = new Button("remove");

        for (int i = 0; i < 15; i++) {
            model.addElement(i);
        }
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    setBackground(Color.RED);
                    setForeground(Color.white);
                } else {
                    setBackground(Color.white);
                    setForeground(Color.black);
                }
                return this;
            }
        });

        add.addActionListener(e -> model.addElement(counter++));
        remove.addActionListener(e -> {
            if (model.getSize() > 0) {
                model.removeElementAt(0);
            }
        });

        add(pane, BorderLayout.NORTH);
        add(add, BorderLayout.WEST);
        add(remove, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        SwingUtil.startFrameWithContent(new ListModeExample());
    }

}
