package me.piomar.pompon;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Check if POM is ordered as in template.
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE, requiresProject = true, threadSafe = true)
public class CheckOrderMojo extends AbstractMojo {

    private static final String EMPTY_LINE_INDICATOR = "<!--\uE000-->";
    private static final Pattern NEW_LINE_REGEX = Pattern.compile("\n");

    /**
     * Location of the POM.
     */
    @Parameter(property = "project.file", required = true)
    private File pomFile;

    public void execute() throws MojoExecutionException {
        final List<String> pomLines;
        try {
            pomLines = Files.readAllLines(pomFile.toPath());
        } catch (IOException exception) {
            throw new MojoExecutionException("Error opening POM file " + pomFile, exception);
        }

        final String preservedXML = pomLines.stream()
                                            .map(this::preserveEmptyLine)
                                            .collect(Collectors.joining("\n"));

        final SAXReader reader = new SAXReader();
        reader.setIgnoreComments(false);

        final Document document;
        try {
            document = reader.read(new StringReader(preservedXML));
        } catch (DocumentException exception) {
            throw new MojoExecutionException("Error reading POM file " + pomFile, exception);
        }
        final Element root = document.getRootElement();
        if (!"project".equals(root.getName())) {
            throw new MojoExecutionException("Given file is not a POM: " + pomFile);
        }

        root.element("dependencies").elements().forEach(element -> getLog().info(element.getName()));

        final OutputFormat format = OutputFormat.createPrettyPrint();
        format.setIndentSize(4);
        format.setLineSeparator("\n");
        final StringWriter writer = new StringWriter();
        final XMLWriter xmlWriter = new XMLWriter(writer, format);

        try {
            xmlWriter.write(root);
        } catch (IOException exception) {
            throw new MojoExecutionException("Error writing POM file " + pomFile, exception);
        }

        final String outputXML = writer.toString();
        final String whitespacedXML = Arrays.stream(NEW_LINE_REGEX.split(outputXML))
                                            .map(this::restoreEmptyLine)
                                            .collect(Collectors.joining("\n"));
        // getLog().info(whitespacedXML);
    }

    private String preserveEmptyLine(final String line) {
        return line.trim().isEmpty() ? EMPTY_LINE_INDICATOR : line;
    }

    private String restoreEmptyLine(final String line) {
        return EMPTY_LINE_INDICATOR.equals(line.trim()) ? "" : line;
    }
}
