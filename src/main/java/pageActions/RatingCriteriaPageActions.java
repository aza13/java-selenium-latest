package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.DropdownHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static pageObjects.RatingCriteriaPageObject.*;
import org.apache.log4j.Logger;
import static pageObjects.DashboardPageObjects.allStatusDropdown;
import static pageObjects.DashboardPageObjects.statusOptions;
import static pageObjects.RatingCriteriaPageObject.businessClassDropDown;
import static pageObjects.RatingCriteriaPageObject.ratingCriteriaButton;
import static pageObjects.RatingCriteriaPageObject.ratingCriteriaContinueButton;
import static pageObjects.RatingCriteriaPageObject.ratingCriteriaRecordsField;
import static pageObjects.RatingCriteriaPageObject.ratingCriteriaRevenueField;
import static pageObjects.RatingCriteriaPageObject.ratingCriteriaTitle;




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

    public void clickRatingCriteriaEffectiveDateCalenderButton (WebDriver driver) throws InterruptedException {

        WaitHelper.waitForElementClickable(driver, ratingCriteriaEffectiveDateSelectionButton);
        ClickHelper.clickElement(driver, ratingCriteriaEffectiveDateSelectionButton);
        WaitHelper.pause(2000);
        ClickHelper.clickElement(driver,ratingCriteriaEffectiveActualDateChoose);
    }

    public boolean viewRatingCriteriaExpirationDateField (WebDriver driver) {

        WaitHelper.waitForElementClickable(driver, ratingCriteriaExpirationExpectedDateShow);
        return driver.findElement(ratingCriteriaExpirationExpectedDateShow).isDisplayed();
    }

    public void clickRatingCriteriaExitButton (WebDriver driver) throws InterruptedException {

        WaitHelper.waitForElementClickable(driver, ratingCriteriaCancelButton);
        ClickHelper.clickElement(driver,ratingCriteriaCancelButton);
        WaitHelper.pause(3000);
    }
}
