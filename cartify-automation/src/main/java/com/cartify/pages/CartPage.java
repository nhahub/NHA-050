package com.cartify.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CartPage: Represents the Cart Page.
 * Migrated from auto module.
 */
public class CartPage extends BasePage {

    // ============================================
    // Page Locators - CSS Selectors
    // ============================================

    private By cartTitle = By.cssSelector(".cart-title");
    private By cartCountText = By.cssSelector(".cart-count");
    private By cartContainer = By.id("cart-container");
    private By checkoutBtn = By.cssSelector(".checkout-btn");
    private By continueShoppingBtn = By.cssSelector(".continue-shopping");
    private By startShoppingBtn = By.cssSelector(".start-shopping"); // For empty cart
    private By emptyCartMessage = By.cssSelector(".empty-cart-message");

    // Price elements
    private By subtotalElement = By.id("subtotal");
    private By shippingElement = By.id("shipping");
    private By taxElement = By.id("tax");
    private By totalElement = By.id("total");

    // Cart items
    private By cartItems = By.cssSelector(".cart-item");
    private By deleteItemBtn = By.cssSelector(".delete-item");

    /**
     * Constructor
     * 
     * @param driver  WebDriver instance
     * @param url     Cart page URL
     * @param seconds Default wait time
     */
    public CartPage(WebDriver driver, String url, int seconds) {
        super(driver, seconds, url);
    }

    // ============================================
    // Page Verification Methods
    // ============================================

    @Override
    public boolean isPageLoaded() {
        try {
            waitForElementToBeVisible(cartTitle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void openCartPage() {
        open();
    }

    // ============================================
    // Cart Item Methods
    // ============================================

    public int getCartItemCount() {
        if (isCartEmpty()) {
            return 0;
        }
        String countText = getElementText(cartCountText);
        return Integer.parseInt(countText.replaceAll("[^0-9]", ""));
    }

    public int getActualCartItemsCount() {
        return getElements(cartItems).size();
    }

    public boolean isCartEmpty() {
        return isElementDisplayed(emptyCartMessage);
    }

    public String getEmptyCartMessage() {
        return getElementText(emptyCartMessage);
    }

    // ============================================
    // Price Methods
    // ============================================

    public String getSubtotal() {
        return getElementText(subtotalElement);
    }

    public String getShipping() {
        return getElementText(shippingElement);
    }

    public String getTax() {
        return getElementText(taxElement);
    }

    public String getTotal() {
        return getElementText(totalElement);
    }

    // ============================================
    // Navigation Methods
    // ============================================

    public void clickProceedToCheckout() {
        click(checkoutBtn);
    }

    public void clickContinueShopping() {
        click(continueShoppingBtn);
    }

    public void clickStartShopping() {
        click(startShoppingBtn);
    }

    // ============================================
    // Button Verification Methods
    // ============================================

    public boolean isCheckoutButtonVisible() {
        return isElementDisplayed(checkoutBtn);
    }

    public boolean isCheckoutButtonEnabled() {
        return isElementEnabled(checkoutBtn);
    }

    public boolean isContinueShoppingButtonVisible() {
        return isElementDisplayed(continueShoppingBtn);
    }

    public boolean isDeleteButtonVisible() {
        return isElementDisplayed(deleteItemBtn);
    }

    public boolean isCartContainerDisplayed() {
        try {
            waitForElementToBeVisible(cartContainer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCartPageTitle() {
        return getPageTitle();
    }
}
