package io.leaderli.demo.swing;

import javax.swing.*;

public class BookEntry {

    private final String title;

    private final String imagePath;

    private ImageIcon image;


    public BookEntry(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageIcon getImage() {
        if (image == null) {
            image = SwingUtil.image(imagePath);
        }
        return image;
    }

    @Override
    public String toString() {
        return title;
    }
}
