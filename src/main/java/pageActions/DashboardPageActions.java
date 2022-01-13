package pageActions;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import enums.ConstantVariable;
import helper.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static pageObjects.DashboardPageObjects.*;
import static pageObjects.DashboardPageObjects.firstAvailableStatus;


public class DashboardPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageActions.class);

    public WebElement tmhccLogo(WebDriver driver) {

        return driver.findElement(tmhccLogo);
    }

    public WebElement profileSettingsIcon(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, profileSettings);
        return driver.findElement(profileSettings);
    }

    public void clickProfileSettings(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, newQuoteButton);
        WaitHelper.waitForElementClickable(driver, profileSettings);
        ClickHelper.clickElement(driver, profileSettings);
    }

    public void enterBrokerId(WebDriver driver, String brokerId) throws InterruptedException {

        TextHelper.enterText(driver, brokerIdField, brokerId);
    }

    public void enterAgencyId(WebDriver driver, String agencyId) throws InterruptedException {

        TextHelper.enterText(driver, agencyIdField, agencyId);
    }


    public void enterTextToSearchBox(WebDriver driver, String textInput) throws InterruptedException {

        TextHelper.enterText(driver, searchInputFiled, textInput);
        WaitHelper.pause(3000);

    }

    public void clickClearSearchButton(WebDriver driver) {

        ClickHelper.clickElement(driver, clearSearchInputFiled);
    }

    public void enterAgencyOfficeId(WebDriver driver, String agencyId) throws InterruptedException {

        TextHelper.enterText(driver, agencyOfficeIdField, agencyId);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
        // temp wait
        Thread.sleep(5000);
    }

    public WebElement profileLink(WebDriver driver) {

        return driver.findElement(profileLink);
    }

    public WebElement signOutLink(WebDriver driver) {

        return driver.findElement(signOutLink);
    }

    public WebElement myQuotesTab(WebDriver driver) {
        try {
            return driver.findElement(myQuotesTab);
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: clickMyQuoteTab" + e.getMessage());
            logger.error("failed to verify the my quote tab :: clickMyQuoteTab");
            throw (e);
        }
    }

    public void clickMyPoliciesTab(WebDriver driver) throws InterruptedException {
        try {
            driver.findElement(myPoliciesTab).click();
            WaitHelper.pause(3000);
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

    public List<WebElement> getQuoteCardsList(WebDriver driver) {

        return driver.findElements(quoteCard);
    }

    public List<WebElement> getPolicyCardsList(WebDriver driver) {

        return driver.findElements(policyCard);
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
            return driver.findElements(quotesListLabels);
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: myPoliciesTabTitle" + e.getMessage());
            logger.error("failed to verify the my quote tab :: myPoliciesTabTitle");
            throw (e);
        }
    }

    public List<String> getAllQuotesStatus(WebDriver driver) {

        List<String> status = new ArrayList<>();

        for (WebElement element : driver.findElements(quoteStatus)) {
            status.add(element.getText());
        }
        return status;

    }

    public String getGivenQuoteStatus(WebDriver driver, int index) {

        String quoteStatusXpath = "(//div[@data-qa='quote_card']//p[@data-qa='status'])["+index+"]";

        WebElement element = driver.findElement(By.xpath(quoteStatusXpath));

        return element.getText();

    }

    public List<String> getAllQuotesProductName(WebDriver driver) {

        List<String> names = new ArrayList<>();

        List<WebElement> quoteNameElements = driver.findElements(quoteProductName);

        for (WebElement element : quoteNameElements) {
            names.add(element.getText());
        }
        return names;
    }

    public List<String> getAllQuoteReferenceIds(WebDriver driver) {

        List<String> names = new ArrayList<>();

        List<WebElement> quoteNameElements = driver.findElements(quoteReferenceIdGenericLocator);

        for (WebElement element : quoteNameElements) {
            names.add(element.getText());
        }
        return names;
    }

    public void clickNewQuote(WebDriver driver) throws InterruptedException {

        ClickHelper.clickElement(driver, newQuoteButton);
        WaitHelper.pause(2000);
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

    public void clickCancelButton(WebDriver driver) {

        ClickHelper.clickElement(driver, cancelButton);
    }

    public WebElement productDropdown(WebDriver driver) {

        return driver.findElement(selectProductDropdown);
    }

    public String getApplicantName(WebDriver driver) {

        return TextHelper.getText(driver, applicantNameField, "value");
    }

    public String getWebsite(WebDriver driver) {

        return TextHelper.getText(driver, websiteField, "value");
    }

    public WebElement websiteField(WebDriver driver) {

        return driver.findElement(websiteField);
    }

    public WebElement productRequiredElement(WebDriver driver) {

        return driver.findElement(productRequiredText);
    }

    public WebElement nameRequiredElement(WebDriver driver) {

        return driver.findElement(nameRequiredText);
    }

    public WebElement websiteRequiredElement(WebDriver driver) {

        return driver.findElement(websiteRequiredText);
    }

    public void validateQuoteStatusColorCoding(WebDriver driver) {

        List<WebElement> quoteStatusList = driver.findElements(quoteStatus);

        int count = quoteStatusList.size();

        if (count > 0) {
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
                    case "Cancelled":
                        assert color.equals("red");
                        break;
                    case "Review":
                        assert color.equals("yellow");
                        break;
                    case "Approved":
                        assert color.equals("green");
                        break;
                }
            }
        }

    }

    public void validatePolicyStatusColorCoding(WebDriver driver) {

        List<WebElement> policyStatusList = driver.findElements(policyStatus);

        int count = policyStatusList.size();

        if (count > 0) {
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

    public LoginPageActions logoutApp(WebDriver driver) {
        logger.info("logging out from the application");
        clickProfileSettings(driver);
        signOutLink(driver).click();
        return PageObjectManager.getLoginPageActions();
    }

    public void clickFilterList(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, filterList);
        ClickHelper.clickElement(driver, filterList);
    }

    public void clickFilterByProductName(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, filterByProductName);
        ClickHelper.clickElement(driver, filterByProductName);
    }

    public void clickSubmissionFilterByStatus(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, submissionFilterByStatus);
        ClickHelper.clickElement(driver, submissionFilterByStatus);
    }

    public void clickSubmissionFilterByDateRange(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, submissionFilterByDateRange);
        ClickHelper.clickElement(driver, submissionFilterByDateRange);
    }

    public List<WebElement> getPolicyTableLabels(WebDriver driver) {
        try {
            return driver.findElements(policyListLabels);
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: getPolicyTableLabels" + e.getMessage());
            logger.error("failed to verify the my quote tab :: getPolicyTableLabels");
            throw (e);
        }
    }



    public WebElement noPolicyFound(WebDriver driver) {

        return driver.findElement(noPolicyFoundText);
    }

    public WebElement noQuoteFound(WebDriver driver) {

        return driver.findElement(noQuoteFoundText);
    }

    public String getSearchForNoResult(WebDriver driver) {

        return TextHelper.getText(driver, searchForNoResult, "text");

    }

    public String getFirstAvailableReferenceId(WebDriver driver) {

        return TextHelper.getText(driver, getFirstAvailableReferenceId, "text");

    }

    public String getFirstAvailableLegalName(WebDriver driver) {

        return TextHelper.getText(driver, getFirstAvailableLegalName, "text");
    }



    public boolean verifyQuoteStatusInTable(WebDriver driver) {

        /**
         * this method verifies all the status of the quotes in the table
         * if table contains the quote with status not specified returns false
         */

        List<WebElement> quoteStatusList = driver.findElements(quoteStatus);
        boolean result = true;
        int count = quoteStatusList.size();
        Set<String> actualStatus = new HashSet<String>();
        if (count > 0) {
            for (WebElement statusElement : quoteStatusList) {
                String status = statusElement.getText();
                actualStatus.add(status);
            }
            Set<String> expectedStatus = new HashSet<String>();
            expectedStatus.add("Active");
            expectedStatus.add("In Review");
            expectedStatus.add("Approved");
            expectedStatus.add("Cancelled");
            expectedStatus.add("Declined");
            for (String status : actualStatus) {
                if (expectedStatus.contains(status)) {
                } else {
                    logger.info("table contains quote with status not specified, pls check specified status once");
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public void selectProductInFilter(WebDriver driver, String product) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, allProductsDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, allProductsDropdown, productOptions, product);
    }

    public void clickApplyFiltersButton(WebDriver driver) throws InterruptedException {

        ClickHelper.clickElement(driver, applyFiltersButton);
        WaitHelper.pause(3000);
    }

    public void selectStatusInFilter(WebDriver driver, String status) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, allStatusDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, allStatusDropdown, statusOptions, status);
    }

    public void selectPolicyStatusInFilter(WebDriver driver, String status) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, policyAllStatusDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, policyAllStatusDropdown, statusOptions, status);
    }

    public void clickPolicyFilterByStatus(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, policyFilterByStatus);
        ClickHelper.clickElement(driver, policyFilterByStatus);
    }

    public void enterCreateStartDate(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, createdStartDateField);
        TextHelper.enterText(driver, createdStartDateField, "12/01/2021");
    }

    public void enterCreateEndDate(WebDriver driver){
        TextHelper.enterText(driver, createdEndDateField, "12/30/2021");
    }

    public List<String> getQuoteCreatedDates(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quoteCreatedDateGeneric);
        List<WebElement> createdDates = driver.findElements(quoteCreatedDateGeneric);
        if (createdDates.size()>0){
            List<String> dates = new ArrayList<>();
            for (WebElement ele : createdDates) {
                System.out.println("\n actual Date --"+ele.getText());
                dates.add(ele.getText());
            }
            return dates;
        }
        return null;
    }

    public List<String> getPolicyExpirationDates(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, policyExpirationDateGeneric);
        List<WebElement> createdDates = driver.findElements(policyExpirationDateGeneric);
        if (createdDates.size()>0){
            List<String> dates = new ArrayList<>();
            for (WebElement ele : createdDates) {
                System.out.println("\n actual Date --"+ele.getText());
                dates.add(ele.getText());
            }
            return dates;
        }
        return null;
    }

    public void clickRenewButton (WebDriver driver) throws ParseException {

        List<String> dates = getPolicyExpirationDates(DriverManager.getDriver());
        Date actualDate, givenDate;
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
        DateFormat df = new SimpleDateFormat(ConstantVariable.DATE_FORMAT);
        String actualStatus = TextHelper.getText(driver, firstAvailableStatus,"text");
        givenDate = df.parse(timeStamp);
        String expStatus = "Renewal Started";
        for (String date: dates) {
            try {
                actualDate = df.parse(date);
                if (!actualStatus.equalsIgnoreCase(expStatus) && getDifferenceInExpirationDateInDays(actualDate,givenDate) <=60) {
                    ClickHelper.clickElement(driver,firstAvailableRenewButton);
                    ClickHelper.clickElement(driver, submitSubmissionRenewal);

                }else {
                    assert expStatus.equals(actualStatus);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }
    public long getDifferenceInExpirationDateInDays (Date expirationDate, Date currentDate) throws ParseException {
        long difference = (expirationDate.getTime()-currentDate.getTime())/8640000;
        return Math.abs(difference);
    }






}
