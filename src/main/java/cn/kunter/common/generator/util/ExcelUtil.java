/**
 *
 */
package cn.kunter.common.generator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import cn.kunter.common.generator.config.PropertyHolder;

/**
 * ExcelUtil
 * @author 阳自然
 * @version 1.0 2015年8月31日
 */
public class ExcelUtil {

    /**
     * 获取到Excel操作对象Workbook
     * @return
     * @throws Exception
     * @author 阳自然
     */
    public static Workbook getWorkbook() throws Exception {

        Workbook wb = null;
        // 获取输入流对象
        InputStream is = null;
        try {
            // 获取文件对象
            File file = new File(PropertyHolder.getJDBCProperty("path.dictionary"));
            // 判断文件是否存在
            if (file.exists()) {
                // 获取输入流对象
                is = new FileInputStream(file);
                // 得到工作簿对象 2007及以上版本需要获取 XSSFWorkbook对象，95~2003版本需要获取HSSFWorkbook对象
                wb = WorkbookFactory.create(is);
            }
        } catch (FileNotFoundException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if (wb != null) {
                wb.close();
            }
            // 输入流不为空
            if (is != null) {
                try {
                    // 关闭输入流
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return wb;
    }
}
