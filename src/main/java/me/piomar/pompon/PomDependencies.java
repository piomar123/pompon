package me.piomar.pompon;

import java.util.List;

public class PomDependencies implements Orderable {

    private static final String DEFAULT_SCOPE = "compile";
    private final PomXmlElement dependenciesElement;
    private final List<PomSection<PomDependency>> sections;

    public PomDependencies(PomXmlElement dependenciesElement, List<PomSection<PomDependency>> sections) {
        this.dependenciesElement = dependenciesElement;
        this.sections = sections;
    }

    public static PomDependencies create(PomXmlElement dependenciesElement) {
        List<PomSection<PomDependency>> sections = PomXmlUtils.extractSections(dependenciesElement, PomDependencies::toDependency);
        return new PomDependencies(dependenciesElement, sections);
    }

    static PomDependency toDependency(PomXmlElement xmlElement) {
        return ImmutablePomDependency.builder()
                                     .scope(xmlElement.getChildByName("scope").map(PomXmlElement::innerText).orElse(DEFAULT_SCOPE))
                                     .groupId(PomXmlUtils.readChildText(xmlElement, "groupId", true))
                                     .artifactId(PomXmlUtils.readChildText(xmlElement, "artifactId", true))
                                     .build();
    }

    @Override
    public List<String> getOrderViolations() {
        return OrderChecker.check(sections);
    }

}
