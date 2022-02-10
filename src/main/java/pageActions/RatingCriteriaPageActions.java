package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.DropdownHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.log4j.Logger;
import static pageObjects.DashboardPageObjects.allStatusDropdown;
import static pageObjects.DashboardPageObjects.statusOptions;
import static pageObjects.RatingCriteriaPageObjects.*;


public class RatingCriteriaPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(RatingCriteriaPageActions.class);

    public boolean isRatingCriteriaPageDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, ratingCriteriaHeader);
       return ClickHelper.isElementExist(driver, ratingCriteriaHeader);

    }

    public WebElement ratingCriteriaExitButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, ratingCriteriaExitButton);
        return driver.findElement(ratingCriteriaExitButton);
    }

    public void clickRatingCriteriaButton(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, ratingCriteriaButton);
        ClickHelper.clickElement(driver, ratingCriteriaButton);
    }

    public void clickRatingCriteriaContinueButton (WebDriver driver) {

        WaitHelper.waitForElementClickable(driver, ratingCriteriaContinueButton);
        ClickHelper.clickElement(driver, ratingCriteriaContinueButton);
    }

    public void clickBusinessClassDropdown(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, businessClassDropDown);
        ClickHelper.clickElement(driver,businessClassDropDown);

    }
    public WebElement ratingCriteriaTitle(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, ratingCriteriaTitle);
        return driver.findElement(ratingCriteriaTitle);
    }

    public void selectBusinessClassInFilter(WebDriver driver, String status) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, allStatusDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, allStatusDropdown, statusOptions, status);
    }

    public void enterRatingCriteriaRevenueAndRecords(WebDriver driver, String revenue, String records) {
        TextHelper.enterText(driver, ratingCriteriaRevenueField, revenue);
        TextHelper.enterText(driver, ratingCriteriaRecordsField , records );
    }
}
