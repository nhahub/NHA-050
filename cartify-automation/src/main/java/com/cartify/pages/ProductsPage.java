package com.cartify.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    // ============================================
    // Page Locators
    // ============================================

    // From auto module
    private By pageTitle = By.cssSelector(".products-title");
    private By productsList = By.cssSelector(".products-list");
    private By productItems = By.cssSelector(".product-item");
    private By addToCartButtons = By.cssSelector(".add-to-cart-btn");
    private By emptyProductsMessage = By.cssSelector(".empty-products-message");
    private By cartIcon = By.cssSelector(".cart-icon");
    private By productName = By.cssSelector(".product-name");
    private By productPrice = By.cssSelector(".product-price");

    // From cartify-automation module (converted to By)
    private By searchBar = By.id("searchBar");
    private By searchButton = By.cssSelector("button.gradient-btn");
    private By cartButton = By.cssSelector("a.cart-btn");
    private By wishlistButton = By.cssSelector("a.wishlist-btn");
    private By sortDropdown = By.id("sort");
    private By productCardContainer = By.cssSelector(".product-card");
    private By addToWishlistButton = By.cssSelector(".btn-wishlist");

    /**
     * Constructor for tests using URL and timeout (from auto)
     */
    public ProductsPage(WebDriver driver, String url, int seconds) {
        super(driver, seconds, url);
    }

    /**
     * Constructor for tests using default settings (from cartify)
     */
    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // ============================================
    // Page Verification Methods
    // ============================================

    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(pageTitle) || isElementDisplayed(productCardContainer);
    }

    public void openProductsPage() {
        open();
    }

    /**
     * Open method from cartify-automation
     */
    @Override
    public void open() {
        if (this.url != null && !this.url.isEmpty()) {
            super.open();
        } else {
            driver.get("https://cartify0.netlify.app/products.html?categoryId=2");
        }
    }

    // ============================================
    // Products Verification Methods (from auto)
    // ============================================

    public boolean isProductsPageEmpty() {
        return getProductCount() == 0;
    }

    public int getProductCount() {
        return getElements(productItems).size();
    }

    public boolean isEmptyProductsMessageDisplayed() {
        return isElementDisplayed(emptyProductsMessage);
    }

    public String getEmptyProductsMessage() {
        return getElementText(emptyProductsMessage);
    }

    // ============================================
    // Add to Cart Methods (from auto)
    // ============================================

    public void attemptToAddProductToCart() {
        if (getProductCount() > 0) {
            click(addToCartButtons);
        } else {
            throw new RuntimeException("Cannot add product to cart - Products page is empty");
        }
    }

    public void addProductToCartByIndex(int index) {
        var buttons = getElements(addToCartButtons);
        if (index >= 0 && index < buttons.size()) {
            buttons.get(index).click();
        } else {
            throw new RuntimeException("Product index out of range: " + index);
        }
    }

    public boolean areAddToCartButtonsVisible() {
        return getElements(addToCartButtons).size() > 0;
    }

    // ============================================
    // Navigation & Interaction Methods (from cartify)
    // ============================================

    public void searchFor(String term) {
        sendKeys(searchBar, term);
        click(searchButton);
    }

    public void goToCart() {
        if (isElementDisplayed(cartButton)) {
            click(cartButton);
        } else {
            click(cartIcon);
        }
    }

    public void clickCartIcon() {
        goToCart();
    }

    public void goToWishlist() {
        click(wishlistButton);
    }

    public void sortBy(String option) {
        selectByVisibleText(sortDropdown, option);
    }

    public boolean isProductDisplayed() {
        return isElementDisplayed(productCardContainer);
    }

    public void addToWishlist() {
        click(addToWishlistButton);
    }

    public String getProductsPageTitle() {
        return getPageTitle();
    }
}
