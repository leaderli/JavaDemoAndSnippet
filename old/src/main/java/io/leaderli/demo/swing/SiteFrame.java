package io.leaderli.demo.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SiteFrame extends JInternalFrame {

    private SiteManager siteManager;

    private JList<String> nameList;
    private String[] pages = {"index.html", "page1.html", "page2.html"};


    public SiteFrame(String name, SiteManager siteManager) {
        super("Site:" + name, true, true, true, true);
        this.siteManager = siteManager;
        setBounds(50, 50, 250, 100);

        nameList = new JList<>(pages);

        nameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    siteManager.addPageFrame(nameList.getSelectedValue());
                }
            }
        });
        nameList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                siteManager.addPageFrame(nameList.getSelectedValue());
            }
        });

        Container contentPane = getContentPane();
        contentPane.add(nameList, BorderLayout.CENTER);
    }
}
