package me.piomar.pompon;

public class PomTextNode extends PomNode {

    private final String text;

    public PomTextNode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
