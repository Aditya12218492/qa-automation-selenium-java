package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Class: End-to-End Checkout Flow.
 * Covers the complete purchase journey from login → product → cart → checkout → confirm.
 */
public class CheckoutTest extends BaseTest {

    private final ConfigReader config = ConfigReader.getInstance();

    // ── TC13: Full E2E Purchase Flow ─────────────────────────────────────────

    @Test(priority = 13,
          description = "TC13 - Complete end-to-end purchase flow should show confirmation")
    public void testCompleteCheckoutFlow() {
        log.info("TC13: Full E2E Checkout");

        // Step 1: Login
        InventoryPage inventory = new LoginPage()
                .loginAs(config.getValidUsername(), config.getValidPassword());
        Assert.assertTrue(inventory.isOnInventoryPage(), "Login failed.");

        // Step 2: Add item to cart
        inventory.addFirstItemToCart();
        Assert.assertEquals(inventory.getCartItemCount(), 1, "Cart count mismatch.");

        // Step 3: Go to cart
        CartPage cart = inventory.goToCart();
        Assert.assertTrue(cart.isOnCartPage(), "Not on cart page.");
        Assert.assertEquals(cart.getCartItemCount(), 1, "Cart should have 1 item.");

        // Step 4: Proceed to checkout
        CheckoutPage checkout = cart.proceedToCheckout();

        // Step 5: Fill customer info
        checkout.fillCustomerInfo("Ruchi", "Kumari", "110001")
                .clickContinue();

        // Step 6: Verify total is displayed and finish
        String total = checkout.getTotalAmount();
        Assert.assertNotNull(total, "Total amount should be displayed.");
        log.info("Order total: {}", total);

        checkout.clickFinish();

        // Step 7: Confirm order
        Assert.assertTrue(checkout.isOrderConfirmed(),
                "Order confirmation header missing 'Thank You'.");
        log.info("Order confirmed: {}", checkout.getConfirmationHeader());
    }

    // ── TC14: Checkout Without Items ─────────────────────────────────────────

    @Test(priority = 14,
          description = "TC14 - Checkout with missing first name should show error")
    public void testCheckoutMissingFirstName() {
        log.info("TC14: Checkout missing first name");

        InventoryPage inventory = new LoginPage()
                .loginAs(config.getValidUsername(), config.getValidPassword());
        inventory.addFirstItemToCart();

        CheckoutPage checkout = inventory.goToCart().proceedToCheckout();
        checkout.fillCustomerInfo("", "Kumari", "110001")
                .clickContinue();

        Assert.assertTrue(checkout.isErrorDisplayed(),
                "Error should show for missing first name.");
        Assert.assertTrue(checkout.getErrorMessage().contains("First Name"),
                "Error should mention 'First Name'.");
    }

    // ── TC15: Checkout Missing Postal Code ───────────────────────────────────

    @Test(priority = 15,
          description = "TC15 - Checkout with missing postal code should show error")
    public void testCheckoutMissingPostalCode() {
        log.info("TC15: Checkout missing postal code");

        InventoryPage inventory = new LoginPage()
                .loginAs(config.getValidUsername(), config.getValidPassword());
        inventory.addFirstItemToCart();

        CheckoutPage checkout = inventory.goToCart().proceedToCheckout();
        checkout.fillCustomerInfo("Ruchi", "Kumari", "")
                .clickContinue();

        Assert.assertTrue(checkout.isErrorDisplayed());
        Assert.assertTrue(checkout.getErrorMessage().contains("Postal Code"));
    }
}
