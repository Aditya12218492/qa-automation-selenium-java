package com.saucedemo.tests;

import com.saucedemo.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest: sets up and tears down WebDriver for each test method.
 * All test classes extend this.
 */
public abstract class BaseTest {

    protected final Logger log = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
        log.info("========== TEST SETUP COMPLETE ==========");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
        log.info("========== TEST TEARDOWN COMPLETE ==========");
    }
}
