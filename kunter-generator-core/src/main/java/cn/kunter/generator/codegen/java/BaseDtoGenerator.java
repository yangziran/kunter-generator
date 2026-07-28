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
 * BaseDto 生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class BaseDtoGenerator implements Generator {

    @Override
    public void maker(List<Table> tables) throws CodeGenerationException {
        var isGenerateBase = Context.getProperty("generate.base.dto", "true");
        if (!"true".equalsIgnoreCase(isGenerateBase)) {
            return;
        }

        String baseDtoPackage = PackageHolder.getBaseDtoPackage();
        String dtoName = "BaseDto";

        StringBuilder builder = new StringBuilder();
        
        builder.append("package ").append(baseDtoPackage).append(";");
        OutputUtils.newLine(builder, 2);

        builder.append("import lombok.Data;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.NoArgsConstructor;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.experimental.SuperBuilder;");
        OutputUtils.newLine(builder);
        builder.append("import java.io.Serializable;");
        OutputUtils.newLine(builder, 2);

        builder.append("/**");
        OutputUtils.newLine(builder);
        builder.append(" * BaseDto 响应基类");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);

        builder.append("@Data");
        OutputUtils.newLine(builder);
        builder.append("@NoArgsConstructor");
        OutputUtils.newLine(builder);
        builder.append("@SuperBuilder");
        OutputUtils.newLine(builder);

        builder.append("public class ").append(dtoName).append(" implements Serializable {");
        OutputUtils.newLine(builder, 2);
        
        builder.append("}");
        OutputUtils.newLine(builder);

        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + baseDtoPackage.replaceAll("\\.", "/") + "/" + dtoName + ".java";
        FileUtils.writeFile(filePath, builder.toString());
        log.info("BaseDto 生成完毕");
    }

}
