import org.openqa.selenium.By;

import core.ActionDriver;

public class Template {
	public static class ObjectNameControl {
		private static String elementName = "Objectname control";
		private static By locator = By.xpath("selector");

		public static void assertElementDisplayed() {ActionDriver.WebActions.assertElementDisplayed(elementName, locator);}
		public static void clearText() {ActionDriver.WebActions.clearText(elementName, locator);}
		public static void clickElement() {ActionDriver.WebActions.clickElement(elementName, locator);}
		public static void setPassword(String value) {ActionDriver.WebActions.setText(elementName, locator, value);}
		public static void setText(String value) {ActionDriver.WebActions.setText(elementName, locator, value);}
		public static void waitElementClickable() {ActionDriver.WebActions.waitElementClickable(elementName, locator);}
		public static void waitElementNotVisible() {ActionDriver.WebActions.waitElementNotVisible(elementName, locator);}
		public static void waitElementVisible() {ActionDriver.WebActions.waitElementVisible(elementName, locator);}
	}
}
