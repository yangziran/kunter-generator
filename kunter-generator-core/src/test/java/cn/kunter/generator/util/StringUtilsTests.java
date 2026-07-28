package cn.kunter.generator.util;

import cn.kunter.generator.config.Context;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * StringUtils 测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
class StringUtilsTests {

    @BeforeEach
    void setUp() {
        Context.getProperties().setProperty("table.prefix.ignore", "sys_,t_");
    }

    @Test
    void convertTableNameToClass() {
        var tableName = "sys_login";
        
        var className = StringUtils.convertTableNameToClass(tableName, "_", false);
        assertEquals("Login", className);

        className = StringUtils.convertTableNameToClass("t_user_info", "_", false);
        assertEquals("UserInfo", className);

        className = StringUtils.convertTableNameToClass("sys_login_log", "_", true);
        assertEquals("LOGINLog", className);

        className = StringUtils.convertTableNameToClass(tableName, null, false);
        assertEquals("Login", className);

        className = StringUtils.convertTableNameToClass("no_prefix_table", "_", false);
        assertEquals("NoPrefixTable", className);

        assertNull(StringUtils.convertTableNameToClass(null, "_", false));
    }

    @Test
    void convertTableNameToParameter() {
        var tableName = "sys_login_log";
        var parameterName = StringUtils.convertTableNameToParameter(tableName, "_", false);
        assertEquals("loginlog", parameterName);

        parameterName = StringUtils.convertTableNameToParameter("t_user_info", "_", true);
        assertEquals("userInfo", parameterName);

        parameterName = StringUtils.convertTableNameToParameter("sys_login_log", null, false);
        assertEquals("login_log", parameterName);

        parameterName = StringUtils.convertTableNameToParameter("sys_login_log", null, true);
        assertEquals("login_log", parameterName);
        
        parameterName = StringUtils.convertTableNameToParameter("no_prefix", "_", false);
        assertEquals("noprefix", parameterName);

        assertNull(StringUtils.convertTableNameToParameter(null, "_", false));
    }

    @Test
    void convertFieldToParameter() {
        var fieldName = "login_id";
        var parameterName = StringUtils.convertFieldToParameter(fieldName, "_");
        assertEquals("loginId", parameterName);

        parameterName = StringUtils.convertFieldToParameter("user_name", "_");
        assertEquals("userName", parameterName);

        parameterName = StringUtils.convertFieldToParameter(fieldName, null);
        assertEquals("login_id", parameterName);

        assertNull(StringUtils.convertFieldToParameter(null, "_"));
    }

}
