package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.DropdownHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

import static pageObjects.DashboardPageObjects.*;
import static pageObjects.DashboardPageObjects.websiteField;
import static pageObjects.InsuredPageObjects.newInsuredButton;
import static pageObjects.RatingCriteriaPageObject.*;


public class RatingCriteriaPageActions extends BaseTest {

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
    public void inputValueToRatingCriteria(WebDriver driver, String numberOfResidentialUnits, String totalCommercialSquareFeet) throws InterruptedException {

        TextHelper.enterText(driver, ratingCriteriaInputBox1, numberOfResidentialUnits);
        TextHelper.enterText(driver, ratingCriteriaInputBox2 , totalCommercialSquareFeet );
        WaitHelper.pause(1000);
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
}
