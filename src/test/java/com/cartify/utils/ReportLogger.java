package com.cartify.utils;

import com.aventstack.extentreports.Status;
import com.cartify.listeners.TestListener;

public class ReportLogger {
    public static void log(String message) {
        if (TestListener.getTest() != null) {
            TestListener.getTest().log(Status.INFO, message);
        }
        System.out.println(message); // Also log to console
    }
}
