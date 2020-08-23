package me.piomar.pompon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public String innerText() {
        StringBuilder sb = new StringBuilder();
        for (PomXmlNode child : children) {
            if (child.type() == PomXmlNodeType.XmlTextNode) {
                sb.append(((PomXmlTextNode) child).text());
            }
        }
        return sb.toString();
    }

    public Optional<PomXmlElement> getChildByName(String child) {
        return children
            .stream()
            .filter(node -> node.type() == PomXmlNodeType.XmlElement)
            .map(element -> (PomXmlElement) element)
            .filter(e -> e.name().equals(child))
            .findFirst();
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
