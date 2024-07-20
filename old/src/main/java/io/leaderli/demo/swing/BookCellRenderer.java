package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class BookCellRenderer extends JLabel implements ListCellRenderer<BookEntry> {
    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

    public BookCellRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends BookEntry> list, BookEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getTitle());
        setIcon(value.getImage());
        if (isSelected) {
            setBackground(HIGHLIGHT_COLOR);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
}
