package utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Resources;

import core.ActionDriver;
import core.Hooks;

public class ReportManager {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");;
	private static Date date = new Date();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentReports extent;
	public static Logger logger = LogManager.getLogger(Hooks.class);
	private static String projectName = ConfigReader.getStringProps("projectName");
	private static String htmlReportPath;
	private static String htmlReportFullPath;
	private static String htmlReportSCRPath;
	private static String htmlBackupPath;
	private static String htmlBackupSCRPath;
	
	public static ExtentReports createInstance() {
		String htmlReportName = projectName + " - " + dateFormat.format(date);
		htmlReportPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "htmlReports" + File.separator;
		htmlReportFullPath = htmlReportPath + projectName + ".html";
		htmlReportSCRPath = htmlReportPath + "screenshots" + File.separator;
		
		htmlBackupPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "extentreports" + File.separator + htmlReportName + File.separator;
		htmlBackupSCRPath = htmlBackupPath + "screenshots\\";
		
		Utility.cleanDirectory(htmlReportPath);	
		Utility.createDirectory(htmlReportPath + "logs" + File.separator);
		Utility.createDirectory(htmlBackupPath + "logs" + File.separator);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(htmlReportFullPath);

		URL url = Resources.getResource("extent-config.xml");
		htmlReporter.loadXMLConfig(new File(url.getPath()), true);

		extent = new ExtentReports();
		extent.setSystemInfo("User:", System.getProperty("user.name"));
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.attachReporter(htmlReporter);
		return extent;
	}

	public synchronized static void createTest(String testCase, String testDesc) {
		ThreadContext.put("testPath", htmlReportPath);
		ThreadContext.put("tcName", testCase);
		test.set(extent.createTest(testDesc));
		logger.info("=================================== START TEST ===================================");
		logger.info("Test Case report initialize for " + testCase);
	}
	
	public synchronized static void removeTest() {
		extent.removeTest(test.get());
	}

	public synchronized static void testPass(String desc) {
		test.get().log(Status.PASS, desc);
		logger.info("PASSED - " +  desc);
	}
	
	public synchronized static void testSkip(String desc) {
		test.get().log(Status.FAIL, desc, ReportScreenshot());
		logger.error("SKIPPED - " + desc);
	}

	public synchronized static void testFail(String desc) {
		try {
			test.get().log(Status.FAIL, desc, ReportScreenshot());
		} catch (Exception e) {
			logger.error("Report Screenshot Error Exception: " + e.getMessage());
		}
		logger.error("FAILED - " +  desc);
	}

	public synchronized static void testInfo(String desc) {
		test.get().log(Status.INFO, desc);
		logger.info("INFO - " + desc);
	}
	
	public synchronized static void testScreenshot() {
		test.get().log(Status.INFO, "SCREENSHOT - ", ReportScreenshot());
		logger.info("SCREENSHOT");
	}
	
	
	public synchronized static void testTags(String tags) {
		test.get().assignCategory(tags);
	}

	public synchronized static void closeInstance() {
		logger.info("=================================== END TEST ===================================");
	}
	
	public static void terminateReport() {
		ThreadContext.clearAll();
		extent.flush();
		System.out.println("Test Report Generated: " + htmlReportFullPath);
		
		
		try {
			FileUtils.copyDirectory(new File(htmlReportPath), new File(htmlBackupPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized static MediaEntityModelProvider ReportScreenshot() {
		try {
			Utility.createDirectory(htmlBackupSCRPath);
			Utility.createDirectory(htmlReportSCRPath);
			return MediaEntityBuilder.createScreenCaptureFromPath(ActionDriver.captureScreenShot(htmlReportSCRPath))
					.build();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Report Screenshot Error IOException: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Report Screenshot Error Exception: " + e.getMessage());
		}
		return null;
	}
	
	
}
