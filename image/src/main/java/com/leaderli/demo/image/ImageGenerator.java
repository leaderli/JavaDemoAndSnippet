package com.leaderli.demo.image;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageMetadata;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriter;
import org.w3c.dom.Node;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageGenerator {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void main(String[] args) throws IOException {
        XMLPrinter.read();
        int width = 500;
        int height = 500;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D graphics = image.createGraphics();

        // Fill the background with white color
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // Draw a red rectangle
        graphics.setColor(Color.red);
        graphics.fillRect(50, 50, 200, 100);
        graphics.setColor(Color.black);
        graphics.drawString("hello",10,10);
        graphics.dispose();

        File output = new File("generated_image.tiff");
        System.out.println(image);
        TIFFImageWriter writer = (TIFFImageWriter) ImageIO.getImageWritersByFormatName("TIFF").next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionType("CCITT T.4");
//        writeParam.setDestinationType(image.getType());
//        writeParam.setDestinationType(ImageTypeSpecifier.createFromBufferedImageType(image.getType()));
        TIFFImageMetadata metadata = writer.getDefaultImageMetadata(ImageTypeSpecifier.createFromBufferedImageType(image.getType()), writeParam);
//        metadata.getRootIFD().addTIFFField(new TIFFField(new TIFFTag("BitsPerSample", 258, DataBuffer.TYPE_INT, new TIFFTagSet(new ArrayList())), 1));
        String nativeMetadataFormatName = "com_sun_media_imageio_plugins_tiff_image_1.0";
        Node root = metadata.getAsTree(nativeMetadataFormatName);

//        IIOMetadataNode newRoot = new IIOMetadataNode(root);

        metadata.mergeTree(nativeMetadataFormatName, root);


        // 创建一个新的 SampleModel，这里示例使用一个新的 SampleModel

        // 创建一个新的 BufferedImage，使用新的 SampleModel
        ColorModel colorModel = image.getColorModel();
        SampleModel compatibleSampleModel = colorModel.createCompatibleSampleModel(image.getWidth(), image.getHeight());
//        BufferedImage newImage = new BufferedImage(colorModel, image.getRaster(), image.getColorModel().isAlphaPremultiplied(), null);

//        colorModel = new DirectColorModel(0xff000001,colorModel.getRedMask(),colorModel.getGreenMask(),colorModel.getBlueMask());
        BufferedImage newImage =  new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        newImage.getGraphics().drawImage(image,0,0,null);

        // Write the image to the file
        writer.setOutput(ImageIO.createImageOutputStream(output));
        IIOImage iioimage = new IIOImage(newImage, null, metadata);
//        writer.write(iioimage.getRenderedImage());
        System.out.println(Arrays.toString(iioimage.getMetadata().getMetadataFormatNames()));
        writer.write(null, iioimage, writeParam);


        String[] nodeNames = metadata.getMetadataFormatNames();
        Node asTree = metadata.getAsTree("com_sun_media_imageio_plugins_tiff_image_1.0");
        XMLPrinter.printNodeAsXML(asTree);
        System.out.println("TIFF Image created successfully!");
        XMLPrinter.read();

    }

}
