package me.piomar.pompon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSet;

public class PomProperties implements Orderable {

    private final PomXmlElement propertiesElement;
    private final List<PomSection> sections;

    private PomProperties(PomXmlElement propertiesElement, List<PomSection> sections) {
        this.propertiesElement = propertiesElement;
        this.sections = sections;
    }

    public static PomProperties create(PomXmlElement propertiesElement) {
        // TODO distinguish comment from section
        List<PomSection> sections = new ArrayList<>();
        ImmutablePomSection.Builder currentSection = ImmutablePomSection.builder();
        for (PomXmlNode node : propertiesElement.children) {
            if (node.type() == PomXmlNodeType.XmlComment) {
                buildAndAddSection(sections, currentSection);
                PomXmlComment commentNode = (PomXmlComment) node;
                String sectionName = commentNode.text();
                currentSection = ImmutablePomSection.builder().name(sectionName).commentNode(commentNode);
                continue;
            }
            if (node.type() == PomXmlNodeType.XmlElement) {
                PomXmlElement element = (PomXmlElement) node;
                String key = element.name();
                currentSection.putProperties(key, element);
            }
        }
        buildAndAddSection(sections, currentSection);
        return new PomProperties(propertiesElement, sections);
    }

    private static void buildAndAddSection(List<PomSection> sections, ImmutablePomSection.Builder sectionBuilder) {
        PomSection section = sectionBuilder.build();
        if (section.properties().isEmpty()) {
            return;
        }
        sections.add(section);
    }

    @Override
    public Optional<String> isUnordered() {
        for (PomSection section : sections) {
            ImmutableSet<Entry<String, PomXmlElement>> actualOrder = section.properties().entrySet();
            // TODO natural word ordering (foo-bar, foo.bar)
            SortedSet<Entry<String, PomXmlElement>> expectedOrder = new TreeSet<>(Entry.comparingByKey());
            expectedOrder.addAll(actualOrder);

            Iterator<Entry<String, PomXmlElement>> expectedIterator = expectedOrder.iterator();
            Iterator<Entry<String, PomXmlElement>> actualIterator = actualOrder.iterator();
            while (expectedIterator.hasNext()) {
                Entry<String, PomXmlElement> expected = expectedIterator.next();
                Entry<String, PomXmlElement> actual = actualIterator.next();
                if (expected.getKey().equals(actual.getKey())) {
                    continue;
                }
                throw new IllegalStateException(String.format("Unordered section <!--%s-->: expected <%s> in place of <%s>, %s",
                                                              section.name(),
                                                              expected.getKey(),
                                                              actual.getKey(),
                                                              actual.getValue().sourceLocation()));
            }
        }
        return Optional.empty();
    }

    @Override
    public void makeOrder() {
        throw new UnsupportedOperationException("nope");
    }
}
