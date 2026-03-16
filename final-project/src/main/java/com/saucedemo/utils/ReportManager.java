package com.saucedemo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Manages ExtentReports lifecycle (thread-safe).
 */
public class ReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    private ReportManager() {}

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportPath = "reports/TestReport_" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("SauceDemo Automation Report");
            spark.config().setReportName("Selenium + TestNG | POM Framework");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework",  "Selenium 4 + TestNG");
            extent.setSystemInfo("Target URL",  "https://www.saucedemo.com");
            extent.setSystemInfo("OS",          System.getProperty("os.name"));
            extent.setSystemInfo("Java",        System.getProperty("java.version"));
        }
        return extent;
    }

    public static void createTest(String testName, String description) {
        ExtentTest test = getExtent().createTest(testName, description);
        testThread.set(test);
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

    public static void flush() {
        if (extent != null) extent.flush();
    }
}
