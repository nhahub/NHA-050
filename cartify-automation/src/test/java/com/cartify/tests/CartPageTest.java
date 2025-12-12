package com.cartify.tests;

import com.cartify.pages.CartPage;
import com.cartify.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

    private CartPage cartPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setUpPages() {
        cartPage = new CartPage(driver, cartUrl, defaultTimeout);
        productsPage = new ProductsPage(driver, productsUrl, defaultTimeout);
    }

    @Test(priority = 1, description = "Verify cart page loads successfully with all elements visible")
    public void testCartPageLoadsSuccessfully() {
        cartPage.openCartPage();
        Assert.assertTrue(cartPage.isPageLoaded(), "Cart page should load successfully");
        Assert.assertTrue(cartPage.isCartContainerDisplayed(), "Cart container should be displayed");
        Assert.assertTrue(cartPage.isCheckoutButtonVisible(), "Checkout button should be visible");
        Assert.assertTrue(cartPage.isContinueShoppingButtonVisible(), "Continue Shopping button should be visible");
        Assert.assertTrue(cartPage.isCheckoutButtonEnabled(), "Checkout button should be enabled and functional");
        boolean deleteButtonExists = cartPage.isDeleteButtonVisible();
        System.out.println("Delete button visible: " + deleteButtonExists);
    }

    @Test(priority = 2, description = "Verify empty cart displays correct placeholder message")
    public void testEmptyCartDisplaysMessage() {
        cartPage.openCartPage();
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty");
        String emptyMessage = cartPage.getEmptyCartMessage();
        Assert.assertEquals(emptyMessage, "Your cart is empty",
                "Empty cart should display 'Your cart is empty' message");
    }

    @Test(priority = 3, description = "Verify Start Shopping button redirects to product listing")
    public void testStartShoppingRedirectsToProducts() {
        cartPage.openCartPage();
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart must be empty for this test");
        cartPage.clickStartShopping();
        Assert.assertTrue(productsPage.isPageLoaded(), "User should be redirected to Products Page");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("products") || currentUrl.contains("home"),
                "URL should contain 'products' or navigate to home page");
    }

    @Test(priority = 4, description = "Verify behavior when Products Page displays no products")
    public void testUnableToAddProductToCart() {
        productsPage.openProductsPage();
        Assert.assertTrue(productsPage.isPageLoaded(), "Products page should load");
        int productCount = productsPage.getProductCount();
        System.out.println("Products displayed: " + productCount);

        if (productCount == 0) {
            Assert.assertTrue(productsPage.isProductsPageEmpty(), "Products page should be empty (bug scenario)");
            Assert.assertFalse(productsPage.areAddToCartButtonsVisible(),
                    "Add to Cart buttons should not be visible when no products");
            try {
                productsPage.attemptToAddProductToCart();
                Assert.fail("Should not be able to add product when page is empty");
            } catch (RuntimeException e) {
                Assert.assertTrue(e.getMessage().contains("empty"), "Exception should indicate empty products");
            }
        } else {
            productsPage.addProductToCartByIndex(0);
            System.out.println("Product added successfully");
        }
    }

    @Test(priority = 5, description = "Verify cart displays accurate item count")
    public void testCartShowsCorrectItemCount() {
        cartPage.openCartPage();
        if (cartPage.isCartEmpty()) {
            int displayedCount = cartPage.getCartItemCount();
            int actualCount = cartPage.getActualCartItemsCount();
            Assert.assertEquals(displayedCount, 0, "Displayed count should be 0 for empty cart");
            Assert.assertEquals(actualCount, 0, "Actual items count should be 0 for empty cart");
            Assert.assertEquals(displayedCount, actualCount, "Displayed count should match actual items count");
        } else {
            int displayedCount = cartPage.getCartItemCount();
            int actualCount = cartPage.getActualCartItemsCount();
            System.out.println("Displayed count: " + displayedCount);
            System.out.println("Actual count: " + actualCount);
            Assert.assertEquals(displayedCount, actualCount, "Cart item count should match actual items added");
        }
    }

    @Test(priority = 6, description = "Verify Proceed to Checkout button redirects correctly")
    public void testProceedToCheckoutButton() {
        cartPage.openCartPage();
        Assert.assertTrue(cartPage.isPageLoaded(), "Cart page should load");
        cartPage.clickProceedToCheckout();
        Assert.assertTrue(cartPage.waitForUrlToContain("checkout"), "User should be redirected to Checkout Page");
        String pageTitle = driver.getTitle();
        System.out.println("Current page title: " + pageTitle);
    }

    @Test(priority = 7, description = "Verify Continue Shopping button redirects to Products Page")
    public void testContinueShoppingButton() {
        cartPage.openCartPage();
        Assert.assertTrue(cartPage.isPageLoaded(), "Cart page should load");
        cartPage.clickContinueShopping();
        boolean isRedirected = cartPage.waitForUrlToContain("products") || cartPage.waitForUrlToContain("home");
        Assert.assertTrue(isRedirected, "User should be redirected to Products Page");
        Assert.assertTrue(productsPage.isPageLoaded(), "Products Page should be loaded");
    }
}
