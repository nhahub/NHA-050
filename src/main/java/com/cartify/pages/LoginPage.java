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

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }
    
    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
