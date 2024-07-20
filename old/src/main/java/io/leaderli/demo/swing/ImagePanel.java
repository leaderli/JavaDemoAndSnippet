package io.leaderli.demo.swing;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ImagePanel extends JPanel {
    JLabel imageIconLabel;
    JTextField textField;

    public ImagePanel(BookEntry bookEntry) {

        setLayout(new BorderLayout());

        imageIconLabel = new JLabel(bookEntry.getImage());
        imageIconLabel.setBorder(new BevelBorder(BevelBorder.RAISED));

        textField = new JTextField(bookEntry.getTitle());
        textField.setColumns(45);
        textField.setBorder(new BevelBorder(BevelBorder.LOWERED));

        add(imageIconLabel, BorderLayout.WEST);
        add(textField, BorderLayout.EAST);
    }

    public void setText(String s) {
        textField.setText(s);
    }

    public String getText() {
        return textField.getText();
    }

    public void setIcon(Icon i) {
        imageIconLabel.setIcon(i);
        repaint();
    }

    public void selectAll() {
        textField.selectAll();
    }

    public void addActionListener(ActionListener listener) {
        textField.addActionListener(listener);
    }

    public void removeActionListener(ActionListener actionListener) {
        textField.removeActionListener(actionListener);
    }

}
