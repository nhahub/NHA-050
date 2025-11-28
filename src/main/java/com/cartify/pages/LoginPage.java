package com.cartify.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "btn")
    private WebElement loginButton;

    @FindBy(xpath = "//a[contains(@href, 'login.html')]") // "Forget password?" link
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//*[contains(text(), 'Wrong username or password')]")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://cartify0.netlify.app/login.html");
    }

    @FindBy(id = "rememberMe")
    private WebElement rememberMeCheckbox;

    public void login(String username, String password) {
        login(username, password, false);
    }

    public void login(String username, String password, boolean rememberMe) {
        type(usernameInput, username);
        type(passwordInput, password);
        if (rememberMe && !rememberMeCheckbox.isSelected()) {
            // JS click for checkbox if needed, or standard click
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();",
                    rememberMeCheckbox);
        }
        // Try JS click
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
