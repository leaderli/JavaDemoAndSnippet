package com.leaderli.demo.image;

import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageReader;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLPrinter {

    public static void printNodeAsXML(Node node) {
        try {
            // Create a transformer
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            // Create a DOMSource from the node
            DOMSource source = new DOMSource(node);

            // Define a StreamResult for the output
            StreamResult result = new StreamResult(System.out);

            // Transform the node to XML and print to console
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    public static void read() throws IOException {
        File output = new File("generated_image.tiff");
        ImageInputStream iis = ImageIO.createImageInputStream(output);
        TIFFImageReader reader = (TIFFImageReader) ImageIO.getImageReaders(iis).next();
        reader.setInput(iis);
        IIOMetadata metadata = reader.getImageMetadata(0);
        printNodeAsXML( metadata.getAsTree("com_sun_media_imageio_plugins_tiff_image_1.0"));
    }
}
