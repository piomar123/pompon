package me.piomar.pompon;

import java.util.List;

public class PomProperties implements Orderable {

    private final PomXmlElement xmlElement;
    private final List<PomSection<PomProperty>> sections;

    private PomProperties(PomXmlElement xmlElement, List<PomSection<PomProperty>> sections) {
        this.xmlElement = xmlElement;
        this.sections = sections;
    }

    public static PomProperties create(PomXmlElement xmlElement) {
        List<PomSection<PomProperty>> sections = PomXmlUtils.extractSections(xmlElement, PomProperties::convertElement);
        return new PomProperties(xmlElement, sections);
    }

    static PomProperty convertElement(PomXmlElement element) {
        return ImmutablePomProperty.builder()
                                   .value(element.name())
                                   .build();
    }

    @Override
    public List<String> getOrderViolations() {
        return OrderChecker.check(sections);
    }
}
