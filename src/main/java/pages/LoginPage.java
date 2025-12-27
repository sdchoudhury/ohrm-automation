package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.NAME, using="username")
	private WebElement usrNameEdt;	
	
	@FindBy(how = How.NAME, using="password")
	private WebElement passwordEdt;	
	
	@FindBy(how = How.CLASS_NAME, using="oxd-button")
	private WebElement loginBtn;	
	
	@FindBy(how = How.XPATH, using="//p[text()='Invalid credentials']")
	private WebElement errorPromptForInvalidCredential;
	
	public WebElement getInvalidCredentialMsg() {
		return errorPromptForInvalidCredential;
	}
	
	public void loginToApp(String usr, String pwd) {
        usrNameEdt.clear();
        usrNameEdt.sendKeys(usr);
        passwordEdt.clear();
        passwordEdt.sendKeys(pwd);
        loginBtn.click();
	}
	
	public String getTitle(WebDriver driver) {
        return driver.getTitle();
	}

}
