package cn.kunter.generator.codegen.java;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.config.Context;
import cn.kunter.generator.config.PackageHolder;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.util.OutputUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

/**
 * BaseEo 生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class BaseEoGenerator implements Generator {

    @Override
    public void maker(List<Table> tables) throws CodeGenerationException {
        // 判断是否生成
        var isGenerateBase = Context.getProperty("generate.base.eo", "true");
        if (!"true".equalsIgnoreCase(isGenerateBase)) {
            return;
        }

        String baseEoPackage = PackageHolder.getBaseEoPackage();
        String eoName = "BaseEo";

        StringBuilder builder = new StringBuilder();
        
        // 包声明
        builder.append("package ").append(baseEoPackage).append(";");
        OutputUtils.newLine(builder, 2);

        // 导入
        builder.append("import lombok.Data;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.NoArgsConstructor;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.experimental.SuperBuilder;");
        OutputUtils.newLine(builder);
        builder.append("import java.io.Serializable;");
        OutputUtils.newLine(builder, 2);

        // 类注释
        builder.append("/**");
        OutputUtils.newLine(builder);
        builder.append(" * BaseEo 实体基类");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);

        // Lombok 注解
        builder.append("@Data");
        OutputUtils.newLine(builder);
        builder.append("@NoArgsConstructor");
        OutputUtils.newLine(builder);
        builder.append("@SuperBuilder");
        OutputUtils.newLine(builder);

        // 类定义
        builder.append("public class ").append(eoName).append(" implements Serializable {");
        OutputUtils.newLine(builder, 2);
        
        builder.append("}");
        OutputUtils.newLine(builder);

        // 输出文件
        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + baseEoPackage.replaceAll("\\.", "/") + "/" + eoName + ".java";
        FileUtils.writeFile(filePath, builder.toString());
        log.info("BaseEo 生成完毕");
    }

}
