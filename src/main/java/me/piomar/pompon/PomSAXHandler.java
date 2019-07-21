package me.piomar.pompon;

import java.util.ArrayDeque;
import java.util.Deque;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

class PomSAXHandler extends DefaultHandler implements LexicalHandler {

    private final Deque<PomElement> elementsStack = new ArrayDeque<>();
    private PomElement currentElement;
    private Locator locator;

    public PomSAXHandler() {
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = new PomElement(qName);
        if (!elementsStack.isEmpty()) {
            elementsStack.getLast().children.add(currentElement);
        }
        elementsStack.addLast(currentElement);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = elementsStack.removeLast();
        if (!qName.equals(currentElement.name())) {
            throw new SAXException(String.format("Closing tag %s, but expected %s. Position %d:%d",
                                                 qName,
                                                 currentElement.name(),
                                                 locator.getLineNumber(),
                                                 locator.getColumnNumber()));
        }
        if (!elementsStack.isEmpty()) {
            currentElement = elementsStack.getLast();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = String.valueOf(ch, start, length);
        PomTextNode node = new PomTextNode(text);
        currentElement.children.add(node);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        String whitespace = String.valueOf(ch, start, length);
    }

    @Override
    public void comment(char[] ch, int start, int length) throws SAXException {
        String commentText = String.valueOf(ch, start, length);
        currentElement.children.add(new PomComment(commentText));
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

    @Override
    public void endDocument() throws SAXException {
        if (!elementsStack.isEmpty()) {
            throw new SAXException(String.format("Missing closing tag of %s", elementsStack.getLast().name()));
        }
    }

    public PomElement finish() {
        return this.currentElement;
    }
}
