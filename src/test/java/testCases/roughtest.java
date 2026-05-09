package testCases;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;

import javax.swing.*;
import java.time.Duration;
import java.util.List;


public class roughtest {


    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://demoqa.com/browser-windows");

        driver.manage().window().maximize();

        // Click Widgets
        WebElement widgets =
                driver.findElement(By.xpath("//div[text()='Widgets']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView(true);", widgets);

        js.executeScript("arguments[0].click();", widgets);

        // Wait and click Select Menu
        WebElement selectMenu = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[text()='Select Menu']")
                )
        );

        selectMenu.click();
        WebElement sel = driver.findElement(By.id("react-select-2-placeholder"));
        Select dropdown = new Select(sel);
        dropdown.selectByIndex(0);
        List<WebElement> values = dropdown.getOptions();
        for (WebElement value : values) {
            System.out.println(value.getText());
        }
        Thread.sleep(2000);

       driver.quit();
    }
}
