package io.leaderli.demo;

import io.leaderli.litool.core.util.LiPrintUtil;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

/**
 * @author leaderli
 * @since 2022/7/9 6:22 AM
 */
public class SaxParserTest {

    @Test
    public void test() throws Throwable {

        InputStream inputStream = SaxParserTest.class.getResourceAsStream("/logback.xml");

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();

        saxParser.parse(inputStream, new MyDefaultHandler());
    }

    private static class MyDefaultHandler extends DefaultHandler {

        private Locator locator;


        @Override
        public void setDocumentLocator(Locator locator) {
            this.locator = locator;
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            for (int i = 0; i < attributes.getLength(); i++) {
                LiPrintUtil.print(attributes.getQName(i), attributes.getValue(i));
            }
            LiPrintUtil.print("start", locator.getLineNumber(), qName, attributes);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            LiPrintUtil.print("end", locator.getLineNumber(), qName);

        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            LiPrintUtil.print("body", locator.getLineNumber(), new String(ch, start, length).trim());
        }


        @Override
        public void warning(SAXParseException e) throws SAXException {
//            e.printStackTrace();
        }


        @Override
        public void error(SAXParseException e) throws SAXException {
//            super.error(e);
//            e.printStackTrace();
            System.out.println(e.toString());

        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
//            super.fatalError(e);
//            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
