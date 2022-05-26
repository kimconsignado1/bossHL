package tricentis;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import core.ActionDriver;
import core.Hooks;
import utilities.ConfigReader;

public class TC0001 extends Hooks {
	public static String invalidUser = "testAutomation";
	public static String validUser = "testAutomation@test.com";
	public static String validPassword = "abc123!@";
	
	@Test(description="User is unable to login in Tricentis WebShop", 
			groups = {"Regression"})
	public static void TestCase001()
	{
		ActionDriver.WebActions.navigateToURL(ConfigReader.getStringProps("GoogleURL"));
		ActionDriver.WebActions.setText("Search Field", By.xpath("//input[@name='q']"), "Test Search");
		ActionDriver.WebActions.clickElement("Search Button", By.xpath("(//input[@value='Google Search'])[1]"));
		
	}

}
