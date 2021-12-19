package cn.kunter.generator.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class JavaTypeResolverTests {

    @Test
    void getJdbcType() {
        var jdbcType = JavaTypeResolver.getJdbcType(Types.INTEGER);
        assertEquals("INTEGER", jdbcType);
        log.info("{}", jdbcType);
    }

    @Test
    void testGetJdbcType() {
        var jdbcType = JavaTypeResolver.getJdbcType("INTEGER");
        assertEquals(Types.INTEGER, jdbcType);
        log.info("{}", jdbcType);
    }

    @Test
    void getJavaType() {
        var javaType = JavaTypeResolver.getJavaType(Types.INTEGER);
        assertEquals(Integer.class.getName(), javaType);
        log.info("{}", javaType);
    }

    @Test
    void testGetJavaType() {
        var javaType = JavaTypeResolver.getJavaType("INTEGER");
        assertEquals(Integer.class.getName(), javaType);
        log.info("{}", javaType);
    }

}
