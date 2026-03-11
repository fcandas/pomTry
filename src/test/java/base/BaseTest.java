package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import utils.ConfigReader;

public class BaseTest {

    public WebDriver driver;

    @BeforeMethod
    public void setup(){

        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("url");

        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.get(url);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}