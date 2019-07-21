package me.piomar.pompon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PomParser {

    public PomParser() {
    }

    public PomElement parse(InputStream inputStream) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PomSAXHandler pomHandler = new PomSAXHandler();
            saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", pomHandler);
            Reader reader;
            reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            InputSource source = new InputSource(reader);
            source.setEncoding(StandardCharsets.UTF_8.name());
            saxParser.parse(source, pomHandler);
            return pomHandler.finish();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException("Unable to parse input", e);
        }
    }

    public PomElement parse(File pomFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(pomFile)) {
            return parse(inputStream);
        }
    }
}
