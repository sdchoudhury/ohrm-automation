package appSpecific;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DropDownUtils {
	


		//Selecting dropdown attribute by using Visible Text
		public static void selectDropdownByVisibleText(WebElement element,String visibleText, String dropDownName)
			{
				try
				{
					//ReporterLogs.log("Selecting Visible Text "+visibleText+" from dropdown : "+ dropDownName,"info");
					Select sel = new Select(element);
					sel.selectByVisibleText(visibleText);
				}
				catch(Exception e)
				{
					//ReporterLogs.log("Unable to select Visible Text "+visibleText+" from dropdown: "+dropDownName + " due to exception : " + e.getCause(), "error");
				}
			}

		//Selecting Dropdown attribute by using Index
		public static void selectDropdownByIndex(WebElement element, int index, String dropDownName)
			{
				try
				{
					//ReporterLogs.log("Selecting options using index : "+index+" from : "+ dropDownName,"info");
					Select sel = new Select(element);
					sel.selectByIndex(index);
				}
				catch(Exception e)
				{
					//ReporterLogs.log("Unable to select values from "+ dropDownName + " due to exception "+e.getCause(), "error");
				}
			}

		//Selecting dropdown attribute by Value
		public static void selectDropdownByValue(WebElement element,String value, String dropDownName)
			{
				try
				{
					//ReporterLogs.log("Selecting "+value+" from dropdown :"+dropDownName, "info");
					Select sel = new Select(element);
					sel.selectByVisibleText(value);
				}
				catch(Exception e)
				{
					//ReporterLogs.log("Exception encountered : " + e.getCause(), "info");
				}
			}

		//Getting the selected Attribute name from drop down
		public static String  getFirstSelectedOptionName(WebElement element, String dropDownName)
		{
			String selectedAttribute="";
			try
			{
				//ReporterLogs.log("Getting the First selected attribute name from dropdown :"+dropDownName, "info");
				Select sel = new Select(element);
				selectedAttribute=sel.getFirstSelectedOption().getText();
				//ReporterLogs.log(selectedAttribute+" is the Selected Attribute in "+dropDownName, "info");
			}
			catch(Exception e)
			{
				//ReporterLogs.log("Unable to get the selected Attribute name from "+dropDownName+" dropdown" + e.getCause(), "error");
			}
			return selectedAttribute;
		}
		
		
}
