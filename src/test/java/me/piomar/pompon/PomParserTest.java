package me.piomar.pompon;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

class PomParserTest {

    @Test
    public void load_and_parse() {
        // given
        InputStream inputPomStream = PomParserTest.class.getClassLoader().getResourceAsStream("ordered.xml");
        PomParser parser = new PomParser();

        // when
        PomElement pom = parser.parse(inputPomStream);

        // then
        then(pom).toString();
    }

}