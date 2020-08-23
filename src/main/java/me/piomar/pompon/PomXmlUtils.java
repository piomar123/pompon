package me.piomar.pompon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;

public class PomXmlUtils {

    private PomXmlUtils() {
    }

    public static Map<String, PomXmlElement> childrenMap(PomXmlElement xml) {
        return xml.children
            .stream()
            .filter(node -> node.type() == PomXmlNodeType.XmlElement)
            .map(element -> (PomXmlElement) element)
            .collect(ImmutableMap.toImmutableMap(PomXmlElement::name, e -> e));
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
}
