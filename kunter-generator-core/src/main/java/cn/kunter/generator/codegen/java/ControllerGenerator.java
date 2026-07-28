package cn.kunter.generator.codegen.java;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.config.Context;
import cn.kunter.generator.config.PackageHolder;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.util.OutputUtils;
import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * Controller生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class ControllerGenerator implements Generator {

    @Override
    public void maker(Table table) throws CodeGenerationException {
        String javaName = table.getJavaName();
        String controllerName = javaName + "Controller";
        String serviceName = javaName + "Service";
        String eoName = javaName + "Eo";

        String controllerPackage = PackageHolder.getControllerPackage(table.getTableName());
        
        String servicePackage = PackageHolder.getServicePackage(table.getTableName());
        String entityPackage = PackageHolder.getEntityPackage(table.getTableName());

        StringBuilder builder = new StringBuilder();

        // 包声明
        builder.append("package ").append(controllerPackage).append(";");
        OutputUtils.newLine(builder, 2);

        // 导入
        builder.append("import ").append(servicePackage).append(".").append(serviceName).append(";");
        OutputUtils.newLine(builder);
        builder.append("import ").append(entityPackage).append(".").append(eoName).append(";");
        OutputUtils.newLine(builder);
        builder.append("import org.springframework.beans.factory.annotation.Autowired;");
        OutputUtils.newLine(builder);
        builder.append("import org.springframework.web.bind.annotation.*;");
        OutputUtils.newLine(builder);
        builder.append("import java.util.List;");
        OutputUtils.newLine(builder, 2);

        boolean swaggerEnable = "true".equalsIgnoreCase(Context.getProperty("swagger.enable"));
        boolean isRestful = !"false".equalsIgnoreCase(Context.getProperty("controller.restful")); // 默认 true
        boolean allowGet = !"false".equalsIgnoreCase(Context.getProperty("controller.allowGet")); // 默认 true
        if (swaggerEnable) {
            builder.append("import io.swagger.annotations.Api;");
            OutputUtils.newLine(builder);
            builder.append("import io.swagger.annotations.ApiOperation;");
            OutputUtils.newLine(builder, 2);
        }

        // 类注释
        builder.append("/**");
        OutputUtils.newLine(builder);
        String tableRemark = StringUtils.isBlank(table.getRemarks()) ? javaName : table.getRemarks();
        builder.append(" * ").append(tableRemark).append(" Controller");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);

        // 注解
        if (swaggerEnable) {
            builder.append("@Api(tags = \"").append(tableRemark).append("\")");
            OutputUtils.newLine(builder);
        }
        builder.append("@RestController");
        OutputUtils.newLine(builder);
        
        // 路由路径
        String requestPath = "/" + StringUtils.convertTableNameToParameter(javaName, null, false);
        builder.append("@RequestMapping(\"").append(requestPath).append("\")");
        OutputUtils.newLine(builder);

        // 类定义
        builder.append("public class ").append(controllerName).append(" {");
        OutputUtils.newLine(builder, 2);

        // Service 注入
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Autowired");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        String serviceParamName = StringUtils.uncapitalize(serviceName);
        builder.append("private ").append(serviceName).append(" ").append(serviceParamName).append(";");
        OutputUtils.newLine(builder, 2);

        // POST 新增
        if (swaggerEnable) {
            OutputUtils.javaIndent(builder, 1);
            builder.append("@ApiOperation(\"新增数据\")");
            OutputUtils.newLine(builder);
        }
        OutputUtils.javaIndent(builder, 1);
        if (isRestful) {
            builder.append("@PostMapping");
        } else {
            builder.append("@PostMapping(\"/create\")");
        }
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public ").append(eoName).append(" create(@RequestBody ").append(eoName).append(" entity) {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append(serviceParamName).append(".insert(entity);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("return entity;");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        // DELETE 删除
        if (swaggerEnable) {
            OutputUtils.javaIndent(builder, 1);
            builder.append("@ApiOperation(\"删除数据\")");
            OutputUtils.newLine(builder);
        }
        OutputUtils.javaIndent(builder, 1);
        if (isRestful) {
            builder.append("@DeleteMapping(\"/{id}\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("public int delete(@PathVariable(\"id\") Long id) {");
        } else {
            builder.append("@PostMapping(\"/delete\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("public int delete(@RequestParam(\"id\") Long id) {");
        }
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("return ").append(serviceParamName).append(".deleteByPrimaryKey(id);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        // PUT 修改
        if (swaggerEnable) {
            OutputUtils.javaIndent(builder, 1);
            builder.append("@ApiOperation(\"修改数据\")");
            OutputUtils.newLine(builder);
        }
        OutputUtils.javaIndent(builder, 1);
        if (isRestful) {
            builder.append("@PutMapping");
        } else {
            builder.append("@PostMapping(\"/update\")");
        }
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public ").append(eoName).append(" update(@RequestBody ").append(eoName).append(" entity) {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append(serviceParamName).append(".updateSelective(entity);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("return entity;");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        // GET/POST 单查
        if (swaggerEnable) {
            OutputUtils.javaIndent(builder, 1);
            builder.append("@ApiOperation(\"查询单条\")");
            OutputUtils.newLine(builder);
        }
        OutputUtils.javaIndent(builder, 1);
        if (isRestful) {
            builder.append("@GetMapping(\"/{id}\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("public ").append(eoName).append(" get(@PathVariable(\"id\") Long id) {");
        } else {
            if (allowGet) {
                builder.append("@GetMapping(\"/get\")");
            } else {
                builder.append("@PostMapping(\"/get\")");
            }
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("public ").append(eoName).append(" get(@RequestParam(\"id\") Long id) {");
        }
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("return ").append(serviceParamName).append(".selectByPrimaryKey(id);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        // GET/POST 列表
        if (swaggerEnable) {
            OutputUtils.javaIndent(builder, 1);
            builder.append("@ApiOperation(\"查询全列表\")");
            OutputUtils.newLine(builder);
        }
        OutputUtils.javaIndent(builder, 1);
        if (isRestful) {
            builder.append("@GetMapping(\"/list\")");
        } else {
            if (allowGet) {
                builder.append("@GetMapping(\"/list\")");
            } else {
                builder.append("@PostMapping(\"/list\")");
            }
        }
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public List<").append(eoName).append("> list() {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("return ").append(serviceParamName).append(".selectAll();");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        builder.append("}");
        OutputUtils.newLine(builder);

        // 输出文件
        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + controllerPackage.replaceAll("\\.", "/") + "/" + controllerName + ".java";
        boolean override = "true".equalsIgnoreCase(Context.getProperty("file.override"));
        FileUtils.writeFile(filePath, builder.toString(), override);
        log.info("Controller生成完毕: {}", filePath);
    }
}
