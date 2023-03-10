package pageActions;

import base.BaseTest;
import base.PageObjectManager;
import helper.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pageObjects.InsuredPageObjects.*;

public class InsuredPageActions extends BaseTest {

    public WebElement newInsuredButton(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, newInsuredButton);
        return driver.findElement(newInsuredButton);
    }

    public WebElement searchAgainButton(WebDriver driver) {

        return driver.findElement(searchAgainButton);
    }

    public void clickCancelInsuredSearch(WebDriver driver) {

        driver.findElement(cancelInsuredSearchButton).click();
    }

    public WebElement continueInsuredSearch(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, continueInsuredSearchButton);
        return driver.findElement(continueInsuredSearchButton);
    }

    public void clickContinueInsuredSearch(WebDriver driver) {

        ClickHelper.clickElement(driver, continueInsuredSearchButton);
    }

    public void clickContinueInsuredButton(WebDriver driver) throws InterruptedException {

        ClickHelper.clickElement(driver, continueInsuredButton);
        WaitHelper.pause(7000);
    }

    public void clickNewInsuredButton(WebDriver driver) {

        ClickHelper.clickElement(driver, newInsuredButton);

    }

    public WebElement modifySearchButton(WebDriver driver) {

        return driver.findElement(modifySearchButton);
    }

    public boolean verifyInsuredSearchResult(WebDriver driver, String website) {
        String websiteXpath = "//div/a[text()='" + website + "']";
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

    public String enterEmailAddress(WebDriver driver) {
        String email = FakeDataHelper.email();
        TextHelper.enterText(driver, insuredFormEmailField, email);
        return email;
    }

    public String enterPhysicalAddress(WebDriver driver) {

        String address = FakeDataHelper.address();
        TextHelper.enterText(driver, insuredPhysicalAddField, address);
        return address;
    }

    public String enterPhyCity(WebDriver driver) {
        String city = FakeDataHelper.city();
        TextHelper.enterText(driver, insuredPhysicalCityField, city);
        return city;
    }

    public String enterPhyZipcode(WebDriver driver) {
        String zipcode = FakeDataHelper.zipcode();
        TextHelper.enterText(driver, insuredPhysicalZipCode, zipcode);
        return zipcode;
    }

    public void selectPhyState(WebDriver driver) throws InterruptedException {
        WebElement dropdown = driver.findElement(physicalStateDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, physicalStateOptions, "CA");
    }

    public void clickSameAsPhyAddress(WebDriver driver) {

        ClickHelper.clickElement(driver, sameAsPhyAddressCheckbox);
    }

    public void cancelInsuredFormButton(WebDriver driver) {

        ClickHelper.clickElement(driver, cancelInsuredFormButton);
    }

    public RatingCriteriaPageActions clickContinueInsuredFormButton(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, continueInsuredFormButton);
        WaitHelper.waitForProgressbarInvisibility(driver);
        return PageObjectManager.getRatingCriteriaPageActions();
    }

    public boolean validateSearchAgainButtonWithInsuredName(WebDriver driver, String name) {

        if (driver.findElement(searchAgainButton).isEnabled()) {
            driver.findElement(clearInsuredNameButton).click();
            boolean val = driver.findElement(searchAgainButton).isEnabled();
            TextHelper.enterText(driver, modifySearchNameField, name);
            return !val;
        } else {
            TextHelper.enterText(driver, modifySearchNameField, name);
            return driver.findElement(searchAgainButton).isEnabled();
        }
    }

    public void enterApplicantWebsite(WebDriver driver, String website) {
            driver.findElement(clearInsuredWebsiteButton).click();
            TextHelper.enterText(driver, modifySearchWebsiteField, website);

    }

    public void clickSearchAgainButton(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, searchAgainButton);
        WaitHelper.pause(5000);
    }

    public boolean duplicateSubmissionDialog(WebDriver driver) {
        return ClickHelper.isElementExist(driver, duplicateSubmissionDialog);

    }

    public String duplicateSubmissionDialogDescription(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, duplicateSubDialogDescription);
        return TextHelper.getText(driver, duplicateSubDialogDescription, "text");
    }

    public void clickDuplicateCancelButton(WebDriver driver) {

        ClickHelper.clickElement(driver, duplicateDialogCancelButton);
    }

    public void selectInsuredCard(WebDriver driver, String insuredName) throws InterruptedException {
        List<WebElement> insuredNames = driver.findElements(insuredNameInCard);
        int cardsCount = 0;
        if (insuredNames.size() > 0) {
            for (WebElement insured : insuredNames) {
                cardsCount++;
                if (insured.getText().contentEquals(insuredName)) {
                    break;
                }
            }
            String selectButtonXpath = "(//button[@data-qa='insured_select'])[" + cardsCount + "]";
            driver.findElement(By.xpath(selectButtonXpath)).click();
        }
        WaitHelper.pause(7000);
    }

    public List<WebElement> getAllInsuredNames(WebDriver driver) {
        return driver.findElements(insuredNameInCard);
    }

    public void selectInsuredCardWithIndex(WebDriver driver, int index) throws InterruptedException {
        String selectButtonXpath = "(//button[@data-qa='insured_select'])[" + index + "]";
        driver.findElement(By.xpath(selectButtonXpath)).click();
        WaitHelper.pause(10000);
    }

    public boolean isClearanceDialogModalDisplayed(WebDriver driver) {

        return ClickHelper.isElementExist(driver, clearanceDialogModal);
    }

   public boolean isQuotePageDisabled (WebDriver driver) {

        return WaitHelper.isElementEnabled(driver, disabledQuoteTab);
    }

    public boolean isClearanceSubmitButtonEnabled(WebDriver driver) {

        return driver.findElement(clearanceSubmitButton).isEnabled();
    }

    public void clickClearanceSubmitButton(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, clearanceSubmitButton, 30);
        ClickHelper.clickElement(driver, clearanceSubmitButton);
        WaitHelper.waitForProgressbarInvisibility(driver);
    }

    public void clickClearanceCancelQuoteButton(WebDriver driver) throws InterruptedException {

        ClickHelper.clickElement(driver, clearanceCancelQuoteButton);
        WaitHelper.pause(3000);
    }

    public void enterClearanceText(WebDriver driver, String text) {

        TextHelper.enterText(driver, clearanceDialogTextArea, text);
    }

    public List<WebElement> getAllInsuranceCards(WebDriver driver){

        List<WebElement> insureCards = driver.findElements(insuranceCardGenericLocator);
        if (insureCards.size() > 0) {
            return insureCards;
            }
        else{
            return  null;
        }
    }

    public  void enterInsuredPhoneNumber(WebDriver driver) throws InterruptedException {
        String phoneNum = FakeDataHelper.phoneNumber();
        TextHelper.enterText(driver, insuredPhoneNumberField, phoneNum);
        WaitHelper.pause(4000);

    }

    public boolean isCreateNewInsuredTextDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, createNewInsuredInfoText, 30);
        return ClickHelper.isElementExist(driver, createNewInsuredInfoText);
    }

    public boolean verifyValidPhoneNumberFormat(WebDriver driver){
        String phone = TextHelper.getText(driver, insuredPhoneNumberField, "value");
        Pattern pattern = Pattern.compile("^\\+[0-9]{1} \\([0-9]{3}\\) [0-9]{3}-[0-9]{4}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public boolean verifyValidZipCode(WebDriver driver){
        String zipcode = TextHelper.getText(driver, insuredPhysicalZipCode, "value");
        Pattern pattern = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");
        assert zipcode != null;
        Matcher matcher = pattern.matcher(zipcode);
        return matcher.matches();
    }

    public boolean isFileSizeLagerThan2MbTextDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, largerThan2MbFileSizeText);
    }
}
