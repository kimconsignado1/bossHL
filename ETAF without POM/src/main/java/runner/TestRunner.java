package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.TestNG;
import org.testng.collections.Lists;
import org.xml.sax.SAXException;

public class TestRunner {
	public static String env;
	public static String browser;
	public static String headless;
	public static String xml;

	public static void main(String[] args)
			throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		TestNG testng = new TestNG();

		if (args.length != 0) {
			env = args[0];
			browser = args[1];
			headless = args[2];
			xml = args[3];

			List<String> suites = Lists.newArrayList();
			suites.add("testcases" + File.separator + args[2] + ".xml");
			testng.setTestSuites(suites);
			testng.setGroups(args[3]);
			testng.run();
		}
	}
}
