package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenShotUtil {

    public static String captureScreenshot(WebDriver driver, String testName){
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") + "/test-output/screenshots/" + testName + ".png";
        File target = new File(dest);
        try {
            Files.createDirectories(target.getParentFile().toPath());
            Files.copy(src.toPath(), target.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;
    }
}