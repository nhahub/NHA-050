package com.cartify.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends BasePage {

    @FindBy(id = "searchBar")
    private WebElement searchBar;

    @FindBy(css = "button.gradient-btn")
    private WebElement searchButton;

    @FindBy(css = "a.cart-btn")
    private WebElement cartButton;

    @FindBy(css = "a.wishlist-btn")
    private WebElement wishlistButton;

    @FindBy(id = "sort")
    private WebElement sortDropdown;

    // Locators for product card elements (inferred/placeholders)
    @FindBy(css = ".product-card")
    private WebElement productCardContainer;

    @FindBy(css = ".product-name")
    private WebElement productName;

    @FindBy(css = ".product-price")
    private WebElement productPrice;

    @FindBy(css = ".add-to-cart-btn")
    private WebElement addToCartButton;

    @FindBy(css = ".btn-wishlist")
    private WebElement addToWishlistButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        // Navigate to a category that has products (e.g. Clothes)
        driver.get("https://cartify0.netlify.app/products.html?categoryId=2");
    }

    public void searchFor(String term) {
        type(searchBar, term);
        click(searchButton);
    }

    public void goToCart() {
        click(cartButton);
    }

    public void goToWishlist() {
        click(wishlistButton);
    }

    public void sortBy(String option) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(option);
    }

    public boolean isProductDisplayed() {
        return isDisplayed(productCardContainer);
    }

    public void addToWishlist() {
        click(addToWishlistButton);
    }
}
