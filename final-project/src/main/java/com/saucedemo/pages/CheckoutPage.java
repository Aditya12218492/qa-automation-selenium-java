package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for SauceDemo Checkout Pages (Step 1 + Step 2 + Complete).
 */
public class CheckoutPage extends BasePage {

    // ── Step 1 – Customer Info ───────────────────────────────────────────────

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // ── Step 2 – Overview ────────────────────────────────────────────────────

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    // ── Complete ─────────────────────────────────────────────────────────────

    @FindBy(className = "complete-header")
    private WebElement confirmationHeader;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    // ── Actions ──────────────────────────────────────────────────────────────

    public CheckoutPage fillCustomerInfo(String firstName, String lastName, String zip) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, zip);
        log.info("Customer info filled: {} {} {}", firstName, lastName, zip);
        return this;
    }

    public CheckoutPage clickContinue() {
        click(continueButton);
        return this;
    }

    public String getTotalAmount() {
        return getText(totalLabel);
    }

    public CheckoutPage clickFinish() {
        click(finishButton);
        log.info("Order finished.");
        return this;
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    public String getConfirmationText() {
        return getText(confirmationText);
    }

    public boolean isOrderConfirmed() {
        return getConfirmationHeader().toLowerCase().contains("thank you");
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
