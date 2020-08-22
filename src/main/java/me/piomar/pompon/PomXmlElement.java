package me.piomar.pompon;

import java.util.ArrayList;
import java.util.List;

public class PomXmlElement extends PomXmlNode {

    private final String name;
    final List<PomXmlNode> children = new ArrayList<>();

    public PomXmlElement(String name, PomXmlLocation location) {
        super(location);
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("<%s>", name);
    }

    @Override
    public PomXmlNodeType type() {
        return PomXmlNodeType.XmlElement;
    }
}
