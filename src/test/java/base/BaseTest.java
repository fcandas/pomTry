package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
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

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static ExtentTest getTest(){
        return test.get();
    }

    public static ExtentReports extent = ExtentManager.getInstance();

    @BeforeMethod
    public void setup(java.lang.reflect.Method method) {

        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("url");

        LoggerUtil.log.info("Launching browser: " + browser);

        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            WebDriver webDriver = new ChromeDriver();
            driver.set(webDriver);
        }

        getDriver().manage().window().maximize();
        getDriver().get(url);

        LoggerUtil.log.info("Navigated to URL: " + url);

        // Extent Test Thread Safe
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if(result.getStatus() == ITestResult.FAILURE){

            LoggerUtil.log.error("Test Failed: " + result.getName());

            String screenshotPath = ScreenShotUtil.captureScreenshot(getDriver(), result.getName());

            getTest().fail("Test Failed").addScreenCaptureFromPath(screenshotPath);

        }
        else if(result.getStatus() == ITestResult.SUCCESS){

            LoggerUtil.log.info("Test Passed: " + result.getName());

            getTest().pass("Test Passed");

        }
        else if(result.getStatus() == ITestResult.SKIP){

            LoggerUtil.log.warn("Test Skipped: " + result.getName());

            getTest().skip("Test Skipped");
        }

        if(getDriver() != null){
            getDriver().quit();
            driver.remove();
        }

        extent.flush();
    }
}