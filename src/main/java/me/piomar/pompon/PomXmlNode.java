package me.piomar.pompon;

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
