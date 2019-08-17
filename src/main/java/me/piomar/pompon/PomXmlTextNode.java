package me.piomar.pompon;

public class PomXmlTextNode extends PomXmlNode {

    private final String text;

    public PomXmlTextNode(String text) {
        this.text = text;
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
