package cn.kunter.generator.util;

import cn.kunter.generator.exception.GeneratorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件操作工具类
 * @author yangziran
 * @version 1.0 2021/12/20
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    /**
     * 加载Excel格式的数据字典，兼容xls和xlsx以及xlsm格式文件
     * @param filePath 文件全路径
     * @return Workbook Excel文件操作对象
     * @throws GeneratorException
     */
    public static Workbook getWorkbook(String filePath) throws GeneratorException {

        if (StringUtils.isBlank(filePath)) {
            log.error("文件路径为空");
            throw new GeneratorException("文件路径为空");
        }

        var path = Paths.get(filePath);
        if (!Files.exists(path)) {
            log.error("文件为空");
            throw new GeneratorException("文件为空");
        }

        try (var inputStream = Files.newInputStream(path)) {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            log.error("文件读取错误", e);
            throw new GeneratorException("文件读取错误", e);
        }
    }

    /**
     * 文本内容写入
     * @param fileName 文件名称（包含全路径）
     * @param content 文件内容
     * @throws GeneratorException
     */
    public static void writeFile(String fileName, String content) throws GeneratorException {

        var path = Paths.get(fileName);
        log.info(path.toString());
        try {
            // 清理旧文件
            Files.deleteIfExists(path);
            // 创建目标文件目录
            Files.createDirectories(path.getParent());
            // 写入文件
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            log.info(e.getMessage(), e);
            throw new GeneratorException("文件写入失败");
        }
    }

}
