package com.cartify.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomTestListener implements ITestListener {

    private List<ITestResult> passedTests = new ArrayList<>();
    private List<ITestResult> failedTests = new ArrayList<>();
    private List<ITestResult> skippedTests = new ArrayList<>();

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests.add(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests.add(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests.add(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        generateSummaryReport();
        if (!failedTests.isEmpty()) {
            generateBugReport();
        }
    }

    private void generateSummaryReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("summary_report.txt"))) {
            writer.write("Test Execution Summary Report");
            writer.newLine();
            writer.write("=============================");
            writer.newLine();
            writer.write("Total Tests Run: " + (passedTests.size() + failedTests.size() + skippedTests.size()));
            writer.newLine();
            writer.write("Passed: " + passedTests.size());
            writer.newLine();
            writer.write("Failed: " + failedTests.size());
            writer.newLine();
            writer.write("Skipped: " + skippedTests.size());
            writer.newLine();
            writer.newLine();

            writer.write("Detailed Results:");
            writer.newLine();
            for (ITestResult result : passedTests) {
                writer.write("[PASS] " + result.getName());
                writer.newLine();
            }
            for (ITestResult result : failedTests) {
                writer.write("[FAIL] " + result.getName());
                writer.newLine();
            }
            for (ITestResult result : skippedTests) {
                writer.write("[SKIP] " + result.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateBugReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bug_report.txt"))) {
            writer.write("Bug Report");
            writer.newLine();
            writer.write("==========");
            writer.newLine();

            for (ITestResult result : failedTests) {
                writer.write("Test Case: " + result.getName());
                writer.newLine();
                writer.write("Class: " + result.getTestClass().getName());
                writer.newLine();
                writer.write("Exception: " + result.getThrowable());
                writer.newLine();

                // Stack Trace
                writer.write("Stack Trace:");
                writer.newLine();
                for (StackTraceElement element : result.getThrowable().getStackTrace()) {
                    writer.write("\tat " + element.toString());
                    writer.newLine();
                }
                writer.write("--------------------------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
