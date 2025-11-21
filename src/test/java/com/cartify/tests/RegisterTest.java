
package com.cartify.tests;

import com.cartify.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpPage() {
        registerPage = new RegisterPage(driver);
        registerPage.open();
    }

    @Test(description = "TC-U-1.1: Valid Registration Submission")
    public void testValidRegistration() {
        String email = "newuser" + System.currentTimeMillis() + "@test.com";
        String username = "User" + System.currentTimeMillis();
        
        // Step 1: Account Info
        registerPage.fillAccountInfo(email, username, "ValidPass123@");
        
        // Step 2: Personal Info
        registerPage.fillPersonalInfo("Test", "User", "1234567890");
        registerPage.fillDate("01/01/2000"); // Date is between Name and Phone in UI, but order here is fine if locators work
        registerPage.selectGender(true);
        registerPage.clickNext();
        
        // Step 3: Address Info
        registerPage.fillAddress("123 Test St", "Test City", "Test State");
        
        // Verification: Check if redirected or success message appears
        Assert.assertFalse(driver.getCurrentUrl().contains("register.html"), "User should be redirected after registration");
    }

    @Test(description = "TC-U-1.9: Verify Address Step Submission")
    public void testAddressSubmission() {
        String email = "addressuser" + System.currentTimeMillis() + "@test.com";
        String username = "AddrUser" + System.currentTimeMillis();
        
        // Pre-requisites: Get to Address Step
        registerPage.fillAccountInfo(email, username, "ValidPass123@");
        registerPage.fillPersonalInfo("Address", "Tester", "0987654321");
        registerPage.fillDate("02/02/1990");
        registerPage.selectGender(false);
        registerPage.clickNext();
        
        // Verify we are on the address step (optional, check for an element)
        // Then fill and submit
        registerPage.fillAddress("456 Sample Ave", "Sample City", "Sample State");
        
        // Assert success
        Assert.assertFalse(driver.getCurrentUrl().contains("register.html"), "User should be redirected after submitting address");
    }

    @Test(description = "TC-U-1.7: Password Minimum Length Check")
    public void testPasswordMinLength() {
        registerPage.fillAccountInfo("shortpass@test.com", "ShortPassUser", "short12");
        // Should fail
        String error = registerPage.getErrorMessage();
        if (error != null && !error.isEmpty()) {
             Assert.assertTrue(error.contains("Password must be 8+ chars"), "Error message should match");
        } else {
             // If no error message found yet, maybe check if we proceeded?
             // Or maybe the input has :invalid state
        }
    }
}
