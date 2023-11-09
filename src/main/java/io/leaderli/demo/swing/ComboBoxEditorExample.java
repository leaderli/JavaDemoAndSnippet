package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class ComboBoxEditorExample implements ComboBoxEditor {

    Map<String, BookEntry> map;
    ImagePanel panel;
    ImageIcon questionIcon;

    public ComboBoxEditorExample(Map<String, BookEntry> map, ImagePanel panel) {
        this.map = map;
        this.panel = panel;
        questionIcon = SwingUtil.image("question.gif");
    }

    @Override
    public Component getEditorComponent() {
        return panel;
    }

    @Override
    public void setItem(Object anObject) {
        if (anObject != null) {
            panel.setText(anObject + "");
            BookEntry bookEntry = map.get(anObject + "");
            if (bookEntry != null) {
                panel.setIcon(bookEntry.getImage());
            } else {
                panel.setIcon(questionIcon);
            }
        }
    }

    @Override
    public Object getItem() {
        return panel.getText();
    }

    @Override
    public void selectAll() {
        panel.selectAll();
    }


    @Override
    public void addActionListener(ActionListener l) {

        panel.addActionListener(l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        panel.removeActionListener(l);
    }
}
