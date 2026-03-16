package com.saucedemo.utils;

import com.saucedemo.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility to capture screenshots on test failure.
 */
public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "reports/screenshots/";

    private ScreenshotUtils() {}

    /**
     * Captures a screenshot and returns the absolute file path.
     *
     * @param testName name used in the file name
     * @return absolute path to the saved screenshot
     */
    public static String capture(String testName) {
        try {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName  = testName + "_" + timestamp + ".png";
            Path   dir       = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dir);

            File srcFile = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.FILE);

            Path destPath = dir.resolve(fileName);
            Files.copy(srcFile.toPath(), destPath);

            log.info("Screenshot saved: {}", destPath.toAbsolutePath());
            return destPath.toAbsolutePath().toString();

        } catch (IOException e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
            return "";
        }
    }
}
