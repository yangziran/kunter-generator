package cn.kunter.common.generator.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 类名称：文件的共通操作类 FileUtil
 * 内容摘要：针对于文件的操作
 * @author 阳自然
 * @version 1.0 2012-11-15
 */
public class FileUtil {

    /**
     * 文本内容写入
     * @param filePath 文件路径
     * @param content 文件内容
     * @author 阳自然
     */
    @SuppressWarnings({ "resource", "static-access" })
    public static void writeFile(String filePath, String content) throws Exception {

        // 获取到目标文件对象
        File targetFile = new File(filePath);
        targetFile.delete();
        // 创建目标文件路径
        targetFile.getParentFile().mkdirs();

        // 声明管道并获取管道 在try()申明并创建资源，不需要显示的去关闭，数据流会在 try 执行完毕后自动被关闭
        try (FileChannel fcout = new RandomAccessFile(targetFile, "rws").getChannel()) {
            // 获取到字节缓冲区
            ByteBuffer wBuffer = ByteBuffer.allocateDirect(100);
            // 执行文件写入
            fcout.write(wBuffer.wrap(content.getBytes()), fcout.size());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
