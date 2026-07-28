package cn.kunter.generator.mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * 打印插件帮助信息
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Mojo(name = "help", requiresDependencyResolution = ResolutionScope.TEST)
public class HelpMojo extends AbstractMojo {

    @Override
    public void execute() {
        getLog().info("------------------------------------------------------------------------");
        getLog().info("Kunter Generator Maven Plugin Help");
        getLog().info("------------------------------------------------------------------------");
        getLog().info("This plugin is used to generate code based on database or excel tables.");
        getLog().info("");
        getLog().info("Available goals:");
        getLog().info("  kunter-generator:help  - Display this help information.");
        getLog().info("  kunter-generator:all   - Generate all code (Entity, Dao, Service, etc.).");
        getLog().info("");
        getLog().info("Usage:");
        getLog().info("  mvn kunter-generator:all");
        getLog().info("------------------------------------------------------------------------");
    }

}
