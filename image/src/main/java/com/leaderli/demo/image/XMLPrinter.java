package com.leaderli.demo.image;

import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLPrinter {

    public static void printNodeAsXML(Node node) {
        try {
            // Create a transformer
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

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


}
