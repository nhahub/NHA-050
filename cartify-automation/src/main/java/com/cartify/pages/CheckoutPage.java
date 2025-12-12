package com.cartify.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CheckoutPage: Represents the Checkout Page.
 * Migrated from auto module.
 */
public class CheckoutPage extends BasePage {

    // ============================================
    // Page Locators - CSS Selectors
    // ============================================

    // Form & Page Elements
    private By checkoutForm = By.cssSelector("form#myform");
    private By pageTitle = By.cssSelector("form#myform h1");

    // Shipping Information Fields
    private By fullNameField = By.cssSelector("input[name='fullname']");
    private By emailField = By.cssSelector("input[name='email']");
    private By phoneField = By.cssSelector("input[name='phone']");
    private By zipCodeField = By.cssSelector("input[name='ZIP']");
    private By mobileField = By.cssSelector("input[name='mobile']");
    private By genderDropdown = By.cssSelector("select[name='gender']");
    private By addressField = By.cssSelector("textarea[name='address']");
    private By cityDropdown = By.cssSelector("select#city");
    private By countryDropdown = By.cssSelector("select#country");

    // Payment Information
    private By paymentMethodDropdown = By.cssSelector("select[name='payment']");

    // Credit Card Fields (visible when Credit Card selected)
    private By cardInfoSection = By.cssSelector("#card-info");
    private By cardNumberField = By.cssSelector("input[name='cardnumber']");
    private By cardNameField = By.cssSelector("input[name='cardname']");
    private By cardExpiryField = By.cssSelector("input[name='expiry']");
    private By cardCvvField = By.cssSelector("input[name='cvc']");

    // PayPal Fields (visible when PayPal selected)
    private By paypalSection = By.cssSelector("#paypal");
    private By paypalEmailField = By.cssSelector("input#paypalEmail");

    // Submit & Messages
    private By submitButton = By.cssSelector("button[type='submit']");
    private By successNotification = By.cssSelector("#successNotification");
    private By validationMessage = By.cssSelector(".notification, .error");

    public CheckoutPage(WebDriver driver, String url, int seconds) {
        super(driver, seconds, url);
    }

    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(checkoutForm);
    }

    public void openCheckoutPage() {
        open();
    }

    // ============================================
    // Form Filling Methods - Shipping Info
    // ============================================

    public void fillFullName(String fullName) {
        clearAndSendKeys(fullNameField, fullName);
    }

    public void fillEmail(String email) {
        clearAndSendKeys(emailField, email);
    }

    public void fillPhone(String phone) {
        clearAndSendKeys(phoneField, phone);
    }

    public void fillMobile(String mobile) {
        clearAndSendKeys(mobileField, mobile);
    }

    public void fillZipCode(String zipCode) {
        clearAndSendKeys(zipCodeField, zipCode);
    }

    public void fillAddress(String address) {
        clearAndSendKeys(addressField, address);
    }

    public void selectGender(String gender) {
        selectByVisibleText(genderDropdown, gender);
    }

    public void selectCity(String city) {
        selectByVisibleText(cityDropdown, city);
    }

    public void selectCountry(String country) {
        selectByVisibleText(countryDropdown, country);
    }

    public void fillBasicCheckoutInfo(String fullName, String email, String phone,
            String mobile, String zipCode, String address, String gender) {
        fillFullName(fullName);
        fillEmail(email);
        fillPhone(phone);
        fillMobile(mobile);
        fillZipCode(zipCode);
        fillAddress(address);
        selectGender(gender);
    }

    // ============================================
    // Payment Methods
    // ============================================

    public void selectCashOnDelivery() {
        selectByValue(paymentMethodDropdown, "cod");
    }

    public void selectCreditCard() {
        selectByValue(paymentMethodDropdown, "card");
    }

    public void selectPayPal() {
        selectByValue(paymentMethodDropdown, "paypal");
    }

    public void fillCreditCardInfo(String cardNumber, String cardName, String expiry, String cvv) {
        waitForElementToBeVisible(cardInfoSection);
        clearAndSendKeys(cardNumberField, cardNumber);
        clearAndSendKeys(cardNameField, cardName);
        clearAndSendKeys(cardExpiryField, expiry);
        clearAndSendKeys(cardCvvField, cvv);
    }

    public void fillPayPalInfo(String paypalEmail) {
        waitForElementToBeVisible(paypalSection);
        clearAndSendKeys(paypalEmailField, paypalEmail);
    }

    // ============================================
    // Submit & Validation Methods
    // ============================================

    public void clickSubmitOrder() {
        click(submitButton);
    }

    public boolean isOrderPlacedSuccessfully() {
        return isElementDisplayed(successNotification);
    }

    public String getSuccessMessage() {
        if (isElementDisplayed(successNotification)) {
            return getElementText(successNotification);
        }
        return "";
    }

    public boolean isValidationMessageDisplayed() {
        return isElementDisplayed(validationMessage);
    }

    public String getValidationMessage() {
        if (isElementDisplayed(validationMessage)) {
            return getElementText(validationMessage);
        }
        return "";
    }

    public boolean isStillOnCheckoutPage() {
        return isPageLoaded();
    }

    public String getCheckoutPageTitle() {
        return getPageTitle();
    }

    public boolean isCardInfoVisible() {
        return isElementDisplayed(cardInfoSection);
    }

    public boolean isPayPalSectionVisible() {
        return isElementDisplayed(paypalSection);
    }
}
