package me.piomar.pompon;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class PomParserTest {

    @Test
    public void load_and_parse() {
        // given
        InputStream inputPomStream = PomParserTest.class.getClassLoader().getResourceAsStream("ordered.xml");
        PomParser parser = new PomParser();

        // when
        PomXmlElement xml = parser.parse(inputPomStream);
        PomXmlElement xmlProperties = findPropertiesElement(xml.children);
        PomProperties pomProperties = PomProperties.create(xmlProperties);
        Optional<String> unordered = pomProperties.isUnordered();

        // then
        then(xml).toString();
    }

    private PomXmlElement findPropertiesElement(Collection<PomXmlNode> elements) {
        for (PomXmlNode node : elements) {
            if (node.type() == PomXmlNodeType.XmlElement) {
                PomXmlElement element = (PomXmlElement) node;
                if (element.name().equals("properties")) {
                    return element;
                }
            }
        }
        return null;
    }

}