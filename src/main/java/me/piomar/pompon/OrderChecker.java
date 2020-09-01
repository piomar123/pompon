package me.piomar.pompon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class OrderChecker {

    private OrderChecker() {
    }

    public static <T extends Comparable<T>> List<String> check(List<PomSection<T>> sections) {
        List<String> violations = new ArrayList<>();
        for (PomSection<T> section : sections) {
            ImmutableSet<Entry<T, PomXmlElement>> actualOrder = section.entries().entrySet();
            List<Entry<T, PomXmlElement>> expectedOrder = Lists.newArrayList(actualOrder);
            expectedOrder.sort(Entry.comparingByKey());

            Iterator<Entry<T, PomXmlElement>> expectedIterator = expectedOrder.iterator();
            Iterator<Entry<T, PomXmlElement>> actualIterator = actualOrder.iterator();
            while (expectedIterator.hasNext()) {
                Entry<T, PomXmlElement> expected = expectedIterator.next();
                Entry<T, PomXmlElement> actual = actualIterator.next();
                if (expected.getKey().equals(actual.getKey())) {
                    continue;
                }
                violations.add(String.format("Unordered section <!--%s-->: expected %s in place of %s, %s",
                                             section.name(),
                                             expected.getKey(),
                                             actual.getKey(),
                                             actual.getValue().sourceLocation()));
            }
        }
        return violations;
    }
}
