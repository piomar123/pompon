package me.piomar.pompon;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

class PomParserTest {

    @Test
    public void load_and_parse() {
        // given
        InputStream inputPomStream = PomParserTest.class.getClassLoader().getResourceAsStream("ordered.xml");
        PomParser parser = new PomParser();

        // when
        PomXmlElement xml = parser.parse(inputPomStream);
        PomStructure pom = PomStructure.parse(xml);
        List<String> disorders = pom.getOrderViolations();

        // then
        then(disorders).isEmpty();
    }

}