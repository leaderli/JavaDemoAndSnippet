package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static io.leaderli.demo.swing.ListExample.books;

public class EditableComboBox extends JPanel {

    Map<String, BookEntry> bookMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public EditableComboBox() {
        for (BookEntry book : books) {
            bookMap.put(book.getTitle(), book);
        }
        setLayout(new BorderLayout());
        JComboBox<BookEntry> bookCombo = new JComboBox<>(books);
        bookCombo.setEditable(true);
        bookCombo.setEditor(new ComboBoxEditorExample(bookMap, new ImagePanel(books[0])));
        bookCombo.setMaximumRowCount(4);
        bookCombo.addActionListener(e -> {
            JComboBox<BookEntry> source = (JComboBox<BookEntry>) e.getSource();
            System.out.println("chose:" + source.getSelectedItem());
        });
        bookCombo.setActionCommand("hello");

        add(bookCombo, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtil.startFrameWithContent(new EditableComboBox());

    }

}
