package core;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utilities.ReportManager;
import utilities.Utility;

public class Hooks {
	public static String browser = "Chrome";

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		Utility.killProcess(browser);
		ReportManager.createInstance();
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		ActionDriver.createInstance(browser);
		Test test = method.getAnnotation(Test.class);
		ReportManager.createTest(method.getName(), test.description());
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String testResult = result.getThrowable().toString();
			ReportManager.testFail(testResult);
			ReportManager.logger.info(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			ReportManager.testSkip(result.getThrowable().toString());
			ReportManager.removeTest();
		}
		ReportManager.closeInstance();
		ActionDriver.closeInstance();
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		ReportManager.terminateReport();
	}

}
