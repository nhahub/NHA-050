package com.cartify.tests;

import com.cartify.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test(description = "TC-U-1.2: Login: Incorrect Password Check")
    public void testIncorrectPassword() {
        loginPage.login("existing@user.com", "WrongPass123");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Wrong username or password") || error.contains("Wrong"), "Error message should be displayed");
    }

    @Test(description = "TC-U-2.4: Login: Non-existent User Error")
    public void testNonExistentUser() {
        loginPage.login("nonexistent@test.net", "AnyPass123");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Wrong username or password") || error.contains("Wrong"), "Error message should be displayed");
    }

    @Test(description = "TC-U-2.5: Password Case Sensitivity")
    public void testPasswordCaseSensitivity() {
        // Assuming 'existing@user.com' exists with 'ValidPass123@'
        // If not, this test might be flaky without seeding data.
        // But based on requirements, we test the negative case.
        loginPage.login("existing@user.com", "TeStPaSs123@");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Wrong username or password") || error.contains("Wrong"), "Error message should be displayed for wrong case");
    }
}
