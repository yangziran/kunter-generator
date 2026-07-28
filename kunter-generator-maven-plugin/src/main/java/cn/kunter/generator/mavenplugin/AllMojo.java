package cn.kunter.generator.mavenplugin;

import cn.kunter.generator.codegen.GeneratorFactory;
import cn.kunter.generator.datasource.DataSourceFactory;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.config.Context;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

/**
 * Kunter Generator All Mojo
 * @author yangziran
 * @version 1.0 2021/7/19
 */
@Mojo(name = "all")
public class AllMojo extends AbstractMojo {

    @Parameter(property = "kunter.generator.configFile", defaultValue = "${project.basedir}/src/main/resources/generatorConfig.properties")
    private File configurationFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Kunter Generator All Mojo Started...");
        try {
            // 0. 加载外部配置 (如果作为插件运行)
            Context.loadFromFile(configurationFile);

            // 1. 初始化数据源并获取所有表结构
            var dataSource = DataSourceFactory.getDataSource();
            if (dataSource == null) {
                throw new MojoExecutionException("无法初始化数据源，请检查 generatorConfig.properties 配置");
            }
            List<Table> tables = dataSource.getTables();
            if (tables == null || tables.isEmpty()) {
                getLog().warn("未解析到任何表结构，请检查数据库配置或 Excel 模板。");
                return;
            }
            getLog().info("成功解析表结构，共计 " + tables.size() + " 个表。");

            // 2. 调用生成器工厂统一调度代码生成
            GeneratorFactory.executeAll(tables);
            
            getLog().info("Kunter Generator All Mojo Completed Successfully.");
        } catch (Exception e) {
            getLog().error("代码生成失败", e);
            throw new MojoExecutionException("代码生成执行异常", e);
        }
    }

}
