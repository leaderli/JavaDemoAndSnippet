package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SimpleButtonGroupExample {
    public static void main(String[] args) {
        JRadioButton choice1 = new JRadioButton("Book 1");
        choice1.setActionCommand("b1");
        JRadioButton choice2 = new JRadioButton("Book 2");
        choice2.setActionCommand("b2");
        JRadioButton choice3 = new JRadioButton("Book 3");
        choice3.setActionCommand("b3");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(choice1);
        buttonGroup.add(choice2);
        buttonGroup.add(choice3);

        ActionListener actionListener = e -> System.out.println("action:" + buttonGroup.getSelection().getActionCommand());
        ItemListener itemListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("item:" + ((AbstractButton) e.getItemSelectable()).getActionCommand());
            }
        };
        choice1.addActionListener(actionListener);
        choice2.addActionListener(actionListener);
        choice3.addActionListener(actionListener);
        choice1.addItemListener(itemListener);
        choice2.addItemListener(itemListener);
        choice3.addItemListener(itemListener);

        SwingUtil.startFrame(new GridLayout(0, 1)
                , new JLabel("select one "), choice1, choice2, choice3);


    }
}
