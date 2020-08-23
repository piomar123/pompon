package me.piomar.pompon;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.Map;
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
        Map<String, PomXmlElement> elementByName = PomXmlUtils.childrenMap(xml);
        PomXmlElement xmlProperties = elementByName.get("properties");
        PomProperties pomProperties = PomProperties.create(xmlProperties);
        Optional<String> unordered = pomProperties.isUnordered();
        PomDependencies.create(elementByName.get("dependencies")).isUnordered();

        xml.getChildByName("dependencyManagement")
           .flatMap(e -> e.getChildByName("dependencies"))
           .ifPresent(deps -> PomDependencies.create(deps).isUnordered());

        // then
        then(xml).toString();
    }

}