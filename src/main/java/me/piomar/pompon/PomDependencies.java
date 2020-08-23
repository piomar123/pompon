package me.piomar.pompon;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSet;

public class PomDependencies implements Orderable {

    private static final String DEFAULT_SCOPE = "compile";
    private final PomXmlElement dependenciesElement;
    private final List<PomSection<PomDependency>> sections;

     // TODO compare with PomProperties and generify the logic

    public PomDependencies(PomXmlElement dependenciesElement, List<PomSection<PomDependency>> sections) {
        this.dependenciesElement = dependenciesElement;
        this.sections = sections;
    }

    public static PomDependencies create(PomXmlElement dependenciesElement) {
        List<PomSection<PomDependency>> sections = PomXmlUtils.extractSections(dependenciesElement, PomDependencies::toDependency);
        return new PomDependencies(dependenciesElement, sections);
    }

    static PomDependency toDependency(PomXmlElement dependencyElement) {
        Map<String, PomXmlElement> childByName = PomXmlUtils.childrenMap(dependencyElement);
        return ImmutablePomDependency.builder()
                                     .scope(Optional.ofNullable(childByName.get("scope")).map(PomXmlElement::innerText).orElse(DEFAULT_SCOPE))
                                     .groupId(childByName.get("groupId").innerText())
                                     .artifactId(childByName.get("artifactId").innerText())
                                     .build();
    }

    @Override
    public Optional<String> isUnordered() {
        for (PomSection<PomDependency> section : sections) {
            ImmutableSet<Entry<PomDependency, PomXmlElement>> actualOrder = section.entries().entrySet();
            SortedSet<Entry<PomDependency, PomXmlElement>> expectedOrder = new TreeSet<>(Entry.comparingByKey());
            expectedOrder.addAll(actualOrder);

            Iterator<Entry<PomDependency, PomXmlElement>> expectedIterator = expectedOrder.iterator();
            Iterator<Entry<PomDependency, PomXmlElement>> actualIterator = actualOrder.iterator();
            while (expectedIterator.hasNext()) {
                Entry<PomDependency, PomXmlElement> expected = expectedIterator.next();
                Entry<PomDependency, PomXmlElement> actual = actualIterator.next();
                if (expected.getKey().equals(actual.getKey())) {
                    continue;
                }
                throw new IllegalStateException(String.format("Unordered section <!--%s-->: expected %s in place of %s, %s",
                                                              section.name(),
                                                              expected.getKey(),
                                                              actual.getKey(),
                                                              actual.getValue().sourceLocation()));
            }
        }
        return Optional.empty();
    }

}
