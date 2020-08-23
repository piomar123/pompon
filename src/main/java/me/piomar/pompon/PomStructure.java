package me.piomar.pompon;

public class PomStructure {

    private final PomProperties properties;

    public PomStructure(PomProperties properties) {
        this.properties = properties;
    }

    public static PomStructure parse(PomXmlNode root) {
        throw new UnsupportedOperationException("nope");
    }
}
