package appSpecific;


import org.apache.maven.surefire.shared.utils.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        String screenshotDir =
                System.getProperty("user.dir") + "/screenshots/";

        new File(screenshotDir).mkdirs();

        String screenshotPath =
                screenshotDir + testName + "_" + timestamp + ".png";

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(screenshotPath);
            FileUtils.copyFile(src, dest);
            return screenshotPath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
