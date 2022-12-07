package helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class DropdownHelper {

    private static final Logger logger = Logger.getLogger(DropdownHelper.class);

    private DropdownHelper() {
    }

    public static void selectByValueFromDropdown(WebDriver driver, By elementLocator, String value) {
        logger.info("Selecting an option from dropdown using value :: selectByValueFromDropdown " + elementLocator);
        try {
            Select dropdown = new Select(driver.findElement(elementLocator));
            logger.info("The option selecting from dropdown is -" + value);
            dropdown.selectByValue(value);
//            WaitHelper.waitForSpinnerIconInvisibility(driver, LoginPageObjects.spinnerIcon);
        } catch (Exception e) {
            logger.error("Failed to select option from dropdown :: selectByValueFromDropdown");
            throw (e);
        }
    }

    public static void selectByVisibleTextFromDropdown(WebDriver driver, By elementLocator, String value) {
        logger.info("Selecting option from dropdown using visible text :: selectByVisibleTextFromDropdown " + elementLocator);
        try {
            WebElement element = driver.findElement(elementLocator);
            Select dropdown = new Select(element);
            logger.info("The value selecting from dropdown is  " + value);
            dropdown.selectByVisibleText(value);
        } catch (Exception e) {
            logger.error("Failed to select option from dropdown using visible text :: selectByVisibleTextFromDropdown");
            throw (e);
        }
    }

    public static String getFirstSelectedOption(WebDriver driver, By elementLocator) {
        logger.info("Returning  the default or first selected option of a dropdown :: getFirstSelectedOption " + elementLocator);
        try {
            Select dropdown = new Select(driver.findElement(elementLocator));
            return dropdown.getFirstSelectedOption().getText();
        } catch (Exception e) {
            logger.error("Failed to select option from dropdown using visible text :: getFirstSelectedOption");
            throw (e);
        }
    }

    public static int sizeOfSelectedDropdown(WebDriver driver, By elementLocator) {
        logger.info("Returning  the default or first selected option of a dropdown :: selectByIndexFromDropdown " + elementLocator);
        try {
            List<String> listOfOption = getDropdownValues(driver, elementLocator);
            return listOfOption.size();
        } catch (Exception e) {
            logger.error("Failed to select option from dropdown using index :: selectByIndexFromDropdown");
            throw (e);
        }
    }

    public static void selectByIndexFromDropdown(WebDriver driver, By elementLocator, int index) {
        logger.info("Returning  the default or first selected option of a dropdown :: selectByIndexFromDropdown " + elementLocator);
        try {
            Select dropdown = new Select(driver.findElement(elementLocator));
            logger.info("The index selecting from dropdown is  " + index);
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            logger.error("Failed to select option from dropdown using index :: selectByIndexFromDropdown");
            throw (e);
        }
    }

    public static List<String> getDropdownValues(WebDriver driver, By elementLocator) {
        logger.info("Getting the options from dropdown:: getDropdownValues " + elementLocator);
        try {
            Select dropdown = new Select(driver.findElement(elementLocator));
            List<WebElement> elements = dropdown.getOptions();
            logger.info("The size of the dropdown elements:: " + elements.size());
            List<String> options = new ArrayList<>();
            for (WebElement element : elements) {
                options.add(element.getText());
            }
            return options;
        } catch (Exception e) {
            logger.error("Failed to get the options from dropdown:: getDropdownValues");
            throw (e);
        }
    }

    public static boolean selectValueFromBootstrapDropdown(WebDriver driver, WebElement dropdown, By option, String optionValue) throws InterruptedException {
        logger.info("selecting given value from the dropdown:: selectValueFromBootstrapDropdown " + dropdown);
        try {
            dropdown.click();
            WaitHelper.waitForElementVisibilityCustom(driver, option, 30);
            List<WebElement> optionElements = driver.findElements(option);
            List<String> optionValues = new ArrayList<>();
            optionElements.forEach(webElement -> optionValues.add(webElement.getText().trim()));
            if(optionValues.contains(optionValue)){
                if (!optionValue.equalsIgnoreCase("index")) {
                    if (optionElements.size() == 1) {
                        optionElements.get(0).click();
                    } else {
                        for (WebElement opt : optionElements) {
                            String actualValue = opt.getText().trim();
                            if (actualValue.contentEquals(optionValue)) {//
                                ClickHelper.javaScriptExecutorClick2(driver, opt);
//                                opt.click();
                                break;
                            }
                        }
                    }
                }else{
                    optionElements.get(1).click();
                }
                WaitHelper.pause(2000);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            logger.error("Failed to select value from dropdown:: selectValueFromBootstrapDropdown " + e.getMessage());
            throw (e);
        }

    }


}
