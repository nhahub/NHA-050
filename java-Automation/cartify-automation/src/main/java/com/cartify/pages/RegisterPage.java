package com.cartify.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    // Step 1: Account Info
    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "pw1")
    private WebElement passwordInput;

    @FindBy(id = "pw2")
    private WebElement confirmPasswordInput;

    // Step 2: Personal Info
    @FindBy(id = "FName")
    private WebElement firstNameInput;

    @FindBy(id = "LName")
    private WebElement lastNameInput;

    @FindBy(id = "telephone")
    private WebElement phoneInput;

    // Step 3: Gender
    @FindBy(css = "label[for='malegen']")
    private WebElement maleRadio;

    @FindBy(css = "label[for='femalegen']")
    private WebElement femaleRadio;

    // Step 4: Date
    @FindBy(id = "datepicker")
    private WebElement birthDateInput;

    // Step 5: Address (inferred from agent logs, might be on same step or next)
    @FindBy(css = "input[placeholder='Street Address']")
    private WebElement streetAddressInput;

    @FindBy(css = "input[placeholder='City']")
    private WebElement cityInput;

    @FindBy(css = "input[placeholder='State']")
    private WebElement stateInput;

    @FindBy(css = "input[placeholder='ZIP Code']")
    private WebElement zipCodeInput;

    @FindBy(css = "input[placeholder='Country']")
    private WebElement countryInput;

    // Buttons
    @FindBy(id = "next1")
    private WebElement nextButton1;

    @FindBy(id = "next2")
    private WebElement nextButton2;

    @FindBy(id = "next3")
    private WebElement nextButton3; // Assuming there is a 3rd one or submit is next

    @FindBy(css = "input[value='Start for free']")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(), 'Wrong') or contains(text(), 'Error') or contains(text(), 'required') or contains(text(), 'Password')]")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://cartify0.netlify.app/register.html");
    }

    public void fillAccountInfo(String email, String username, String password) {
        type(emailInput, email);
        type(usernameInput, username);
        type(passwordInput, password);
        type(confirmPasswordInput, password);
        click(nextButton1);
    }

    public void fillPersonalInfo(String firstName, String lastName, String phone) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(phoneInput, phone);
    }

    public void selectGender(boolean isMale) {
        System.out.println("Selecting gender: " + (isMale ? "Male" : "Female"));
        if (isMale) {
            click(maleRadio);
        } else {
            click(femaleRadio);
        }
        System.out.println("Gender selected");
    }

    public void fillDate(String date) {
        System.out.println("Filling date: " + date);
        type(birthDateInput, date);
        birthDateInput.sendKeys(org.openqa.selenium.Keys.TAB);
        // Click body to close date picker if it's open
        driver.findElement(org.openqa.selenium.By.tagName("body")).click();
        System.out.println("Date filled and tabbed, body clicked");
    }

    public void clickNext() {
        System.out.println("Clicking next2");
        if (isDisplayed(errorMessage)) {
            System.out.println("Error message displayed BEFORE clicking next2: " + getText(errorMessage));
        }
        // Ensure button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(nextButton2));
        // Try JS click
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton2);
        System.out.println("Clicked next2 via JS");

        if (isDisplayed(errorMessage)) {
            System.out.println("Error message displayed AFTER clicking next2: " + getText(errorMessage));
        }
        // Wait for address step to be visible
        try {
            wait.until(ExpectedConditions.visibilityOf(zipCodeInput));
            System.out.println("Zip code input is now visible");
        } catch (Exception e) {
            System.out.println("Zip code input NOT visible after clicking next2");
        }
    }

    public void fillAddress(String street, String city, String state, String zip, String country) {
        System.out.println("Current URL: " + driver.getCurrentUrl());
        if (isDisplayed(errorMessage)) {
            System.out.println("Error message displayed: " + getText(errorMessage));
        }
        type(streetAddressInput, street);
        type(cityInput, city);
        type(stateInput, state);
        type(zipCodeInput, zip);
        type(countryInput, country);
        click(submitButton);
    }

    public boolean isMaleSelected() {
        return maleRadio.isSelected();
    }

    public boolean isFemaleSelected() {
        return femaleRadio.isSelected();
    }

    public String getBirthDate() {
        return birthDateInput.getAttribute("value");
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
