package utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	private static Properties properties;
	
	public static String getStringProps(String value) {
		properties = new Properties();
		try {
			InputStream fs = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
			properties.load(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return properties.getProperty(value);
	}
	
	public static Integer getIntProps(String value) {
		properties = new Properties();
		try {
			InputStream fs = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
			properties.load(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Integer.parseInt(properties.getProperty(value));
	}
}
