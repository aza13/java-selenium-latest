package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.DropdownHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
}
