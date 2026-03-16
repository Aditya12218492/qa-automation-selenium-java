package com.saucedemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for SauceDemo Inventory / Products Page.
 * URL: https://www.saucedemo.com/inventory.html
 */
public class InventoryPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(css = "button.btn_inventory")
    private List<WebElement> addToCartButtons;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = "select.product_sort_container")
    private WebElement sortDropdown;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement hamburgerMenu;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    // ── Actions ──────────────────────────────────────────────────────────────

    public boolean isOnInventoryPage() {
        return getCurrentUrl().contains("inventory");
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public int getProductCount() {
        return inventoryItems.size();
    }

    public List<String> getAllProductNames() {
        return itemNames.stream()
                .map(e -> e.getText().trim())
                .toList();
    }

    public List<String> getAllProductPrices() {
        return itemPrices.stream()
                .map(e -> e.getText().trim())
                .toList();
    }

    /** Add the first product to the cart. */
    public InventoryPage addFirstItemToCart() {
        click(addToCartButtons.get(0));
        log.info("First item added to cart.");
        return this;
    }

    /** Add all products to the cart. */
    public InventoryPage addAllItemsToCart() {
        addToCartButtons.forEach(this::click);
        log.info("All {} items added to cart.", addToCartButtons.size());
        return this;
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        click(cartIcon);
        return new CartPage();
    }

    public InventoryPage sortBy(String visibleText) {
        org.openqa.selenium.support.ui.Select select =
                new org.openqa.selenium.support.ui.Select(sortDropdown);
        select.selectByVisibleText(visibleText);
        log.info("Sorted by: {}", visibleText);
        return this;
    }

    public LoginPage logout() {
        click(hamburgerMenu);
        click(logoutLink);
        log.info("Logged out.");
        return new LoginPage();
    }
}
