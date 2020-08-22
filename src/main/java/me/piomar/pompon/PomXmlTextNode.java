package me.piomar.pompon;

public class PomXmlTextNode extends PomXmlNode {

    private final String text;

    public PomXmlTextNode(String text, PomXmlLocation location) {
        super(location);
        this.text = text;
    }

    public String text() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public PomXmlNodeType type() {
        return PomXmlNodeType.XmlTextNode;
    }
}
