package me.piomar.pompon;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.xml.sax.InputSource;

/**
 * Check if POM is ordered as in template.
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE, requiresProject = true, threadSafe = true)
public class CheckOrderMojo extends AbstractMojo {

    /**
     * Location of the POM.
     */
    @Parameter(property = "project.file", required = true)
    private File pomFile;

    public void execute() throws MojoExecutionException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PomSAXHandler pomHandler = new PomSAXHandler(getLog());
            saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", pomHandler);
            Reader reader;
            try (InputStream inputStream = new FileInputStream(pomFile)) {
                reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                InputSource source = new InputSource(reader);
                source.setEncoding(StandardCharsets.UTF_8.name());
                saxParser.parse(source, pomHandler);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Error reading POM file " + pomFile, e);
        }

    }
}
