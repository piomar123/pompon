package me.piomar.pompon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PomXmlUtils {

    private PomXmlUtils() {
    }

    static <T> List<PomSection<T>> extractSections(PomXmlElement elementWithSections, Function<PomXmlElement, T> toKey) {
        if (elementWithSections == null) {
            return Collections.emptyList();
        }
        List<PomSection<T>> sections = new ArrayList<>();
        ImmutablePomSection.Builder<T> currentSection = ImmutablePomSection.builder();
        for (PomXmlNode node : elementWithSections.children) {
            if (node.type() == PomXmlNodeType.XmlComment) {
                buildAndAddSection(sections, currentSection);
                PomXmlComment commentNode = (PomXmlComment) node;
                String sectionName = commentNode.text();
                currentSection = ImmutablePomSection.<T>builder().name(sectionName).commentNode(commentNode);
                continue;
            }
            if (node.type() == PomXmlNodeType.XmlElement) {
                PomXmlElement element = (PomXmlElement) node;
                currentSection.putEntries(toKey.apply(element), element);
            }
        }
        buildAndAddSection(sections, currentSection);
        return sections;
    }

    private static <T> void buildAndAddSection(List<PomSection<T>> sections, ImmutablePomSection.Builder<T> sectionBuilder) {
        PomSection<T> section = sectionBuilder.build();
        if (section.entries().isEmpty()) {
            return;
        }
        sections.add(section);
    }

    static String readChildText(PomXmlElement element, String childName, boolean required) {
        Optional<String> childText = element.getChildByName(childName)
                                            .map(PomXmlElement::innerText);
        if (childText.isPresent()) {
            return childText.get();
        }
        if (required) {
            throw PomParsingUtils.missingValueException(childName, element);
        }
        return null;
    }
}
