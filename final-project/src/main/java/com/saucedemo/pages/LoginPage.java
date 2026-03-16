package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for SauceDemo Login Page.
 * URL: https://www.saucedemo.com
 */
public class LoginPage extends BasePage {

    // ── Locators (via @FindBy) ───────────────────────────────────────────────

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // ── Actions ──────────────────────────────────────────────────────────────

    public LoginPage open() {
        navigateTo("https://www.saucedemo.com");
        log.info("LoginPage opened.");
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public InventoryPage clickLogin() {
        click(loginButton);
        log.info("Login button clicked.");
        return new InventoryPage();
    }

    /** Attempt login that is expected to FAIL (returns same page). */
    public LoginPage clickLoginExpectingFailure() {
        click(loginButton);
        return this;
    }

    /** Full login flow returning InventoryPage. */
    public InventoryPage loginAs(String username, String password) {
        return open()
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    // ── Assertions helpers ───────────────────────────────────────────────────

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().equals("https://www.saucedemo.com/");
    }
}
