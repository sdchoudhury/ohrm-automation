package generic;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {

    // Thread-safe WebDriver
    private final static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Initialize driver based on browser
    public static void initDriver(String browser) {

        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver.set(new ChromeDriver());
                    break;
                case "firefox":
                    driver.set(new FirefoxDriver());
                    break;
                default:
                    throw new RuntimeException("Browser not supported: " + browser);
            }
        }
    }

    // Get driver
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new RuntimeException("Driver not initialized. Call initDriver() first!");
        }
        return driver.get();
    }

    // Open URL
    public static void getUrl(String url) {
        getDriver().get(url);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Quit driver safely
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
