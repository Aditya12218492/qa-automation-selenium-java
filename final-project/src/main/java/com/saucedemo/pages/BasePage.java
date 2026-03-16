package com.saucedemo.pages;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage: parent class for all Page Objects.
 * Contains reusable Selenium helper methods.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Logger log;
    private static final ConfigReader config = ConfigReader.getInstance();

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait   = new WebDriverWait(driver,
                Duration.ofSeconds(config.getExplicitWait()));
        this.log    = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }

    // ── Navigation ──────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        driver.get(url);
        log.info("Navigated to: {}", url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    // ── Click ────────────────────────────────────────────────────────────────

    public void click(WebElement element) {
        waitForClickable(element).click();
        log.debug("Clicked element: {}", element);
    }

    public void clickByLocator(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // ── Type ─────────────────────────────────────────────────────────────────

    public void type(WebElement element, String text) {
        waitForVisible(element).clear();
        element.sendKeys(text);
        log.debug("Typed '{}' into element", text);
    }

    // ── Waits ─────────────────────────────────────────────────────────────────

    public WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    // ── Text ─────────────────────────────────────────────────────────────────

    public String getText(WebElement element) {
        return waitForVisible(element).getText().trim();
    }

    // ── JavaScript ───────────────────────────────────────────────────────────

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
