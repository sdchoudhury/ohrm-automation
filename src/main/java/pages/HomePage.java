package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import appSpecific.Wait;


public class HomePage {
	
	public HomePage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using="//p[text()='Quick Launch']")
	private WebElement welcomeMsg;	
	
	@FindBy(how = How.XPATH, using="//h6[text()='Dashboard']")	
	private WebElement dashBoardLabel;	
	
	@FindBy(how = How.XPATH, using="//a[text()='Logout']")
	private WebElement logOutBtn;	
	
	@FindBy(how = How.CLASS_NAME,using="oxd-userdropdown-name")
	private WebElement usrNameIcon;
	
	public WebElement getWelcomeMsg() {
		return welcomeMsg;
		
	}
	
	public WebElement getdashBoardLabel() {
		return dashBoardLabel;
		
	}
		
	public void logOutFromApp(WebDriver driver, int waitTime) {
			Wait.waitForElementToBeClickable(driver,usrNameIcon, waitTime);
			if(welcomeMsg.isDisplayed()) {
				usrNameIcon.click();
				logOutBtn.click();
			}
			
	}

}
