package cn.kunter.generator.config;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Context 测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
class ContextTests {

    @Test
    void testGetProperty() {
        Properties properties = Context.getProperties();
        assertNotNull(properties);
        
        try {
            File tempFile = File.createTempFile("generatorConfig-test", ".properties");
            tempFile.deleteOnExit();
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("test.key=test_value\n");
                writer.write("sourceType=excel\n");
            }
            
            Context.loadFromFile(tempFile);
            
            assertEquals("test_value", Context.getProperty("test.key"));
            assertEquals("excel", Context.getProperty("sourceType"));
            assertEquals("default", Context.getProperty("non_exist_key", "default"));
            assertNull(Context.getProperty("non_exist_key"));
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
