package com.cartify.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    // Buttons
    @FindBy(id = "next1")
    private WebElement nextButton1;

    @FindBy(id = "next2")
    private WebElement nextButton2;

    @FindBy(id = "next3")
    private WebElement nextButton3; // Assuming there is a 3rd one or submit is next

    @FindBy(id = "submit")
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
        if (isMale) {
            click(maleRadio);
        } else {
            click(femaleRadio);
        }
    }

    public void fillDate(String date) {
        type(birthDateInput, date);
        driver.findElement(By.tagName("body")).click(); // Click empty space to close date picker
    }

    public void clickNext() {
        click(nextButton2);
    }

    private void clickVisibleNext() {
        // Deprecated or unused now, but keeping if needed for generic next
    }

    public void fillAddress(String street, String city, String state) {
        type(streetAddressInput, street);
        type(cityInput, city);
        type(stateInput, state);
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
