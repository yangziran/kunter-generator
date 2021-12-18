package cn.kunter.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class StringUtilsTests {

    @Test
    void convertTableNameToClass() {
        var tableName = "sys_login";
        var className = StringUtils.convertTableNameToClass(tableName, "_", false);
        assertNotNull(className);
        log.info("{}", className);

        className = StringUtils.convertTableNameToClass(tableName, "_", null);
        assertNotNull(className);
        log.info("{}", className);

        className = StringUtils.convertTableNameToClass(tableName, "_", true);
        assertNotNull(className);
        log.info("{}", className);

        className = StringUtils.convertTableNameToClass(tableName, null, false);
        assertNotNull(className);
        log.info("{}", className);

        className = StringUtils.convertTableNameToClass(tableName, null, true);
        assertNotNull(className);
        log.info("{}", className);
    }

    @Test
    void convertTableNameToParameter() {
        var tableName = "sys_login";
        var parameterName = StringUtils.convertTableNameToParameter(tableName, "_", false);
        assertNotNull(parameterName);
        log.info("{}", parameterName);

        parameterName = StringUtils.convertTableNameToParameter(tableName, "_", true);
        assertNotNull(parameterName);
        log.info("{}", parameterName);

        parameterName = StringUtils.convertTableNameToParameter(tableName, null, false);
        assertNotNull(parameterName);
        log.info("{}", parameterName);

        parameterName = StringUtils.convertTableNameToParameter(tableName, null, true);
        assertNotNull(parameterName);
        log.info("{}", parameterName);
    }

    @Test
    void convertFieldToParameter() {
        var fieldName = "login_id";
        var parameterName = StringUtils.convertFieldToParameter(fieldName, "_");
        assertNotNull(parameterName);
        log.info("{}", parameterName);

        parameterName = StringUtils.convertFieldToParameter(fieldName, null);
        assertNotNull(parameterName);
        log.info("{}", parameterName);
    }

}
