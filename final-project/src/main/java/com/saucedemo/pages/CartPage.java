package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for SauceDemo Cart Page.
 */
public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    // ── Actions ──────────────────────────────────────────────────────────────

    public boolean isOnCartPage() {
        return getCurrentUrl().contains("cart");
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public List<String> getCartItemNames() {
        return cartItemNames.stream()
                .map(e -> e.getText().trim())
                .toList();
    }

    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        log.info("Proceeded to checkout.");
        return new CheckoutPage();
    }

    public InventoryPage continueShopping() {
        click(continueShoppingButton);
        return new InventoryPage();
    }
}
