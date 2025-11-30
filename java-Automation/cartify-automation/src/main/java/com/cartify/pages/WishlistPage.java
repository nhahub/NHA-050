package com.cartify.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.stream.Collectors;

public class WishlistPage extends BasePage {

    @FindBy(css = ".wishlist-item")
    private List<WebElement> wishlistItems;

    @FindBy(css = ".wishlist-item .product-name")
    private List<WebElement> wishlistProductNames;

    @FindBy(css = ".wishlist-item .remove-btn")
    private List<WebElement> removeButtons;

    public WishlistPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public int getWishlistSize() {
        return wishlistItems.size();
    }

    public List<String> getWishlistProductNames() {
        return wishlistProductNames.stream()
                .map(this::getText)
                .collect(Collectors.toList());
    }

    public boolean isProductInWishlist(String productName) {
        return getWishlistProductNames().contains(productName);
    }

    public void removeProductFromWishlist(String productName) {
        for (int i = 0; i < wishlistProductNames.size(); i++) {
            if (getText(wishlistProductNames.get(i)).equals(productName)) {
                click(removeButtons.get(i));
                return;
            }
        }
        throw new RuntimeException("Product " + productName + " not found in wishlist");
    }
}
