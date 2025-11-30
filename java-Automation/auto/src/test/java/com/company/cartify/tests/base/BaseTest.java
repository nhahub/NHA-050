package com.company.cartify.tests.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import org.openqa.selenium.PageLoadStrategy;


/**
 * BaseTest: الفئة الأساسية لجميع الاختبارات
 * 
 * المشكلة: كل Test Class يحتاج setup/teardown للـ WebDriver - تكرار الكود
 * الحل: تجميع كل منطق إعداد الاختبارات في مكان واحد
 * 
 * الفوائد المعمارية:
 * 1. DRY Principle: كود الـ setup يُكتب مرة واحدة فقط
 * 2. Consistency: كل الاختبارات تستخدم نفس الإعدادات
 * 3. Maintainability: تغيير إعداد WebDriver في مكان واحد ينطبق على جميع
 * الاختبارات
 * 4. Clean Tests: الـ Test Classes تركز فقط على منطق الاختبار - لا setup code
 */
public class BaseTest {

    protected WebDriver driver;
    protected int defaultTimeout = 10; // ثواني

    // Configuration - عنوان التطبيق الفعلي
    protected String baseUrl = "https://cartify0.netlify.app";
    protected String cartUrl = baseUrl + "/cartpage"; // الرابط الصحيح للسلة
    protected String productsUrl = baseUrl + "/products";
    protected String checkoutUrl = baseUrl + "/checkout";

    /**
     * Setup قبل كل Test Method
     * يتم تنفيذه تلقائياً قبل أي @Test
     * 
     * الفائدة: كل test يبدأ بـ browser جديد ونظيف - تجنب تداخل الاختبارات
     */
    @BeforeMethod
    public void setUp() {
        // إعداد ChromeDriver options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // فتح المتصفح بحجم كامل
        options.addArguments("--disable-notifications"); // إيقاف الإشعارات
        options.addArguments("--disable-renderer-backgrounding");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        // options.addArguments("--headless"); // تشغيل بدون واجهة - للـ CI/CD

        // تهيئة WebDriver
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    /**
     * Cleanup بعد كل Test Method
     * يتم تنفيذه تلقائياً بعد انتهاء أي @Test
     * 
     * الفائدة: تنظيف الموارد (إغلاق Browser) تلقائياً - تجنب تسريب الذاكرة
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        // أخذ screenshot عند فشل الاختبار فقط
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshotOnFailure(result.getName());
        }

        // إغلاق المتصفح
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * أخذ screenshot عند فشل الاختبار
     * الفائدة: توثيق الفشل للـ debugging
     */
    private void takeScreenshotOnFailure(String testName) {
        try {
            org.openqa.selenium.TakesScreenshot screenshot = (org.openqa.selenium.TakesScreenshot) driver;
            java.io.File srcFile = screenshot.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            java.io.File destFile = new java.io.File("screenshots/failed_" + testName + ".png");
            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    /**
     * Helper method: الانتظار لثوانٍ محددة
     * الفائدة: للحالات الخاصة التي تحتاج انتظار ثابت (يُفضل تجنبه)
     */
    protected void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
