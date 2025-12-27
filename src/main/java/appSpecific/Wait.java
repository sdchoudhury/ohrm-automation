package appSpecific;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {
	
	
	public static void waitForPageToLoad(WebDriver driver, int timeout){
		//ReporterLogs.log("Wait for page to load", "info");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
	}
	
	
	public static void waitForElementToBeClickable(WebDriver driver, WebElement wbElement, long waitSeconds){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(wbElement));
        }
	
	public static void waitForElementToBeVisible(WebDriver driver, WebElement element, long waitSeconds){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
        }
	
	public static void waitForTitleIs(WebDriver driver, String wbTitle, long waitSeconds) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.titleIs(wbTitle));
	}


}
