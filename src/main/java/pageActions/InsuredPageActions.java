package pageActions;

import base.BaseTest;
import helper.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Text;

import static pageObjects.InsuredPageObjects.*;

public class InsuredPageActions extends BaseTest {

    public WebElement newInsuredButton(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, newInsuredButton);
        return driver.findElement(newInsuredButton);
    }

    public WebElement searchAgainButton(WebDriver driver) {

        return driver.findElement(searchAgainButton);
    }

    public WebElement cancelInsuredSearch(WebDriver driver) {

        return driver.findElement(cancelInsuredSearchButton);
    }

    public WebElement continueInsuredSearch(WebDriver driver) {

        return driver.findElement(continueInsuredSearchButton);
    }

    public void clickContinueInsuredSearch(WebDriver driver) {

        ClickHelper.clickElement(driver, continueInsuredSearchButton);
    }

    public void clickSelectInsuredButton(WebDriver driver) {

        ClickHelper.clickElement(driver, selectInsuredButton);
    }

    public void clickNewInsuredButton(WebDriver driver) {

        ClickHelper.clickElement(driver, newInsuredButton);
    }

    public WebElement modifySearchButton(WebDriver driver) {

        return driver.findElement(modifySearchButton);
    }

    public boolean verifyInsuredSearchResult(WebDriver driver, String applicantName, String website) {

        String websiteXpath = "//div/a[text()='" + website + "']";
        String applicantXpath = "//div[text()='" + applicantName + "']";
        WebElement websiteLink = driver.findElement(By.xpath(websiteXpath));
        return websiteLink.isDisplayed();
    }

    public String getInsuredName(WebDriver driver) {

        return TextHelper.getText(driver, insuredNameField, "value");
    }

    public String getInsuredWebsite(WebDriver driver) {

        return TextHelper.getText(driver, insuredWebsiteField, "value");
    }

    public WebElement emailReqText(WebDriver driver) {

        return driver.findElement(emailRequiredText);
    }

    public boolean validatePhysicalAddressFields(WebDriver driver) {

        if (driver.findElement(physicalAddressReqText).isDisplayed()) {
            if (driver.findElement(physicalCityReqText).isDisplayed()) {
                if (driver.findElement(physicalStateReText).isDisplayed()) {
                    if (driver.findElement(physicalZipcodeReqText).isDisplayed()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateMailingAddressFields(WebDriver driver) {

        if (driver.findElement(mailingAddressReqText).isDisplayed()) {
            if (driver.findElement(mailingCityReqText).isDisplayed()) {
                if (driver.findElement(mailingZipcodeReqText).isDisplayed()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void enterEmailAddress(WebDriver driver){
        String email = FakeDataHelper.email();
        TextHelper.enterText(driver, insuredFormEmailField, email);
    }

    public void enterPhysicalAddress(WebDriver driver){

        String address = FakeDataHelper.address();
        TextHelper.enterText(driver, insuredPhysicalAddField, address);
    }

    public void enterPhyCity(WebDriver driver){
        String city = FakeDataHelper.city();
        TextHelper.enterText(driver, insuredPhysicalCityField, city);
    }

    public void enterPhyZipcode(WebDriver driver){
        String zipcode = FakeDataHelper.zipcode();
        TextHelper.enterText(driver, insuredPhysicalZipCode, zipcode);
    }

    public void selectPhyState(WebDriver driver) throws InterruptedException {

        DropdownHelper.selectValueFromBootstrapDropdown(driver, physicalStateDropdown, physicalStateOptions, "CA");
    }

    public void clickSameAsPhyAddress(WebDriver driver){

        ClickHelper.clickElement(driver, sameAsPhyAddressCheckbox);
    }

    public void cancelInsuredFormButton(WebDriver driver){

        ClickHelper.clickElement(driver, cancelInsuredFormButton);
    }

    public void clickContinueInsuredFormButton(WebDriver driver){

        ClickHelper.clickElement(driver, continueInsuredFormButton);
    }

    public boolean validateSearchAgainButtonWithInsuredName(WebDriver driver, String name){

        if (driver.findElement(searchAgainButton).isEnabled()){
            driver.findElement(clearInsuredNameButton).click();
            boolean val = driver.findElement(searchAgainButton).isEnabled();
            TextHelper.enterText(driver, modifySearchNameField, name);
            return !val;
        }else{
            TextHelper.enterText(driver, modifySearchNameField, name);
            return driver.findElement(searchAgainButton).isEnabled();
        }
    }

    public boolean validateSearchAgainButtonWithInsuredWebsite(WebDriver driver, String website){

        if (driver.findElement(searchAgainButton).isEnabled()){
            driver.findElement(clearInsuredWebsiteButton).click();
            boolean val = driver.findElement(searchAgainButton).isEnabled();
            TextHelper.enterText(driver, modifySearchWebsiteField, website);
            return !val;
        }else{
        TextHelper.enterText(driver, modifySearchWebsiteField, website);
            return driver.findElement(searchAgainButton).isEnabled();
        }
    }

    public void clickSearchAgainButton(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, searchAgainButton);
        WaitHelper.pause(3000);
    }


}
