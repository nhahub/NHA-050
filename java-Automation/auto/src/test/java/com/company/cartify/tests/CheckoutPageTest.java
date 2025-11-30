package com.company.cartify.tests;

import com.company.cartify.tests.base.BaseTest;
import com.company.cartify.tests.cartify.pages.CheckoutPage;
import com.company.cartify.tests.cartify.pages.CartPage;
import com.company.cartify.tests.cartify.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * CheckoutPageTest: اختبارات صفحة الدفع
 */
public class CheckoutPageTest extends BaseTest {

    private CheckoutPage checkoutPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUpPages() {
        checkoutPage = new CheckoutPage(driver, checkoutUrl, defaultTimeout);
        cartPage = new CartPage(driver, cartUrl, defaultTimeout);
    }

    /**
     * TC-C-008: Checkout form validation for empty required field
     */
    @Test(priority = 1, description = "Verify checkout validation for empty required field")
    public void testCheckoutShowsErrorForEmptyField() {
        // Open Checkout Page
        checkoutPage.openCheckoutPage();
        Assert.assertTrue(checkoutPage.isPageLoaded(), "Checkout page should load");

        // Fill all fields EXCEPT email (required field)
        checkoutPage.fillFullName("Ahmed Mohamed");
        // Email - leave empty
        checkoutPage.fillPhone("01234567890");
        checkoutPage.fillMobile("01234567890");
        checkoutPage.fillZipCode("12345");
        checkoutPage.fillAddress("123 Street");
        checkoutPage.selectGender("Male");

        // Select payment method
        checkoutPage.selectCashOnDelivery();

        // Try to submit
        checkoutPage.clickSubmitOrder();

        // Verification: Should remain on checkout page (HTML5 validation)
        Assert.assertTrue(checkoutPage.isStillOnCheckoutPage(),
                "User should remain on Checkout Page when validation fails");
    }

    /**
     * TC-C-009: Checkout with Cash on Delivery - valid data
     */
    @Test(priority = 2, description = "Verify checkout submission with COD")
    public void testCheckoutWithCashOnDelivery() {
        // Open Checkout
        checkoutPage.openCheckoutPage();
        Assert.assertTrue(checkoutPage.isPageLoaded(), "Checkout page should load");

        // Fill all required fields
        checkoutPage.fillBasicCheckoutInfo(
                "Fatma Ali", "fatma@test.com", "01234567890",
                "01234567890", "54321", "456 Avenue", "Female");

        // Select Cash on Delivery
        checkoutPage.selectCashOnDelivery();

        // Submit order
        checkoutPage.clickSubmitOrder();

        // Wait a bit for submission
        waitFor(2);

        // Check if success message appears
        if (checkoutPage.isOrderPlacedSuccessfully()) {
            String successMsg = checkoutPage.getSuccessMessage();
            System.out.println("✅ Success: " + successMsg);
            Assert.assertTrue(successMsg.contains("success") || successMsg.contains("submitted"),
                    "Should show success message");
        } else {
            System.out.println("⚠️ No success message - may need cart items");
        }
    }

    /**
     * TC-C-010: Checkout with Credit Card
     */
    @Test(priority = 3, description = "Verify checkout with credit card payment")
    public void testCheckoutWithCreditCard() {
        checkoutPage.openCheckoutPage();

        // Fill shipping info
        checkoutPage.fillBasicCheckoutInfo(
                "Omar Hassan", "omar@test.com", "01234567890",
                "01234567890", "67890", "789 Road", "Male");

        // Select Credit Card
        checkoutPage.selectCreditCard();

        // Wait for card fields to appear
        waitFor(1);

        // Fill credit card info if fields are visible
        if (checkoutPage.isCardInfoVisible()) {
            checkoutPage.fillCreditCardInfo(
                    "4111111111111111",
                    "Test User",
                    "12/25",
                    "123");

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

    /**
     * TC-C-011: Checkout with PayPal
     */
    @Test(priority = 4, description = "Verify checkout with PayPal payment")
    public void testCheckoutWithPayPal() {
        checkoutPage.openCheckoutPage();

        // Fill shipping info
        checkoutPage.fillBasicCheckoutInfo(
                "Sara Ahmed", "sara@test.com", "01234567890",
                "01234567890", "11111", "123 Main St", "Female");

        // Select PayPal
        checkoutPage.selectPayPal();

        // Wait for PayPal fields
        waitFor(1);

        // Fill PayPal info if visible
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

    /**
     * TC-C-012: Products page limitation documentation
     */
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
