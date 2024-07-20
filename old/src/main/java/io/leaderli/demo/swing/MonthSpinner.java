package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class MonthSpinner {

    public static void main(String[] args) {
        JFrame frame = SwingUtil.newJFrame();
        Container c = frame.getContentPane();
        c.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));

        c.add(new JLabel("Expiration Date:"));
        Date today = new Date();

        JSpinner s = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.MONTH));
        s.setEditor(new JSpinner.DateEditor(s, "MM/yy"));
        ((JSpinner.DateEditor) s.getEditor()).getTextField().setEditable(false);
        c.add(s);
        frame.setVisible(true);
    }
}
