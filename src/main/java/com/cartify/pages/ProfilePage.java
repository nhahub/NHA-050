package com.cartify.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage extends BasePage {

    @FindBy(css = ".user-name")
    private WebElement profileName;

    @FindBy(css = ".user-email")
    private WebElement profileEmail;

    @FindBy(id = "save-btn") // Placeholder
    private WebElement saveButton;

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://cartify0.netlify.app/profile");
    }

    public boolean isProfileLoaded() {
        return isDisplayed(profileName);
    }

    public String getProfileName() {
        return getText(profileName);
    }

    public String getProfileEmail() {
        return getText(profileEmail);
    }
}
