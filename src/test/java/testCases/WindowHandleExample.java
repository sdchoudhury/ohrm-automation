package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class WindowHandleExample {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/browser-windows");

        // Store parent window
        String parentWindow = driver.getWindowHandle();

        // Click button that opens new window
        driver.findElement(By.id("windowButton")).click();

        // Get all window handles
        Set<String> allWindows = driver.getWindowHandles();

        for(String window : allWindows){

            if(!window.equals(parentWindow)){

                driver.switchTo().window(window);

                System.out.println("Child Window Title: " + driver.getTitle());

                driver.close(); // close child window
            }
        }

        // Switch back to parent window
        driver.switchTo().window(parentWindow);

        System.out.println("Parent Window Title: " + driver.getTitle());

        driver.quit();
    }
}