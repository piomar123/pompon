package me.piomar.pompon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class PomProperties implements Orderable {

    private final PomXmlElement propertiesElement;
    private final List<Section> sections;

    private PomProperties(PomXmlElement propertiesElement, List<Section> sections) {
        this.propertiesElement = propertiesElement;
        this.sections = sections;
    }

    public static PomProperties create(PomXmlElement propertiesElement) {
        List<Section> sections = new ArrayList<>();
        for (PomXmlNode node : propertiesElement.children) {
            if (node.type() == PomXmlNodeType.XmlComment) {
                String sectionName = ((PomXmlComment) node).text();
                continue;
            }
            if (node.type() == PomXmlNodeType.XmlElement) {
                PomXmlElement element = (PomXmlElement) node;
                String key = element.name();
                // String value = theOnlyChild(element.children);
            }
        }
        return new PomProperties(propertiesElement, sections);
    }

    @Override
    public Optional<String> isUnordered() {
        return Optional.empty();
    }

    @Override
    public void makeOrder() {

    }

    private static class Section {

        String name;
        LinkedHashMap<String, String> properties;
    }
}
