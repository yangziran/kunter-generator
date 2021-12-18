package cn.kunter.generator.config;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * JDBC连接配置
 * @author nature
 * @version 1.0 2021/7/20
 */
@Data
@Builder
public class JdbcConnectionConfig extends PropertyHolder {

    private String driverClass;
    private String connectionUrl;
    private String userId;
    private String password;

    /**
     * 验证参数
     * 允许userId和password为空，所以只需要验证driverClass和connectionUrl不为空
     * @param errors 错误信息集合
     */
    public void validate(List<String> errors) {
        if (StringUtils.isBlank(driverClass)) {
            errors.add(MessageFormat.format("JDBC Driver必须指定", new Object[]{}));
        }
        if (StringUtils.isBlank(connectionUrl)) {
            errors.add(MessageFormat.format("JDBC Connection URL必须指定", new Object[]{}));
        }
    }

}
