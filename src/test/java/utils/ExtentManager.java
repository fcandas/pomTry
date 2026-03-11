package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if(extent == null){
            String path = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setDocumentTitle("Automation Report");
            reporter.config().setReportName("Selenium POM Test Report");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Fatih Candas");
            extent.setSystemInfo("Environment", "QA");
        }

        return extent;
    }
}