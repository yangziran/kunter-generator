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
 * BaseVo 生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class BaseVoGenerator implements Generator {

    @Override
    public void maker(List<Table> tables) throws CodeGenerationException {
        var isGenerateBase = Context.getProperty("generate.base.vo", "true");
        if (!"true".equalsIgnoreCase(isGenerateBase)) {
            return;
        }

        String baseVoPackage = PackageHolder.getBaseVoPackage();
        String voName = "BaseVo";

        StringBuilder builder = new StringBuilder();
        
        builder.append("package ").append(baseVoPackage).append(";");
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
        builder.append(" * BaseVo 请求基类");
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

        builder.append("public class ").append(voName).append(" implements Serializable {");
        OutputUtils.newLine(builder, 2);
        
        builder.append("}");
        OutputUtils.newLine(builder);

        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + baseVoPackage.replaceAll("\\.", "/") + "/" + voName + ".java";
        FileUtils.writeFile(filePath, builder.toString());
        log.info("BaseVo 生成完毕");
    }

}
