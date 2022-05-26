package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import utilities.ConfigReader;
import utilities.ReportManager;

public class RetryAnalyzer implements IRetryAnalyzer {
	
	int counter = 0;
	int retryLimit = ConfigReader.getIntProps("failedRetries");

	@Override
	public boolean retry(ITestResult result) {
		if (counter < retryLimit) {
			counter++;
			ReportManager.logger.error("FAILED - " + result.getName() + " - Retry Triggered (" + counter + "/" + retryLimit + ").");
			return true;
		}
		return false;
	}
}
