package cn.kunter.generator.util;

import cn.kunter.generator.exception.GeneratorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
