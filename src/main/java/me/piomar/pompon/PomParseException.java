package me.piomar.pompon;

public class PomParseException extends RuntimeException {

    public PomParseException(String message, PomXmlLocation location) {
        super(createMessage(message, location));
    }

    private static String createMessage(String message, PomXmlLocation location) {
        return message + ": " + location.toString();
    }
}
