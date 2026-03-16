package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Class: Login Scenarios
 * Covers: valid login, invalid login, locked-out user, empty fields.
 */
public class LoginTest extends BaseTest {

    private final ConfigReader config = ConfigReader.getInstance();

    // ── TC01: Valid Login ────────────────────────────────────────────────────

    @Test(priority = 1,
          description = "TC01 - Valid user should be redirected to inventory page")
    public void testValidLogin() {
        log.info("TC01: Valid Login");
        InventoryPage inventoryPage = new LoginPage()
                .loginAs(config.getValidUsername(), config.getValidPassword());

        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "User was NOT redirected to inventory page after valid login.");
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
                "Page title mismatch after login.");
    }

    // ── TC02: Invalid Password ───────────────────────────────────────────────

    @Test(priority = 2,
          description = "TC02 - Invalid credentials should display an error message")
    public void testInvalidLogin() {
        log.info("TC02: Invalid Login");
        LoginPage loginPage = new LoginPage()
                .open()
                .enterUsername("invalid_user")
                .enterPassword("wrong_pass")
                .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be visible for invalid credentials.");
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Username and password do not match"),
                "Error message text mismatch.");
    }

    // ── TC03: Locked-Out User ────────────────────────────────────────────────

    @Test(priority = 3,
          description = "TC03 - Locked out user should see a specific error")
    public void testLockedOutUser() {
        log.info("TC03: Locked-out user");
        LoginPage loginPage = new LoginPage()
                .open()
                .enterUsername(config.getLockedUsername())
                .enterPassword(config.getValidPassword())
                .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("locked out"),
                "Expected 'locked out' error for locked user.");
    }

    // ── TC04: Empty Username ─────────────────────────────────────────────────

    @Test(priority = 4,
          description = "TC04 - Empty username should show validation error")
    public void testEmptyUsername() {
        log.info("TC04: Empty username");
        LoginPage loginPage = new LoginPage()
                .open()
                .enterUsername("")
                .enterPassword(config.getValidPassword())
                .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Username is required"));
    }

    // ── TC05: Empty Password ─────────────────────────────────────────────────

    @Test(priority = 5,
          description = "TC05 - Empty password should show validation error")
    public void testEmptyPassword() {
        log.info("TC05: Empty password");
        LoginPage loginPage = new LoginPage()
                .open()
                .enterUsername(config.getValidUsername())
                .enterPassword("")
                .clickLoginExpectingFailure();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Password is required"));
    }

    // ── TC06: Logout ─────────────────────────────────────────────────────────

    @Test(priority = 6,
          description = "TC06 - User should be able to logout successfully")
    public void testLogout() {
        log.info("TC06: Logout");
        LoginPage loginPage = new LoginPage()
                .loginAs(config.getValidUsername(), config.getValidPassword())
                .logout();

        Assert.assertTrue(loginPage.isOnLoginPage(),
                "User should be on login page after logout.");
    }
}
