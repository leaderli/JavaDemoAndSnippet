package io.leaderli.demo.swing;

import io.leaderli.litool.core.text.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PageFrame extends JInternalFrame implements ActionListener {
    private final SiteManager siteManager;
    private String fileName;
    private JTextArea jTextArea;

    public PageFrame(String fileName, SiteManager siteManager) {
        super("Page:" + fileName, true, true, true, true);
        this.siteManager = siteManager;
        this.fileName = fileName;
        setBounds(50, 50, 300, 150);
        Container contentPane = getContentPane();

        jTextArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        contentPane.add(jScrollPane, BorderLayout.CENTER);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> saveContent());
        fileMenu.add(saveMenuItem);

        jMenuBar.add(fileMenu);
        setJMenuBar(jMenuBar);
        loadContent();


    }

    public void loadContent() {

        try {
            FileReader fileReader = new FileReader(fileName);
            jTextArea.read(fileReader, null);
            fileReader.close();
        } catch (Exception e) {
            System.err.println("Could not load page:" + fileName);
        }
    }

    public void saveContent() {

        System.out.println("save");
        try {
            FileWriter fw = new FileWriter(fileName);
            jTextArea.write(fw);
            fw.close();

            FileReader fileReader = new FileReader(fileName);
            System.out.println(StringUtils.read(Files.newInputStream(Paths.get(fileName))));
        } catch (Exception e) {
            System.err.println("Could not save page:" + fileName);
        }
    }

    public void copyText() {
        jTextArea.copy();
    }

    public void cutText() {
        jTextArea.cut();
    }

    public void pasteText() {
        jTextArea.paste();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "copy":
                copyText();
                return;
            case "cut":
                cutText();
                return;
            case "paste":
                pasteText();
        }
    }
}
