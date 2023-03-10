package helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    private static final Logger logger = Logger.getLogger(WaitHelper.class);

    private WaitHelper() {
    }

    public static void pause(long millis) throws InterruptedException {

        Thread.sleep(millis);
    }

    public static void waitForElementVisibility(WebDriver driver, By elementLocator) {
        logger.info("Waiting for element visibility:: waitForElementVisibility");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            logger.info("Element is visible");
        } catch (Exception e) {
            logger.error("Failed - Element not visible or present :: waitForElementVisibility ", e);
            throw (e);
        }
    }

    public static void waitForElementVisibilityCustom(WebDriver driver, By elementLocator, int seconds) throws InterruptedException {
        logger.info("Waits the element for element visibility for given time period:: waitForElementVisibilityCustom");
        try {
            int i = 1;
            int n = seconds/3;
            while(i <= n){
                Thread.sleep(3000);
                boolean result = ClickHelper.isElementExist(driver, elementLocator);
                if (result){
                    break;
                }else{
                    i++;
                }
            }
        } catch (Exception e) {
            logger.error("Failed - Element not visible or present :: waitForElementVisibilityCustom "+ e.getMessage());
            throw (e);
        }
    }

    public static void waitForProgressbarInvisibility(WebDriver driver) throws InterruptedException {

        logger.info("waiting for progressbar to invisible :: waitForProgressbarInvisibility");
        try {
            String progressbarXpath = "//span[@role='progressbar']/parent::div";
            int n = 0;
            while(n<12){
                pause(3000);
                String text = TextHelper.getText(driver, By.xpath(progressbarXpath), "style").trim();
                if(text.contains("hidden")){
                    break;
                }
                n++;
            }
            logger.info("element is visible, i.e progressbar is disappeared");
        } catch (Exception e) {
            logger.error("Failed - Element not visible or present :: waitForElementVisibility "+e.getMessage());
            throw (e);
        }
    }

    public static WebElement waitForStaleElement(WebDriver driver, By elementLocator) {

        logger.info("Handling the stale element :: waitForStaleElement");
        try {
            return driver.findElement(elementLocator);
        } catch (StaleElementReferenceException e) {
            logger.error("Element is stale, trying to find again :: waitForStaleElement");
            return waitForStaleElement(driver, elementLocator);
        }
    }

    public static void waitForElementClickable(WebDriver driver, By elementLocator) {
        logger.info("Waiting for the element till it is clickable :: waitForElementClickable");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (Exception e) {
            logger.error("Failed to wait till the element is clickable :: waitForElementClickable::" + elementLocator);
            throw (e);
        }
    }

    public static boolean isElementDisplayed(WebDriver driver, By elementLocator) {
        logger.info("Checking whether element displayed or not in :: isElementDisplayed");
        try {
            WebElement element = driver.findElement(elementLocator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            logger.error("Element not displayed :: isElementDisplayed");
            throw (e);
        }
    }

    public static boolean isElementEnabled(WebDriver driver, By elementLocator) {
        logger.info("Checking whether element enabled or not in :: isElementEnabled");
        try {
            WebElement element = driver.findElement(elementLocator);
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            logger.error("Element not displayed :: isElementEnabled");
            throw (e);
        }
    }
}
