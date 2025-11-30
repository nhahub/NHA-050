package com.company.cartify.tests.cartify.base;

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
 * BasePage: الفئة الأساسية لجميع صفحات الـ Page Object Model
 * 
 * المشكلة: تكرار كود الانتظار، النقر، والإدخال في كل صفحة
 * الحل: تجميع كل الوظائف المشتركة في مكان واحد (DRY Principle)
 * 
 * الفوائد المعمارية:
 * 1. Reusability: كل Page ترث هذه الوظائف تلقائياً
 * 2. Maintainability: أي تعديل يتم في مكان واحد فقط
 * 3. Consistency: نفس طريقة التعامل مع العناصر في كل الصفحات
 * 4. Error Handling: معالجة الأخطاء موحدة في مكان واحد
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String url;

    /**
     * Constructor مشترك لجميع الصفحات
     * 
     * @param webdriver instance من WebDriver
     * @param seconds   وقت الانتظار الافتراضي
     * @param url       عنوان الصفحة
     */
    public BasePage(WebDriver webdriver, int seconds, String url) {
        this.driver = webdriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        this.url = url;
    }

    /**
     * فتح الصفحة
     */
    protected void open() {
        driver.get(this.url);
    }

    /**
     * Method مجردة - كل صفحة تطبقها بطريقتها
     * الفائدة: إجبار كل Page على تعريف كيف نتحقق من تحميلها
     */
    public abstract boolean isPageLoaded();

    // ============================================
    // Wait Methods - طرق الانتظار
    // ============================================

    /**
     * الانتظار حتى يظهر العنصر
     * الفائدة: تجنب NoSuchElementException - يضمن أن العنصر موجود قبل التفاعل معه
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not visible after waiting: " + locator.toString(), e);
        }
    }

    /**
     * الانتظار حتى يصبح العنصر قابل للنقر
     * الفائدة: تجنب ElementNotInteractableException - يضمن أن العنصر جاهز للتفاعل
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not clickable after waiting: " + locator.toString(), e);
        }
    }

    /**
     * الانتظار حتى يختفي العنصر
     * الفائدة: مفيد للـ Loading Spinners والنوافذ المنبثقة
     */
    protected boolean waitForElementToDisappear(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * الانتظار حتى يظهر النص في العنصر
     */
    protected boolean waitForTextToBePresentInElement(By locator, String text) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * الانتظار حتى يحتوي الرابط على نص معين
     */
    public boolean waitForUrlToContain(String fraction) {
        try {
            return wait.until(ExpectedConditions.urlContains(fraction));
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ============================================
    // Click Methods - طرق النقر
    // ============================================

    /**
     * النقر على عنصر مع الانتظار
     * الفائدة: دمج الانتظار مع النقر في method واحدة - يقلل السطور في Page Classes
     */
    protected void click(By locator) {
        try {
            waitForElementToBeClickable(locator).click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click on element: " + locator.toString(), e);
        }
    }

    /**
     * النقر باستخدام JavaScript
     * الفائدة: يحل مشكلة العناصر المخفية أو المحجوبة بعناصر أخرى
     */
    protected void clickWithJS(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click with JS: " + locator.toString(), e);
        }
    }

    /**
     * النقر المزدوج
     */
    protected void doubleClick(By locator) {
        try {
            WebElement element = waitForElementToBeClickable(locator);
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            actions.doubleClick(element).perform();
        } catch (Exception e) {
            throw new RuntimeException("Failed to double click: " + locator.toString(), e);
        }
    }

    // ============================================
    // Input Methods - طرق الإدخال
    // ============================================

    /**
     * إدخال نص في حقل
     * الفائدة: الانتظار حتى يكون العنصر جاهز ثم الإدخال
     */
    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys to: " + locator.toString(), e);
        }
    }

    /**
     * مسح النص ثم إدخال نص جديد
     * الفائدة: ضمان أن الحقل فارغ قبل الإدخال - تجنب البيانات القديمة
     */
    protected void clearAndSendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear and send keys: " + locator.toString(), e);
        }
    }

    // ============================================
    // Verification Methods - طرق التحقق
    // ============================================

    /**
     * فحص إذا كان العنصر ظاهر
     * الفائدة: للـ Assertions في الـ Tests
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * فحص إذا كان العنصر مفعّل
     */
    protected boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * الحصول على نص العنصر
     * الفائدة: للتحقق من المحتوى في Tests
     */
    protected String getElementText(By locator) {
        try {
            return waitForElementToBeVisible(locator).getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text from: " + locator.toString(), e);
        }
    }

    /**
     * الحصول على قيمة attribute
     */
    protected String getElementAttribute(By locator, String attribute) {
        try {
            return waitForElementToBeVisible(locator).getAttribute(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attribute: " + locator.toString(), e);
        }
    }

    /**
     * الحصول على جميع العناصر المطابقة
     */
    protected List<WebElement> getElements(By locator) {
        try {
            return driver.findElements(locator);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get elements: " + locator.toString(), e);
        }
    }

    // ============================================
    // Scroll Methods - طرق التمرير
    // ============================================

    /**
     * التمرير إلى عنصر
     * الفائدة: ضمان ظهور العنصر في الشاشة قبل التفاعل معه
     */
    protected void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500); // انتظار قصير بعد التمرير
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to element: " + locator.toString(), e);
        }
    }

    /**
     * التمرير لأعلى الصفحة
     */
    protected void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    /**
     * التمرير لأسفل الصفحة
     */
    protected void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ============================================
    // Dropdown Methods - طرق القوائم المنسدلة
    // ============================================

    /**
     * اختيار من قائمة منسدلة بالنص المرئي
     */
    protected void selectByVisibleText(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select by text: " + locator.toString(), e);
        }
    }

    /**
     * اختيار من قائمة منسدلة بالقيمة
     */
    protected void selectByValue(By locator, String value) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select by value: " + locator.toString(), e);
        }
    }

    /**
     * اختيار من قائمة منسدلة بالترتيب
     */
    protected void selectByIndex(By locator, int index) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select select = new Select(element);
            select.selectByIndex(index);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select by index: " + locator.toString(), e);
        }
    }

    // ============================================
    // Screenshot Methods - طرق لقطة الشاشة
    // ============================================

    /**
     * أخذ لقطة شاشة
     * الفائدة: للتوثيق والـ Debugging عند فشل الاختبارات
     */
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

    /**
     * الحصول على عنوان الصفحة الحالية
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * الحصول على الـ URL الحالي
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
