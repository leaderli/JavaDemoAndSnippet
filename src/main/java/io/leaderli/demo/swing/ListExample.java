package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;

public class ListExample extends JPanel {
    public static final BookEntry[] books = {
            new BookEntry("Ant: The Definitive Guide", "ant.gif"),
            new BookEntry("Database Programming with JDBC and Java",
                    "jdbc.gif"),
            new BookEntry("Developing Java Beans", "beans.gif"),
            new BookEntry("Developing JSP Custom Tag Libraries", "jsptl.gif"),
            new BookEntry("Java 2D Graphics", "java2d.gif"),
            new BookEntry("Java and XML", "jxml.gif"),
            new BookEntry("Java and XSLT", "jxslt.gif"),
            new BookEntry("Java and SOAP", "jsoap.gif"),
            new BookEntry("Java and XML Data Binding", "jxmldb.gif"),
            new BookEntry("Java Cookbook", "jcook.gif"),
            new BookEntry("Java Cryptography", "jcrypto.gif"),
            new BookEntry("Java Distributed Computing", "jdist.gif"),
            new BookEntry("Java I/O", "javaio.gif"),
            new BookEntry("Java in a Nutshell", "javanut.gif"),
            new BookEntry("Java Management Extensions", "jmx.gif"),
            new BookEntry("Java Message Service", "jms.gif"),
            new BookEntry("Java Network Programming", "jnetp.gif"),
            new BookEntry("Java Performance Tuning", "jperf.gif"),
            new BookEntry("Java RMI", "jrmi.gif"),
            new BookEntry("Java Security", "jsec.gif"),
            new BookEntry("JavaServer Pages", "jsp.gif"),
            new BookEntry("Java Servlet Programming", "servlet.gif"),
            new BookEntry("Java Swing", "swing.gif"),
            new BookEntry("Java Threads", "jthread.gif"),
            new BookEntry("Java Web Services", "jws.gif"),
            new BookEntry("Learning Java", "learnj.gif")

    };
    private JList<BookEntry> booklist = new JList<>(books);

    public ListExample() {
        setLayout(new BorderLayout());
        JButton button = new JButton("Print");
        button.addActionListener(e -> System.out.println(booklist.getSelectedValuesList()));
        booklist = new JList<>(books);
        booklist.setCellRenderer(new BookCellRenderer());
        booklist.setVisibleRowCount(4);
        JScrollPane pane = new JScrollPane(booklist);
        add(pane, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
    }

    public static void main(String... args) {
        JFrame frame = new JFrame("List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ListExample());
        frame.pack();
        frame.setVisible(true);
    }
}
