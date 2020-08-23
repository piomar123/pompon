package me.piomar.pompon;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSet;

public class PomProperties implements Orderable {

    private final PomXmlElement propertiesElement;
    private final List<PomSection<String>> sections;

    private PomProperties(PomXmlElement propertiesElement, List<PomSection<String>> sections) {
        this.propertiesElement = propertiesElement;
        this.sections = sections;
    }

    public static PomProperties create(PomXmlElement propertiesElement) {
        // TODO distinguish a comment from section - minor as extra section does not break the order
        List<PomSection<String>> sections = PomXmlUtils.extractSections(propertiesElement, PomXmlElement::name);
        return new PomProperties(propertiesElement, sections);
    }

    @Override
    public Optional<String> isUnordered() {
        for (PomSection<String> section : sections) {
            ImmutableSet<Entry<String, PomXmlElement>> actualOrder = section.entries().entrySet();
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

    // @Override
    // public void makeOrder() {
    //     throw new UnsupportedOperationException("nope");
    // }
}
