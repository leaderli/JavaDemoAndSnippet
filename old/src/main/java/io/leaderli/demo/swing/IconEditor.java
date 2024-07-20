package io.leaderli.demo.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class IconEditor extends JLabel implements ChangeListener {
    JSpinner spinner;
    Icon icon;

    public IconEditor(JSpinner spinner) {
        super(((BookEntry) spinner.getValue()).getImage(), CENTER);
        icon = ((BookEntry) spinner.getValue()).getImage();
        this.spinner = spinner;
        spinner.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        icon =((BookEntry) spinner.getValue()).getImage();
        setIcon(icon);
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }
}
