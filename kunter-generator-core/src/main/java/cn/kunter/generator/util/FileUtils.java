package cn.kunter.generator.util;

import cn.kunter.generator.exception.CodeGenerationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件工具类
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    /**
     * 加载Excel格式的数据字典，兼容xls和xlsx以及xlsm格式文件
     *
     * @param filePath 文件全路径
     * @return Workbook Excel文件操作对象
     * @throws CodeGenerationException 读取异常
     */
    public static Workbook getWorkbook(String filePath) throws CodeGenerationException {

        if (StringUtils.isBlank(filePath)) {
            throw new CodeGenerationException("文件路径为空");
        }

        var file = new File(filePath);
        if (!file.exists()) {
            throw new CodeGenerationException("文件不存在: " + filePath);
        }

        try (var inputStream = new FileInputStream(file)) {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new CodeGenerationException("文件读取错误", e);
        }
    }

    /**
     * 写文件
     *
     * @param fileName 文件路径
     * @param content 文件内容
     * @throws CodeGenerationException 生成异常
     */
    public static void writeFile(String fileName, String content) throws CodeGenerationException {
        writeFile(fileName, content, true);
    }

    /**
     * 写文件
     *
     * @param fileName 文件路径
     * @param content 文件内容
     * @param override 是否覆盖
     * @throws CodeGenerationException 生成异常
     */
    public static void writeFile(String fileName, String content, boolean override) throws CodeGenerationException {

        var targetFile = new File(fileName);
        if (targetFile.exists() && !override) {
            return;
        }

        var path = targetFile.getParentFile();
        if (!path.exists()) {
            path.mkdirs();
        }

        try {
            Files.writeString(Paths.get(fileName), content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new CodeGenerationException("文件写入失败", e);
        }
    }

}
