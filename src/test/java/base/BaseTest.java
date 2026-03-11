package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.LoggerUtil;
import utils.ScreenShotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public WebDriver driver;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeMethod
    public void setup() {

        extent = ExtentManager.getInstance();
        test = extent.createTest("Test");

        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("url");

        LoggerUtil.log.info("Launching browser: " + browser);

        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.get(url);

        LoggerUtil.log.info("Navigated to URL: " + url);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if(result.getStatus() == ITestResult.FAILURE){
            LoggerUtil.log.error("Test Failed: " + result.getName());
            String screenshotPath = ScreenShotUtil.captureScreenshot(driver, result.getName());
            test.fail("Test Failed").addScreenCaptureFromPath(screenshotPath);
        } else if(result.getStatus() == ITestResult.SUCCESS){
            LoggerUtil.log.info("Test Passed: " + result.getName());
            test.pass("Test Passed");
        } else if(result.getStatus() == ITestResult.SKIP){
            LoggerUtil.log.warn("Test Skipped: " + result.getName());
            test.skip("Test Skipped");
        }

        if(driver != null){
            driver.quit();
        }
        extent.flush();
    }
}