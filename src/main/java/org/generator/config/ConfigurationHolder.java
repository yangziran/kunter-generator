/**
 * 
 */
package org.generator.config;

import java.text.MessageFormat;
import java.util.List;

import org.generator.util.StringUtility;

/**
 * 数据库连接属性
 * @author yangziran
 * @version 1.0 2014年9月12日
 */
public class ConfigurationHolder extends PropertyHolder {

    private String driverClass;

    private String connectionURL;

    private String userId;

    private String password;

    public ConfigurationHolder() {
        super();
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * 验证driverClass和connectionURL是否为空
     * @param errors 错误信息集合
     * @author yangziran
     */
    public void validate(List<String> errors) {
        if (StringUtility.isEmpty(driverClass)) {
            errors.add(MessageFormat.format("JDBC Driver必须指定", new Object[] {}));
        }

        if (StringUtility.isEmpty(connectionURL)) {
            errors.add(MessageFormat.format("JDBC Connection URL必须指定", new Object[] {}));
        }
    }
}
