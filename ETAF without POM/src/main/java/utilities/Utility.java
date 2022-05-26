package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.os.WindowsUtils;

public class Utility {
	
	// Create Directory
	public static void createDirectory(String directoryName) {
		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void killProcess(String browser) {
		switch (browser) {
		case "chrome":
			WindowsUtils.killByName("chromedriver.exe");
			break;
		case "firefox":
			WindowsUtils.killByName("gecko.exe");
			break;
		case "ie":
			WindowsUtils.killByName("IEDriverServer.exe");
			break;
		}
	}
	
	public static void cleanDirectory(String dirParam) {
		File dir = new File(dirParam);
		if (dir.isDirectory()) {
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
