package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test Class: Inventory / Product Page Scenarios.
 */
public class InventoryTest extends BaseTest {

    private final ConfigReader config = ConfigReader.getInstance();

    private InventoryPage loginAndGetInventory() {
        return new LoginPage()
                .loginAs(config.getValidUsername(), config.getValidPassword());
    }

    // ── TC07: Product Count ──────────────────────────────────────────────────

    @Test(priority = 7,
          description = "TC07 - Inventory page should display 6 products")
    public void testProductCount() {
        log.info("TC07: Product count");
        InventoryPage inventory = loginAndGetInventory();

        Assert.assertEquals(inventory.getProductCount(), 6,
                "Expected 6 products on the inventory page.");
    }

    // ── TC08: Add Single Item to Cart ────────────────────────────────────────

    @Test(priority = 8,
          description = "TC08 - Adding one item should update cart badge to 1")
    public void testAddSingleItemToCart() {
        log.info("TC08: Add single item to cart");
        InventoryPage inventory = loginAndGetInventory();
        inventory.addFirstItemToCart();

        Assert.assertEquals(inventory.getCartItemCount(), 1,
                "Cart badge should show 1 after adding one item.");
    }

    // ── TC09: Add All Items to Cart ──────────────────────────────────────────

    @Test(priority = 9,
          description = "TC09 - Adding all items should update cart badge to 6")
    public void testAddAllItemsToCart() {
        log.info("TC09: Add all items to cart");
        InventoryPage inventory = loginAndGetInventory();
        inventory.addAllItemsToCart();

        Assert.assertEquals(inventory.getCartItemCount(), 6,
                "Cart badge should show 6 after adding all items.");
    }

    // ── TC10: Sort by Price Low-High ─────────────────────────────────────────

    @Test(priority = 10,
          description = "TC10 - Products should be sorted by price low to high")
    public void testSortByPriceLowHigh() {
        log.info("TC10: Sort by Price (low to high)");
        InventoryPage inventory = loginAndGetInventory();
        inventory.sortBy("Price (low to high)");

        List<String> prices = inventory.getAllProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            double p1 = Double.parseDouble(prices.get(i).replace("$", ""));
            double p2 = Double.parseDouble(prices.get(i + 1).replace("$", ""));
            Assert.assertTrue(p1 <= p2,
                    "Products are NOT sorted by price (low to high) at index " + i);
        }
    }

    // ── TC11: Sort by Name A-Z ───────────────────────────────────────────────

    @Test(priority = 11,
          description = "TC11 - Products should be sorted alphabetically A to Z")
    public void testSortByNameAZ() {
        log.info("TC11: Sort by Name A-Z");
        InventoryPage inventory = loginAndGetInventory();
        inventory.sortBy("Name (A to Z)");

        List<String> names = inventory.getAllProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(names.get(i).compareTo(names.get(i + 1)) <= 0,
                    "Products are NOT sorted A to Z at index " + i);
        }
    }

    // ── TC12: Navigate to Cart ───────────────────────────────────────────────

    @Test(priority = 12,
          description = "TC12 - Clicking cart icon should navigate to cart page")
    public void testNavigateToCart() {
        log.info("TC12: Navigate to cart");
        CartPage cartPage = loginAndGetInventory().goToCart();

        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should be on cart page after clicking cart icon.");
    }
}
