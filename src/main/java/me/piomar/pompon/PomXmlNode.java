package me.piomar.pompon;

/**
 * Abstraction over various XML item types like element, comment or text entry. See subclasses.
 */
public abstract class PomXmlNode {

    private final PomXmlLocation location;

    protected PomXmlNode(PomXmlLocation location) {
        this.location = location;
    }

    public abstract PomXmlNodeType type();

    public PomXmlLocation sourceLocation() {
        return location;
    }

    // public String deepToString();
}
