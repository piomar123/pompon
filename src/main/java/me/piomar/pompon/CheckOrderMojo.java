package me.piomar.pompon;

import java.io.File;

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
        try {
            PomParser parser = new PomParser();
            PomElement root = parser.parse(pomFile);
            getLog().info(root.toString());

        } catch (Exception e) {
            throw new MojoExecutionException("Error reading POM file " + pomFile, e);
        }

    }
}
