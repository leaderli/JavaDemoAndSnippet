package io.leaderli.demo.swing;

import io.leaderli.litool.core.meta.Lira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class SampleDesktop extends JFrame {

    private JDesktopPane desk;
    private IconPolice iconPolice = new IconPolice();


    public SampleDesktop(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        desk = new JDesktopPane();
        setContentPane(desk);

        desk.setDesktopManager(new SampleDesktopManager());

        createMenuBar();
        loadBackgroundImage();

    }

    private void createMenuBar() {

        JMenuBar jMenuBar = new JMenuBar();

        JMenu menu = new JMenu("Frames");

        menu.add(new AddFrameAction(true));
        menu.add(new AddFrameAction(false));
        menu.add(new TileAction(desk));

        setJMenuBar(jMenuBar);
        jMenuBar.add(menu);
    }

    private void loadBackgroundImage() {

        ImageIcon image = SwingUtil.image("matterhorn.gif");
        JLabel label = new JLabel(image);
        label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        desk.add(label, new Integer(Integer.MIN_VALUE));
    }

    public static void main(String[] args) {
        SampleDesktop sampleDesktop = new SampleDesktop("Sample Desktop");
        sampleDesktop.setSize(300, 220);
        sampleDesktop.setLocation(900, 450);
        sampleDesktop.setVisible(true);
    }

    class AddFrameAction extends AbstractAction {

        private Integer layer;

        private String name;

        @SuppressWarnings("UnnecessaryBoxing")
        public AddFrameAction(boolean upper) {

            super(upper ? "Add Upper Frame" : "Add lower Frame");

            if (upper) {
                this.layer = new Integer(2);
                this.name = "Up";
            } else {
                this.layer = new Integer(1);
                this.name = "Lo";
            }

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            JInternalFrame jInternalFrame = new JInternalFrame(name, true, true, true, true);
            jInternalFrame.addVetoableChangeListener(iconPolice);
            jInternalFrame.setBounds(0, 0, 120, 60);
            desk.add(jInternalFrame, layer);
            jInternalFrame.setVisible(true);
        }
    }

    static class TileAction extends AbstractAction {

        private final JDesktopPane desk;

        public TileAction(JDesktopPane desk) {
            super("tile Frames");
            this.desk = desk;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            JInternalFrame[] frames = desk.getAllFrames();
            int count = frames.length;
            if (count == 0) {
                return;
            }

            int sqrt = (int) Math.sqrt(count);

            int rows = sqrt;
            int cols = sqrt;

            if (rows * cols < count) {
                cols++;
                if (rows * cols < count) {
                    rows++;
                }
            }

            Dimension size = desk.getSize();

            int w = size.width / cols;
            int h = size.height / rows;

            int x = 0;
            int y = 0;

            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < cols && (i * cols) + j < count; j++) {
                    JInternalFrame f = frames[(i * cols) + j];
                    if (!f.isClosed() && f.isIcon()) {
                        try {
                            f.setIcon(false);
                        } catch (PropertyVetoException ignored) {
                        }
                    }
                    desk.getDesktopManager().resizeFrame(f, x, y, w, h);
                }
                y += h;
            }
        }
    }

    class IconPolice implements VetoableChangeListener {
        @Override
        public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {

            String name = evt.getPropertyName();
            if (name.equals(JInternalFrame.IS_ICON_PROPERTY) && evt.getNewValue() == Boolean.TRUE) {
                if (Lira.of(desk.getAllFrames())
                        .filter(i -> !i.isIcon())
                        .absent()) {
                    throw new PropertyVetoException("Invalid IconNotification!", evt);
                }

            }

        }
    }


}

class SampleDesktopManager extends DefaultDesktopManager {

    @Override
    public void dragFrame(JComponent f, int x, int y) {
        if (f instanceof JInternalFrame) {
            JInternalFrame frame = (JInternalFrame) f;
            JDesktopPane desktopPane = frame.getDesktopPane();
            Dimension d = desktopPane.getSize();

            if (x < 0) {
                x = 0;
            } else {
                if (x + frame.getWidth() > d.width) {
                    x = d.width - frame.getWidth();
                }
            }

            if (y < 0) {
                y = 0;
            } else {
                if (y + frame.getHeight() > d.height) {
                    y = d.height - frame.getHeight();
                }
            }

        }
        super.dragFrame(f, x, y);
    }

}
