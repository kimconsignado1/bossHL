package core;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import selenium.BrowserFactory;
import utilities.ConfigReader;
import utilities.ReportManager;
import utilities.Utility;

public class ActionDriver {
	
	protected static ThreadLocal<Integer> step = new ThreadLocal<Integer>();
	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	
	private static WebElement getElement(By locator) {
		WebElement element = getDriver().findElement(locator);
		return element;
	}
	
	private static void logToReporter(String desc) {
		ReportManager.testPass("Step " + step.get() + " - " + desc);
		step.set(step.get() + 1);
	}

	public synchronized static String captureScreenShot(String path) throws Exception {		
		/*File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		path = path + System.currentTimeMillis() + ".png";
		FileUtils.copyFile(scrFile, new File(path));*/
		
		Utility.createDirectory(path);
		path = path + System.currentTimeMillis() + ".png";
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(ActionDriver.getDriver());
		ImageIO.write(screenshot.getImage(), "PNG", new File(path));

		return path;
	}	
	
	public static void closeInstance() {
		driver.get().quit();
		driver.remove();
	}
	
	public static void createInstance(String browser) {
		step.set(1);
		driver.set(BrowserFactory.launchApplication(browser));
	}
	
	public static WebDriver getDriver() {
		return driver.get();
	}	
	
	public static class WebActions {
		public static void clearText(String elementName, By locator) {
			getElement(locator).clear();
			logToReporter(elementName + " is cleared.");
			
		}
		
		// Verify Element is displayed
		public static void assertElementDisplayed(String elementName, By locator) {
			Assert.assertTrue(getElement(locator).isDisplayed());
			logToReporter(elementName + " is displayed.");
		}	
		
		// Click Element
		public static void clickElement(String elementName, By locator) {
			getElement(locator).click();
			logToReporter(elementName + " was clicked.");
		}
		
		public static void navigateToURL(String value) {
			getDriver().get(value);
		}
		
		// Set Password
			public static void setPassword(String elementName, By locator, String value) {
				getElement(locator).sendKeys(value);
				logToReporter(elementName + " is populated with ********.");
			}

		// Set Text
		public static void setText(String elementName, By locator, String value) {
			getElement(locator).sendKeys(value);
			logToReporter(elementName + " is populated with " + value + ".");
		}

		// Wait for Element to be clickable
		public static void waitElementClickable(String elementName, By locator) {
			WebDriverWait wait = new WebDriverWait(getDriver(), 5);
			wait.until(ExpectedConditions.elementToBeClickable((By) getElement(locator)));
			getDriver().manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
			logToReporter(elementName + " is clickable.");
		}

		// Wait until element is not visible
		public static void waitElementNotVisible(String elementName, By locator) {
			WebDriverWait wait = new WebDriverWait(getDriver(), ConfigReader.getIntProps("longWait"));
			wait.until(ExpectedConditions.invisibilityOfElementLocated((By) getElement(locator)));
			logToReporter(elementName + " is not visible.");
		}

		// Wait until element is visible
		public static void waitElementVisible(String elementName, By locator) {
			WebDriverWait wait = new WebDriverWait(getDriver(), ConfigReader.getIntProps("midWait"));
			wait.until(ExpectedConditions.visibilityOf(getElement(locator))).isDisplayed();
			getDriver().manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
			logToReporter(elementName + " is visible.");
		}

		// Wait for page to load completely
		public static void waitPageLoadComplete() {
			new WebDriverWait(getDriver(), 60).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
					.executeScript("return document.readyState").equals("complete"));
		}
	}
}
