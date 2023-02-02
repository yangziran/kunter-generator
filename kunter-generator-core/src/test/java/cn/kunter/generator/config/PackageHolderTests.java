package cn.kunter.generator.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class PackageHolderTests {

    private static final String tableName = "sys_login";

    @Test
    void getPackages() {
        var packages = PackageHolder.getPackages(tableName);
        assertNotNull(packages);
        log.info(packages.toString());
    }

    @Test
    void getEntityPackage() {
        var packages = PackageHolder.getEntityPackage(tableName);
        assertNotNull(packages);
        log.info(packages);
    }

    @Test
    void getDtoPackage() {
        var packages = PackageHolder.getDtoPackage(tableName);
        assertNotNull(packages);
        log.info(packages);
    }

    @Test
    void getVoPackage() {
        var packages = PackageHolder.getVoPackage(tableName);
        assertNotNull(packages);
        log.info(packages);
    }

    @Test
    void getDaoPackage() {
        var packages = PackageHolder.getDaoPackage(tableName);
        assertNotNull(packages);
        log.info(packages);
    }

    @Test
    void getServicePackage() {
        var packages = PackageHolder.getServicePackage(tableName);
        assertNotNull(packages);
        log.info(packages);
    }

    @Test
    void getServiceImplPackage() {
        var packages = PackageHolder.getServiceImplPackage(tableName);
        assertNotNull(packages);
        log.info(packages);
    }

}
