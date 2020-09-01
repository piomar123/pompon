package me.piomar.pompon;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
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
    File pomFile;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            PomParser parser = new PomParser();
            PomXmlElement root = parser.parse(pomFile);
            PomStructure pom = PomStructure.parse(root);
            List<String> disorders = pom.getOrderViolations();
            if (disorders.isEmpty()) {
                getLog().info(String.format("POM %s is ordered", pomFile));
                return;
            }
            throw new MojoFailureException(String.format("POM %s is not ordered:%n%s", pomFile, String.join("\n", disorders)));

        } catch (MojoFailureException e) {
            throw e;
        } catch (IOException e) {
            throw new MojoExecutionException("Error reading POM file " + pomFile, e);
        } catch (Exception e) {
            throw new MojoFailureException("Error parsing POM file " + pomFile, e);
        }

    }
}
