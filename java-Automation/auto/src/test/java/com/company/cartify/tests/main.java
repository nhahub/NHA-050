package com.company.cartify.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class main {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @DataProvider(name = "LoginData")
    public Object[][] getData() {
        return new Object[][] {
                {"tomsmith", "SuperSecretPassword!"},  // Case 1: Valid
                {"wronguser", "wrong123"},             // Case 2: Invalid User
        };
    }

    @Test(dataProvider = "LoginData")
    public void loginTest(String username, String password) {

        System.out.println("Testing with: " + username + " and " + password);

        driver.get("https://the-internet.herokuapp.com/login");

        // DataProvider
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button.radius")).click();

        //??
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
