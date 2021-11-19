package helper;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextHelper {

    private static final Logger logger = Logger.getLogger(TextHelper.class);
    
    private TextHelper(){
        
    }

    public static void enterText(WebDriver driver, By elementLocator, String text) {

        logger.info("Entering the text into the field " + elementLocator + " :: enterText");
        try {
            driver.findElement(elementLocator).clear();
            driver.findElement(elementLocator).sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to enter the text to the element " + elementLocator+" :: enterText");
            throw (e);
        }
    }

    public static void enterTextUsingJS(WebDriver driver, By elementLocator, String text) {

        logger.info("Entering the text into the field " + elementLocator + " :: enterText");
        try {
            WebElement element = driver.findElement(elementLocator);
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].value='12345678911'", element);
//            driver.findElement(elementLocator).clear();
//            driver.findElement(elementLocator).sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to enter the text to the element " + elementLocator+" :: enterText");
            throw (e);
        }
    }

    public static String getText(WebDriver driver, By elementLocator, String parameter) {

        logger.info("Getting the text from the given element "+elementLocator+" :: getText: ");
        try {
            if (parameter.equals("text")) {
                return driver.findElement(elementLocator).getText();
            }else if(parameter.equals("value")){
                return driver.findElement(elementLocator).getAttribute("value");
            }else{
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get the text from the element " + elementLocator+" :: getText");
            throw (e);
        }
    }
}
