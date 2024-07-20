package io.leaderli.demo.swing;

import io.leaderli.litool.core.meta.Lino;
import io.leaderli.litool.core.meta.Lira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class SiteManager extends JFrame {

    JLayeredPane desktop;
    Vector<JInternalFrame> popups = new Vector<>();


    public SiteManager() {
        setSize(450, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contentPane = getContentPane();

        JToolBar jToolBar = new JToolBar();
        jToolBar.add(new TextAction("copy", this));
        jToolBar.add(new TextAction("cut", this));
        jToolBar.add(new TextAction("paste", this));
        contentPane.add(jToolBar, BorderLayout.NORTH);

        desktop = new JDesktopPane();
        contentPane.add(desktop, BorderLayout.CENTER);

        addSiteFrame("sample");
    }

    public static void main(String[] args) {
        SiteManager siteManager = new SiteManager();
        siteManager.setVisible(true);
    }

    private void addSiteFrame(String name) {
        SiteFrame siteFrame = new SiteFrame(name, this);
        popups.add(siteFrame);
        desktop.add(siteFrame, 2);
        siteFrame.setVisible(true);
    }

    public void addPageFrame(String name) {
        PageFrame pageFrame = new PageFrame(name, this);
        desktop.add(pageFrame, 1);
        pageFrame.setVisible(true);
        pageFrame.setIconifiable(true);
        popups.addElement(pageFrame);
    }

    public Lino<JInternalFrame> getCurrentFrame() {

        return Lira.of(popups)
                .filter(JInternalFrame::isSelected)
                .first();
    }


    private static class TextAction extends AbstractAction {
        private final SiteManager siteManager;

        public TextAction(String name, SiteManager siteManager) {
            super(name);
            this.siteManager = siteManager;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println(e.getActionCommand());
            siteManager.getCurrentFrame()
                    .cast(PageFrame.class)
                    .ifPresent(f -> f.actionPerformed(e))
            ;

        }
    }

}
