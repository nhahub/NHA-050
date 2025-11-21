package com.cartify.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {

    @FindBy(css = "a.shop-btn[href='index.html']")
    private WebElement startShoppingButton;

    @FindBy(css = "button.checkout-btn")
    private WebElement proceedToCheckoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://cartify0.netlify.app/cartpage");
    }

    public void startShopping() {
        click(startShoppingButton);
    }

    public void proceedToCheckout() {
        click(proceedToCheckoutButton);
    }
    
    public boolean isCartEmptyMessageDisplayed() {
        try {
            return driver.findElement(By.xpath("//*[contains(text(), 'Your cart is empty')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
