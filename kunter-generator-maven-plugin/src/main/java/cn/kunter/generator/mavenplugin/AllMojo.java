package cn.kunter.generator.mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Kunter Generator All Mojo
 * @author nature
 * @version 1.0 2021/7/19
 */
@Mojo(name = "all")
public class AllMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Kunter Generator All Mojo");
    }

}
