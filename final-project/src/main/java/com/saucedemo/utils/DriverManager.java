package com.saucedemo.utils;

import com.saucedemo.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Thread-safe WebDriver manager using ThreadLocal.
 * Supports Chrome, Firefox, and Edge.
 */
public class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ConfigReader config = ConfigReader.getInstance();

    private DriverManager() {}

    /** Initialize WebDriver for the current thread. */
    public static void initDriver() {
        String browser = config.getBrowser().toLowerCase().trim();
        boolean headless = config.isHeadless();
        WebDriver driver;

        log.info("Launching browser: {} | headless: {}", browser, headless);

        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOpts = new FirefoxOptions();
                if (headless) ffOpts.addArguments("--headless");
                driver = new FirefoxDriver(ffOpts);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOpts = new EdgeOptions();
                if (headless) edgeOpts.addArguments("--headless");
                driver = new EdgeDriver(edgeOpts);
            }
            default -> {                                    // chrome
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOpts = new ChromeOptions();
                if (headless) chromeOpts.addArguments("--headless=new");
                chromeOpts.addArguments(
                        "--start-maximized",
                        "--disable-notifications",
                        "--remote-allow-origins=*"
                );
                driver = new ChromeDriver(chromeOpts);
            }
        }

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();

        driverThread.set(driver);
        log.info("WebDriver initialized: {}", driver.getClass().getSimpleName());
    }

    /** Returns the WebDriver for the current thread. */
    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            throw new IllegalStateException("WebDriver not initialized. Call initDriver() first.");
        }
        return driverThread.get();
    }

    /** Quit and remove WebDriver for the current thread. */
    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
            log.info("WebDriver closed.");
        }
    }
}
