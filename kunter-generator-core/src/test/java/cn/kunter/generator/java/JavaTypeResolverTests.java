package cn.kunter.generator.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JavaTypeResolver 测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
class JavaTypeResolverTests {

    @Test
    void getJdbcType() {
        assertEquals("INTEGER", JavaTypeResolver.getJdbcType(Types.INTEGER));
        assertEquals("VARCHAR", JavaTypeResolver.getJdbcType(Types.VARCHAR));
        assertEquals("BIGINT", JavaTypeResolver.getJdbcType(Types.BIGINT));
        assertEquals("TIMESTAMP", JavaTypeResolver.getJdbcType(Types.TIMESTAMP));
        assertEquals("OTHER", JavaTypeResolver.getJdbcType(9999));
    }

    @Test
    void testGetJdbcType() {
        assertEquals(Types.INTEGER, JavaTypeResolver.getJdbcType("INTEGER"));
        assertEquals(Types.VARCHAR, JavaTypeResolver.getJdbcType("VARCHAR"));
        assertEquals(Types.DECIMAL, JavaTypeResolver.getJdbcType("DECIMAL"));
        assertEquals(Types.TIMESTAMP, JavaTypeResolver.getJdbcType("TIMESTAMP"));
        assertEquals(Types.OTHER, JavaTypeResolver.getJdbcType("UNKNOWN_TYPE"));
    }

    @Test
    void getJavaType() {
        assertEquals(Integer.class.getName(), JavaTypeResolver.getJavaType(Types.INTEGER));
        assertEquals(String.class.getName(), JavaTypeResolver.getJavaType(Types.VARCHAR));
        assertEquals(Long.class.getName(), JavaTypeResolver.getJavaType(Types.BIGINT));
        assertEquals(java.util.Date.class.getName(), JavaTypeResolver.getJavaType(Types.TIMESTAMP));
        assertEquals(Object.class.getName(), JavaTypeResolver.getJavaType(9999));
    }

    @Test
    void testGetJavaType() {
        assertEquals(Integer.class.getName(), JavaTypeResolver.getJavaType("INTEGER"));
        assertEquals(String.class.getName(), JavaTypeResolver.getJavaType("VARCHAR"));
        assertEquals(java.math.BigDecimal.class.getName(), JavaTypeResolver.getJavaType("DECIMAL"));
        assertEquals(java.util.Date.class.getName(), JavaTypeResolver.getJavaType("TIMESTAMP"));
        assertEquals(Object.class.getName(), JavaTypeResolver.getJavaType("UNKNOWN_TYPE"));
    }

}
