package me.piomar.pompon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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
        try (BufferedReader reader = new BufferedReader(new FileReader(pomFile))) {
            reader.lines().forEach(line -> getLog().info(line));
        } catch (IOException exception) {
            throw new MojoExecutionException("Error opening POM file " + pomFile, exception);
        }
    }
}
