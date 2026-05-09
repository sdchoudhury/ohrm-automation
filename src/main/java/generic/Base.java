package generic;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Base {

    // Thread-safe WebDriver
    private final static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Initialize driver based on browser
    public static void initDriver(String browser) {

        if (driver.get() == null) {

            switch (browser.toLowerCase()) {

                case "chrome":

                    ChromeOptions chromeOptions = new ChromeOptions();

                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--window-size=1920,1080");

                    driver.set(new ChromeDriver(chromeOptions));

                    break;

                case "firefox":

                    FirefoxOptions firefoxOptions = new FirefoxOptions();

                    firefoxOptions.addArguments("--headless");

                    driver.set(new FirefoxDriver(firefoxOptions));

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

        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    // Quit driver safely
    public static void quitDriver() {

        if (driver.get() != null) {

            driver.get().quit();

            driver.remove();
        }
    }
}