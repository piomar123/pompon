package me.piomar.pompon;

public class PomParsingUtils {

    public static RuntimeException missingValueException(String elementName, PomXmlElement parentElement) {
        return new IllegalStateException(String.format("Missing %s in %s element, %s",
                                                       elementName,
                                                       parentElement.name(),
                                                       parentElement.sourceLocation()));
    }
}
