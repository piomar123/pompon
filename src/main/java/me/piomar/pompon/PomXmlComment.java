package me.piomar.pompon;

public class PomXmlComment extends PomXmlNode {

    private final String commentText;

    public PomXmlComment(String commentText) {
        this.commentText = commentText;
    }

    public String text() {
        return commentText;
    }

    @Override
    public String toString() {
        return String.format("<!--%s-->", commentText);
    }

    @Override
    public PomXmlNodeType type() {
        return PomXmlNodeType.XmlComment;
    }
}
