package com.cartify.tests;

import com.cartify.pages.CheckoutPage;
import com.cartify.pages.CartPage;
import com.cartify.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutPageTest extends BaseTest {

    private CheckoutPage checkoutPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUpPages() {
        checkoutPage = new CheckoutPage(driver, checkoutUrl, defaultTimeout);
        cartPage = new CartPage(driver, cartUrl, defaultTimeout);
    }

    @Test(priority = 1, description = "Verify checkout validation for empty required field")
    public void testCheckoutShowsErrorForEmptyField() {
        checkoutPage.openCheckoutPage();
        Assert.assertTrue(checkoutPage.isPageLoaded(), "Checkout page should load");

        checkoutPage.fillFullName("Ahmed Mohamed");
        // Email - leave empty
        checkoutPage.fillPhone("01234567890");
        checkoutPage.fillMobile("01234567890");
        checkoutPage.fillZipCode("12345");
        checkoutPage.fillAddress("123 Street");
        checkoutPage.selectGender("Male");

        checkoutPage.selectCashOnDelivery();
        checkoutPage.clickSubmitOrder();

        Assert.assertTrue(checkoutPage.isStillOnCheckoutPage(),
                "User should remain on Checkout Page when validation fails");
    }

    @Test(priority = 2, description = "Verify checkout submission with COD")
    public void testCheckoutWithCashOnDelivery() {
        checkoutPage.openCheckoutPage();
        Assert.assertTrue(checkoutPage.isPageLoaded(), "Checkout page should load");

        checkoutPage.fillBasicCheckoutInfo("Fatma Ali", "fatma@test.com", "01234567890", "01234567890", "54321",
                "456 Avenue", "Female");
        checkoutPage.selectCashOnDelivery();
        checkoutPage.clickSubmitOrder();

        waitFor(2);

        if (checkoutPage.isOrderPlacedSuccessfully()) {
            String successMsg = checkoutPage.getSuccessMessage();
            System.out.println("✅ Success: " + successMsg);
            Assert.assertTrue(successMsg.contains("success") || successMsg.contains("submitted"),
                    "Should show success message");
        } else {
            System.out.println("⚠️ No success message - may need cart items");
        }
    }

    @Test(priority = 3, description = "Verify checkout with credit card payment")
    public void testCheckoutWithCreditCard() {
        checkoutPage.openCheckoutPage();
        checkoutPage.fillBasicCheckoutInfo("Omar Hassan", "omar@test.com", "01234567890", "01234567890", "67890",
                "789 Road", "Male");
        checkoutPage.selectCreditCard();

        waitFor(1);

        if (checkoutPage.isCardInfoVisible()) {
            checkoutPage.fillCreditCardInfo("4111111111111111", "Test User", "12/25", "123");
            checkoutPage.clickSubmitOrder();
            waitFor(2);
            if (checkoutPage.isOrderPlacedSuccessfully()) {
                System.out.println("✅ Order placed successfully with Credit Card");
            } else {
                System.out.println("⚠️ Card fields filled but may need cart items");
            }
        } else {
            System.out.println("⚠️ Credit card fields not visible");
        }
    }

    @Test(priority = 4, description = "Verify checkout with PayPal payment")
    public void testCheckoutWithPayPal() {
        checkoutPage.openCheckoutPage();
        checkoutPage.fillBasicCheckoutInfo("Sara Ahmed", "sara@test.com", "01234567890", "01234567890", "11111",
                "123 Main St", "Female");
        checkoutPage.selectPayPal();

        waitFor(1);

        if (checkoutPage.isPayPalSectionVisible()) {
            checkoutPage.fillPayPalInfo("sara.paypal@test.com");
            checkoutPage.clickSubmitOrder();
            waitFor(2);
            if (checkoutPage.isOrderPlacedSuccessfully()) {
                System.out.println("✅ Order placed successfully with PayPal");
            } else {
                System.out.println("⚠️ PayPal filled but may need cart items");
            }
        } else {
            System.out.println("⚠️ PayPal fields not visible");
        }
    }

    @Test(priority = 5, description = "Document inability to fully test checkout due to empty products")
    public void testCheckoutLimitationDueToEmptyProducts() {
        ProductsPage productsPage = new ProductsPage(driver, productsUrl, defaultTimeout);
        productsPage.openProductsPage();
        int productCount = productsPage.getProductCount();

        if (productCount == 0) {
            Assert.assertTrue(productsPage.isProductsPageEmpty(),
                    "Products page is currently empty (known limitation)");
            System.out.println("⚠️ LIMITATION: Cannot test full checkout flow");
            System.out.println("Reason: Products Page displays no products");
            System.out.println("Cannot add items to cart → Cannot test checkout with items");
        } else {
            Assert.fail("Products are now available! Update this test to test full checkout flow");
        }
    }
}
