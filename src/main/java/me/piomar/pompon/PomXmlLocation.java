package me.piomar.pompon;

import org.xml.sax.Locator;

public class PomXmlLocation {

    private final int line;
    private final int column;

    public PomXmlLocation(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public static PomXmlLocation createLocation(Locator locator) {
        return new PomXmlLocation(locator.getLineNumber(), locator.getColumnNumber());
    }

    public int line() {
        return line;
    }

    public int column() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("location %d:%d", line, column);
    }
}
