package me.piomar.pompon;

import java.util.ArrayList;
import java.util.List;

public class PomElement extends PomNode {

    private final String name;
    final List<PomNode> children = new ArrayList<>();

    public PomElement(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("<%s>", name);
    }
}
