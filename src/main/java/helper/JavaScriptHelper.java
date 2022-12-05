package helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptHelper {

    private static final Logger logger = Logger.getLogger(JavaScriptHelper.class);

    private JavaScriptHelper() {
    }

    public static void executeJavaScript(WebDriver driver, String script) {
        logger.info("Waiting for element visibility:: executeJavaScript");
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript(script);
        } catch (Exception e) {
            logger.error("Failed - Element not visible or present :: executeJavaScript ", e);
            throw (e);
        }
    }

    public static void executeJavaScriptOnWebElement(WebDriver driver, String script, WebElement element) {
        logger.info("Waiting for element visibility:: executeJavaScript");
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript(script, element);
        } catch (Exception e) {
            logger.error("Failed - Element not visible or present :: executeJavaScript ", e);
            throw (e);
        }
    }

}
