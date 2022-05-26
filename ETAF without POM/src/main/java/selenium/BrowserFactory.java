package selenium;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ConfigReader;

public class BrowserFactory {
	public static WebDriver launchApplication(String browser) {
		WebDriver driver = null;

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			if (ConfigReader.getStringProps("headless").equalsIgnoreCase("yes")) {
				options.addArguments("--headless");
			}
			driver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			if (ConfigReader.getStringProps("headless").equalsIgnoreCase("yes")) {
				FirefoxBinary binOptions = new FirefoxBinary();
				binOptions.addCommandLineOptions("--headless");
				FirefoxOptions options = new FirefoxOptions();
				options.setBinary(binOptions);
				driver = new FirefoxDriver(options);
			} else {
				driver = new FirefoxDriver();
			}
		} else {
			try {
				System.out.println(browser + ": is not correct or blank");
				throw new Exception("NO BROWSER FOUND EXCEPTION");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(ConfigReader.getIntProps("pageLoadTimeout"), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(ConfigReader.getIntProps("implicitlyWait"), TimeUnit.SECONDS);

		return driver;
	}
}
