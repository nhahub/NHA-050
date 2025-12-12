package com.cartify.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * BasePage: Unified Base Page for all Page Objects.
 * Supports both By locators and PageFactory WebElements.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String url;

    /**
     * Constructor for pages using By locators (from auto module)
     */
    public BasePage(WebDriver webdriver, int seconds, String url) {
        this.driver = webdriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        this.url = url;
    }

    /**
     * Constructor for pages using PageFactory (from cartify-automation module)
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.url = "";
    }

    protected void open() {
        if (this.url != null && !this.url.isEmpty()) {
            driver.get(this.url);
        }
    }

    /**
     * Optional: Override in child pages to verify page load.
     */
    public boolean isPageLoaded() {
        return true;
    }

    // ============================================
    // Methods for WebElements (PageFactory)
    // ============================================

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ============================================
    // Methods for By Locators
    // ============================================

    protected WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not visible after waiting: " + locator.toString(), e);
        }
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not clickable after waiting: " + locator.toString(), e);
        }
    }

    protected boolean waitForElementToDisappear(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean waitForTextToBePresentInElement(By locator, String text) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean waitForUrlToContain(String fraction) {
        try {
            return wait.until(ExpectedConditions.urlContains(fraction));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void click(By locator) {
        try {
            waitForElementToBeClickable(locator).click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click on element: " + locator.toString(), e);
        }
    }

    protected void clickWithJS(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click with JS: " + locator.toString(), e);
        }
    }

    protected void doubleClick(By locator) {
        try {
            WebElement element = waitForElementToBeClickable(locator);
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            actions.doubleClick(element).perform();
        } catch (Exception e) {
            throw new RuntimeException("Failed to double click: " + locator.toString(), e);
        }
    }

    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys to: " + locator.toString(), e);
        }
    }

    protected void clearAndSendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear and send keys: " + locator.toString(), e);
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected String getElementText(By locator) {
        try {
            return waitForElementToBeVisible(locator).getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text from: " + locator.toString(), e);
        }
    }

    protected String getElementAttribute(By locator, String attribute) {
        try {
            return waitForElementToBeVisible(locator).getAttribute(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attribute: " + locator.toString(), e);
        }
    }

    protected List<WebElement> getElements(By locator) {
        try {
            return driver.findElements(locator);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get elements: " + locator.toString(), e);
        }
    }

    protected void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to element: " + locator.toString(), e);
        }
    }

    protected void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected void selectByVisibleText(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select by text: " + locator.toString(), e);
        }
    }

    protected void selectByValue(By locator, String value) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select by value: " + locator.toString(), e);
        }
    }

    protected void selectByIndex(By locator, int index) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select select = new Select(element);
            select.selectByIndex(index);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select by index: " + locator.toString(), e);
        }
    }

    protected void takeScreenshot(String fileName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("screenshots/" + fileName + ".png");
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to take screenshot: " + fileName, e);
        }
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
