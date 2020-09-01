package me.piomar.pompon;

import java.util.List;

public class PomPlugins implements Orderable {

    private final PomXmlElement xmlElement;
    private final List<PomSection<PomPlugin>> sections;

    public PomPlugins(PomXmlElement xmlElement, List<PomSection<PomPlugin>> sections) {
        this.xmlElement = xmlElement;
        this.sections = sections;
    }

    public static PomPlugins create(PomXmlElement xmlElement) {
        List<PomSection<PomPlugin>> sections = PomXmlUtils.extractSections(xmlElement, PomPlugins::toPlugin);
        return new PomPlugins(xmlElement, sections);
    }

    static PomPlugin toPlugin(PomXmlElement element) {
        return ImmutablePomPlugin.builder()
                                 .groupId(PomXmlUtils.readChildText(element, "groupId", true))
                                 .artifactId(PomXmlUtils.readChildText(element, "artifactId", true))
                                 .version(PomXmlUtils.readChildText(element, "version", false))
                                 .build();
    }

    @Override
    public List<String> getOrderViolations() {
        return OrderChecker.check(sections);
    }

}
