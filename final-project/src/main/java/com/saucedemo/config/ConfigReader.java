package com.saucedemo.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads configuration from config.properties file.
 * Singleton pattern to avoid loading file multiple times.
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;

    private static final String CONFIG_PATH =
            "src/test/resources/config.properties";

    private ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            log.info("Configuration loaded from: {}", CONFIG_PATH);
        } catch (IOException e) {
            log.error("Failed to load config.properties: {}", e.getMessage());
            throw new RuntimeException("Cannot load configuration file!", e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getBaseUrl()            { return get("base.url"); }
    public String getBrowser()            { return get("browser"); }
    public int    getImplicitWait()       { return getInt("implicit.wait"); }
    public int    getExplicitWait()       { return getInt("explicit.wait"); }
    public int    getPageLoadTimeout()    { return getInt("page.load.timeout"); }
    public boolean isHeadless()           { return getBool("headless"); }
    public boolean isScreenshotOnFail()   { return getBool("screenshot.on.failure"); }
    public String getReportsDir()         { return get("reports.dir"); }
    public String getValidUsername()      { return get("valid.username"); }
    public String getValidPassword()      { return get("valid.password"); }
    public String getLockedUsername()     { return get("locked.username"); }

    // ---- helpers ----
    private String  get(String key)     { return properties.getProperty(key); }
    private int     getInt(String key)  { return Integer.parseInt(get(key)); }
    private boolean getBool(String key) { return Boolean.parseBoolean(get(key)); }
}
