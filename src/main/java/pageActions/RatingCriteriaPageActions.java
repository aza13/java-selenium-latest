package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.DropdownHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageObjects.DashboardPageObjects.allStatusDropdown;
import static pageObjects.DashboardPageObjects.statusOptions;
import static pageObjects.RatingCriteriaPageObjects.*;


public class RatingCriteriaPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(RatingCriteriaPageActions.class);

    public boolean isRatingCriteriaPageDisplayed(WebDriver driver){
       return ClickHelper.isElementExist(driver, ratingCriteriaHeader);

    }

    public WebElement ratingCriteriaExitButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, ratingCriteriaExitButton);
        return driver.findElement(ratingCriteriaExitButton);
    }

    public void clickRatingCriteriaButton(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, ratingCriteriaOkButton);
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

    public void enterTextToBusinessClassDropDown(WebDriver driver, String bitcoin) throws InterruptedException {
        ClickHelper.clickElement(driver, businessClassDropDown);
        ClickHelper.clickElement(driver, clearBusinessClassButton);
        TextHelper.enterText(driver, businessClassDropDown, bitcoin);
        WaitHelper.pause(1000);
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

    public void clickRatingCriteriaOkButton (WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, ratingCriteriaOkButton);
        ClickHelper.clickElement(driver, ratingCriteriaOkButton);
        WaitHelper.pause(4000);
    }

    public void clickRatingCriteriaDropDownClearButton (WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, ratingCriteriaDropDownClearButton);
        ClickHelper.clickElement(driver, ratingCriteriaDropDownClearButton);
        WaitHelper.pause(1000);
    }

    public WebElement hardDeclineText (WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, hardDeclineText);
        return driver.findElement(hardDeclineText);
    }
    public void enterValueRatingCriteriaTextBox(WebDriver driver, String revenue, String records) throws InterruptedException {

        TextHelper.enterText(driver, ratingCriteriaRevenueField, revenue);
        TextHelper.enterText(driver, ratingCriteriaRecordsField , records );
        WaitHelper.pause(1000);
    }

    public void clickBusinessClassOption(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, businessClassOption);
        ClickHelper.clickElement(driver, businessClassOption);
    }
}
