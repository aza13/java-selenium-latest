package pageActions;

import base.BaseTest;
import base.PageObjectManager;
import helper.ClickHelper;
import helper.DropdownHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageObjects.DashboardPageObjects.allStatusDropdown;
import static pageObjects.DashboardPageObjects.statusOptions;
import static pageObjects.RatingCriteriaPageObjects.*;

public class RatingCriteriaPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(RatingCriteriaPageActions.class);

    public boolean isRatingCriteriaPageDisplayed(WebDriver driver) throws InterruptedException {
       WaitHelper.pause(5000);
       return ClickHelper.isElementExist(driver, detailsPageSelected);

    }

    public void ratingCriteriaPageClick(WebDriver driver) throws InterruptedException {
         ClickHelper.clickElement(driver, ratingCriteriaButton);
         WaitHelper.pause(2000);
    }

    public WebElement ratingCriteriaExitButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, ratingCriteriaExitButton);
        return driver.findElement(ratingCriteriaExitButton);
    }

    public void clickRatingCriteriaButton(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, ratingCriteriaOkButton);
        ClickHelper.clickElement(driver, ratingCriteriaButton);
    }

    public UnderwritingQuestionsPageActions clickRatingCriteriaContinueButton (WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, ratingCriteriaContinueButton);
        ClickHelper.javaScriptExecutorClick(driver, ratingCriteriaContinueButton);
        WaitHelper.pause(20000);
        return PageObjectManager.getUnderwritingQuestionsPageActions();
    }

    public void clickBusinessClassDropdown(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, businessClassDropDown);
        ClickHelper.clickElement(driver,businessClassDropDown);

    }

    public void enterTextToBusinessClassDropDown(WebDriver driver, String bitcoin) throws InterruptedException {
        ClickHelper.clickElement(driver, businessClassDropDown);
        if(ClickHelper.isElementExist(driver, clearBusinessClassButton)){
            ClickHelper.clickElement(driver, clearBusinessClassButton);
        }
        TextHelper.enterText(driver, businessClassDropDown, bitcoin);
        WaitHelper.pause(2000);
    }


    public WebElement ratingCriteriaTitle(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, ratingCriteriaTitle);
        return driver.findElement(ratingCriteriaTitle);
    }

    public void selectBusinessClassInFilter(WebDriver driver, String status) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, allStatusDropdown);
        WebElement dropdown = driver.findElement(allStatusDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, statusOptions, status);
    }

    public void enterRatingCriteriaRevenueAndRecords(WebDriver driver, String revenue, String records) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, ratingCriteriaRevenueField, 30);
        TextHelper.enterText(driver, ratingCriteriaRevenueField, revenue);
        TextHelper.enterText(driver, ratingCriteriaRecordsField , records );
        driver.findElement(ratingCriteriaRecordsField).sendKeys(Keys.TAB);
        WaitHelper.pause(2000);
    }

    public void enterNetWorth(WebDriver driver, String revenue) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, ratingCriteriaNetWorthField, 30);
        TextHelper.enterText(driver, ratingCriteriaNetWorthField, revenue);
        driver.findElement(ratingCriteriaNetWorthField).sendKeys(Keys.TAB);
        WaitHelper.pause(2000);
    }

    public void enterNoOfPhysicians(WebDriver driver, String count){
        TextHelper.enterText(driver, noOfPhysicians, count);
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

    public void verifyEffectiveDateField (WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, ratingCriteriaEffectiveDateSelectionButton);
        ClickHelper.clickElement(driver, clickNextMonth );
        boolean result = WaitHelper.isElementEnabled(driver, clickNextMonth);
        assert !result;
        WaitHelper.pause(2000);
        ClickHelper.clickElement(driver,ratingCriteriaEffectiveActualDateChoose);
    }

    public void verifyExpirationDate (WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, ratingCriteriaExpirationDateField);
        ClickHelper.clickElement(driver, ratingCriteriaExpirationDateField);
        WaitHelper.pause(1000);
        WaitHelper.waitForElementClickable(driver, dropDownOnExpirationDatePicker );
        ClickHelper.clickElement(driver, dropDownOnExpirationDatePicker);
        WaitHelper.waitForElementClickable(driver, nextYearInExpirationDatePicker);
        ClickHelper.clickElement(driver, nextYearInExpirationDatePicker);
        WaitHelper.isElementEnabled(driver, clickNextMonth);


    }

    public void clickRatingCriteriaExitButton (WebDriver driver) throws InterruptedException {

        WaitHelper.waitForElementClickable(driver, ratingCriteriaCancelButton);
        ClickHelper.clickElement(driver,ratingCriteriaCancelButton);
        WaitHelper.pause(3000);
    }

    public void clickDetailsPageTab(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(5000);
        ClickHelper.clickElement(driver, ratingCriteriaHeader);
        WaitHelper.pause(5000);
    }

    public boolean checkEditButtonIsVisible(WebDriver driver){
        return ClickHelper.isElementExist(driver, clickEditOnDetails);
    }

    public void clickEditButtonIsVisible(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, clickEditOnDetails);
        ClickHelper.clickElement(driver, clickEditOnDetails);
        WaitHelper.pause(3000);
    }

    public void checkEditConfirmMsgIsVisible(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, confirmMsgDetails);
        ClickHelper.clickElement(driver, confirmMsgOKDetails);
        WaitHelper.pause(3000);
    }

    public void checkEditConfirmMsgCancelIsVisible(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        ClickHelper.clickElement(driver, confirmMsgCancelDetails);
        WaitHelper.pause(3000);
    }

    public boolean checkEffectiveDateIsVisible(WebDriver driver){
        return ClickHelper.isElementExist(driver, ratingCriteriaHeader2);
    }

    public void enterRatingCriteriaNoPhysiciansRevenueAndRecords(WebDriver driver, String noPhysicians, String revenue, String records) throws InterruptedException {
        WaitHelper.pause(5000);
        TextHelper.enterText(driver, ratingCriteriaNoPhysiciansField, noPhysicians);
        TextHelper.enterText(driver, ratingCriteriaRevenueField, revenue);
        TextHelper.enterText(driver, ratingCriteriaRecordsField , records );
        driver.findElement(ratingCriteriaRecordsField).sendKeys(Keys.TAB);
        WaitHelper.pause(3000);
    }

    public void verifyAndClickHardDeclinePopup(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, hardDeclineMsg);
        ClickHelper.clickElement(driver, hardDeclineOKButton);
        WaitHelper.pause(3000);
    }

}
