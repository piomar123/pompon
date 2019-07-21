package me.piomar.pompon;

public class PomComment extends PomNode {

    private final String commentText;

    public PomComment(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return String.format("<!--%s-->", commentText);
    }
}
