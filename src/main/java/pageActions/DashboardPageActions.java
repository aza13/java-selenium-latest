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
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static pageObjects.DashboardPageObjects.*;

public class DashboardPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageActions.class);

    public WebElement tmhccLogo(WebDriver driver){

        return driver.findElement(tmhccLogo);
    }

    public WebElement profileSettingsIcon(WebDriver driver){

        return driver.findElement(profileSettings);
    }

    public void clickProfileSettings(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, newQuoteButton);
        WaitHelper.waitForElementClickable(driver, profileSettings);
        ClickHelper.clickElement(driver, profileSettings);
    }

    public void enterBrokerId(WebDriver driver, String brokerId) throws InterruptedException {

        TextHelper.enterText(driver, brokerIdField, brokerId);
        /*Actions action = new Actions(driver);
        action.sendKeys(Keys.TAB).build().perform();*/
        // tem wait
        Thread.sleep(6000);
    }

    public void enterAgencyId(WebDriver driver, String agencyId) throws InterruptedException {

        TextHelper.enterText(driver, agencyIdField, agencyId);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
        // tem wait
        Thread.sleep(6000);
    }

    public WebElement profileLink(WebDriver driver){

        return driver.findElement(profileLink);
    }

    public WebElement signOutLink(WebDriver driver){

        return driver.findElement(signOutLink);
    }

    public WebElement clickMyQuoteTab(WebDriver driver) {
        try {
            return driver.findElement(myQuotesTab);
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: clickMyQuoteTab" + e.getMessage());
            logger.error("failed to verify the my quote tab :: clickMyQuoteTab");
            throw (e);
        }
    }

    public void clickMyPoliciesTab(WebDriver driver) {
        try {
            driver.findElement(myPoliciesTab).click();
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: clickMyPoliciesTab" + e.getMessage());
            logger.error("failed to verify the my quote tab :: clickMyPoliciesTab");
            throw (e);
        }
    }

    public String getMyQuotesTabTitle(WebDriver driver) {
        try {
            return TextHelper.getText(driver, myQuotesTab, "text");
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: getMyQuotesTabTitle" + e.getMessage());
            logger.error("failed to verify the my quote tab :: getMyQuotesTabTitle");
            throw (e);
        }
    }

    public String getMyPoliciesTabTitle(WebDriver driver) {
        try {
            return TextHelper.getText(driver, myPoliciesTab, "text");
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: getMyPoliciesTabTitle" + e.getMessage());
            logger.error("failed to verify the my quote tab :: getMyPoliciesTabTitle");
            throw (e);
        }
    }

    public List<WebElement> getSubmissionsList(WebDriver driver) {
        try {
            return driver.findElements(quotesList);
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: getSubmissionsList" + e.getMessage());
            logger.error("failed to verify the my quote tab :: getSubmissionsList");
            throw (e);
        }
    }

    public List<WebElement> getQuoteTableLabels(WebDriver driver) {
        try {
            List<WebElement> labels = driver.findElements(quotesListLabels);
            return labels;
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: myPoliciesTabTitle" + e.getMessage());
            logger.error("failed to verify the my quote tab :: myPoliciesTabTitle");
            throw (e);
        }
    }

    public void clickNewQuote(WebDriver driver) {

        ClickHelper.clickElement(driver, newQuoteButton);
    }

    public void CreateNewQuote(WebDriver driver, String product, String applicantName, String website) throws InterruptedException {

        DropdownHelper.selectValueFromBootstrapDropdown(driver, selectProductDropdown, genericProductOption, product);
        TextHelper.enterText(driver, applicantNameField, applicantName);
        TextHelper.enterText(driver, websiteField, website);
    }

    public InsuredPageActions clickContinueButton(WebDriver driver) throws InterruptedException {

        ClickHelper.clickElement(driver, continueButton);
        Thread.sleep(5000);
//        WaitHelper.waitForElementVisibility(driver, InsuredPageObjects.newInsuredButton);
        return PageObjectManager.getInsuredPageActions();
    }

    public void clickCancelButton(WebDriver driver){

        ClickHelper.clickElement(driver, cancelButton);
    }

    public WebElement productDropdown(WebDriver driver){

        return driver.findElement(selectProductDropdown);
    }

    public String getApplicantName(WebDriver driver){

        return TextHelper.getText(driver, applicantNameField, "value");
    }

    public String getWebsite(WebDriver driver){

        return TextHelper.getText(driver, websiteField, "value");
    }

    public WebElement websiteField(WebDriver driver){

        return driver.findElement(websiteField);
    }

    public WebElement productRequiredElement(WebDriver driver){

        return driver.findElement(productRequiredText);
    }

    public WebElement  nameRequiredElement(WebDriver driver){

        return driver.findElement(nameRequiredText);
    }

    public WebElement  websiteRequiredElement(WebDriver driver){

        return driver.findElement(websiteRequiredText);
    }

    public void validateQuoteStatusColorCoding(WebDriver driver){

        List<WebElement> quoteStatusList = driver.findElements(quoteStatus);

        int count = quoteStatusList.size();

        if (count > 0){
            for (WebElement statusElement : quoteStatusList) {
                String status = statusElement.getText();
                String color = statusElement.getAttribute("style").split(":")[1].replace(";", "").trim();
                switch (status) {
                    case "Active":
                        assert color.equals("blue");
                        break;
                    case "Renewed":
                        assert color.equals("black");
                        break;
                    case "Expired":
                        assert color.equals("grey");
                        break;
                    case "Declined":
                        assert color.equals("red");
                        break;
                }
            }
        }

     }

    public void validatePolicyStatusColorCoding(WebDriver driver){

        List<WebElement> policyStatusList = driver.findElements(policyStatus);

        int count = policyStatusList.size();

        if (count > 0){
            for (WebElement statusElement : policyStatusList) {
                String status = statusElement.getText();
                String color = statusElement.getCssValue("color");
                switch (status) {
                    case "Active":
                        assert color.equals("blue");
                        break;
                    case "Renewed":
                        assert color.equals("black");
                        break;
                    case "Expired":
                        assert color.equals("grey");
                        break;
                    case "Declined":
                        assert color.equals("red");
                        break;
                }
            }
        }

    }

    public LoginPageActions logoutApp(WebDriver driver){
        logger.info("logging out from the application");
        clickProfileSettings(driver);
        signOutLink(driver).click();
        return PageObjectManager.getLoginPageActions();
    }

}
