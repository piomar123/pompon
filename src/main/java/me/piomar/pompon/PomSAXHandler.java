package me.piomar.pompon;

import org.apache.maven.plugin.logging.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class PomSAXHandler extends DefaultHandler implements LexicalHandler {

    private final Log log;

    public PomSAXHandler(Log log) {
        this.log = log;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        log.info(String.format("%s %s %s %d", uri, localName, qName, attributes.getLength()));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    @Override
    public void comment(char[] ch, int start, int length) throws SAXException {
        String commentText = String.valueOf(ch, start, length);
        log.warn(commentText);
    }

    @Override
    public void startDTD(String name, String publicId, String systemId) throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void startEntity(String name) throws SAXException {
    }

    @Override
    public void endEntity(String name) throws SAXException {
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void endCDATA() throws SAXException {
    }
}
