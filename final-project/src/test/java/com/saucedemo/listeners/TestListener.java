package com.saucedemo.listeners;

import com.aventstack.extentreports.Status;
import com.saucedemo.utils.ReportManager;
import com.saucedemo.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener: hooks into test lifecycle for reporting + screenshots.
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        String desc = result.getMethod().getDescription();
        ReportManager.createTest(name, desc != null ? desc : "");
        log.info("▶ TEST STARTED: {}", name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportManager.getTest().log(Status.PASS, "✅ Test PASSED");
        log.info("✅ TEST PASSED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String errorMsg = result.getThrowable().getMessage();

        // Log failure details
        ReportManager.getTest().log(Status.FAIL, "❌ Test FAILED: " + errorMsg);

        // Take screenshot
        String screenshotPath = ScreenshotUtils.capture(testName);
        if (!screenshotPath.isEmpty()) {
            try {
                ReportManager.getTest().addScreenCaptureFromPath(
                        screenshotPath, "Failure Screenshot");
            } catch (Exception e) {
                log.error("Could not attach screenshot: {}", e.getMessage());
            }
        }
        log.error("❌ TEST FAILED: {} | Reason: {}", testName, errorMsg);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ReportManager.getTest().log(Status.SKIP, "⏭ Test SKIPPED");
        log.warn("⏭ TEST SKIPPED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.flush();
        log.info("📊 Report generated. Suite finished: {}", context.getName());
    }
}
