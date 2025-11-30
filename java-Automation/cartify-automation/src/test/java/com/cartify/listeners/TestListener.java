package com.cartify.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cartify.utils.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.createInstance("target/extent-report.html");
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed");
        test.get().fail(result.getThrowable());

        try {
            Object currentClass = result.getInstance();
            org.openqa.selenium.WebDriver driver = ((com.cartify.tests.BaseTest) currentClass).getDriver();
            if (driver != null) {
                String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    private String captureScreenshot(org.openqa.selenium.WebDriver driver, String screenshotName) {
        String dateName = new java.text.SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date());
        org.openqa.selenium.TakesScreenshot ts = (org.openqa.selenium.TakesScreenshot) driver;
        java.io.File source = ts.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/target/screenshots/" + screenshotName + dateName
                + ".png";
        java.io.File finalDestination = new java.io.File(destination);
        try {
            org.apache.commons.io.FileUtils.copyFile(source, finalDestination);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return destination;
    }
}
