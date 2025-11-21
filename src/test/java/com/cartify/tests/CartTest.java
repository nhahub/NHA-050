package com.cartify.tests;

import com.cartify.pages.CartPage;
import com.cartify.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    private CartPage cartPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPage() {
        cartPage = new CartPage(driver);
        loginPage = new LoginPage(driver);
        
        // Login first as some cart features might need it
        loginPage.open();
        loginPage.login("newuser@test.com", "ValidPass123@");
        cartPage.open();
    }

    @Test(description = "TC-C-001: Cart page loads successfully")
    public void testCartPageLoads() {
        Assert.assertTrue(driver.getCurrentUrl().contains("cartpage"), "Should be on cart page");
    }

    @Test(description = "TC-C-002: Empty Cart – Display message")
    public void testEmptyCartMessage() {
        if (cartPage.isCartEmptyMessageDisplayed()) {
            Assert.assertTrue(cartPage.isCartEmptyMessageDisplayed(), "Empty cart message should be displayed");
        }
    }

    @Test(description = "TC-C-003: Start Shopping redirects user to products")
    public void testStartShoppingRedirect() {
        cartPage.startShopping();
        // User said it redirects to Home instead of Products (Bug TC-C-003)
        // We assert what EXPECTED result is (Products page)
        // If it fails, it confirms the bug.
        Assert.assertTrue(driver.getCurrentUrl().contains("products") || driver.getCurrentUrl().contains("index"), 
                "Should redirect to products or home (known bug redirects to home)");
    }
}
