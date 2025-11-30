package com.company.cartify.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class main {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        // applying in any findelement
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");

        // press start
        driver.findElement(By.cssSelector("#start button")).click();

        // wait 10sec until catch the element
        String text = driver.findElement(By.cssSelector("#finish h4")).getText();

        System.out.println("Text found: " + text);

        driver.quit();
    }
}