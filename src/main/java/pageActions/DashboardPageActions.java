package pageActions;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static pageObjects.DashboardPageObjects.*;
import static pageObjects.DashboardPageObjects.firstAvailableStatus;
import static pageObjects.DashboardPageObjects.quoteBusinessType;
import static pageObjects.LoginPageObjects.logInButton;


public class DashboardPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageActions.class);

    public WebElement tmhccLogo(WebDriver driver) {

        return driver.findElement(tmhccLogo);
    }

    public WebElement profileSettingsIcon(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, profileSettings);
        return driver.findElement(profileSettings);
    }

    public String getQuoteStatus (WebDriver driver)  {
        return TextHelper.getText(driver, quoteStatus, "text");
    }

    public void clickProfileSettings(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, newQuoteButton, 30);
        WaitHelper.waitForElementClickable(driver, profileSettings);
        ClickHelper.clickElement(driver, profileSettings);
    }

    public boolean isBrokerIdDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, brokerIdField);
    }

    public void enterTextToSearchBox(WebDriver driver, String textInput) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, searchInputFiled);
        TextHelper.enterText(driver, searchInputFiled, textInput);
        WaitHelper.pause(10000);
    }

    public void clickClearSearchButton(WebDriver driver) {

        ClickHelper.clickElement(driver, clearSearchInputFiled);
    }

    public WebElement signOutLink(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, signOutLink);
        return driver.findElement(signOutLink);
    }

    public void clickSupportLink(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, supportLink, 30);
        driver.findElement(supportLink).click();
    }

    public WebElement clickQuotesTab(WebDriver driver) {
        try {
            WaitHelper.waitForElementVisibility(driver, myQuotesTab);
            return driver.findElement(myQuotesTab);
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: clickQuotesTab" + e.getMessage());
            logger.error("failed to verify the my quote tab :: clickQuotesTab");
            throw (e);
        }
    }

    public void clickMyPoliciesTab(WebDriver driver) {
        try {
            WaitHelper.waitForElementVisibilityCustom(driver, myPoliciesTab, 30);
            driver.findElement(myPoliciesTab).click();
            WaitHelper.pause(5000);
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: clickMyPoliciesTab" + e.getMessage());
            logger.error("failed to verify the my quote tab :: clickMyPoliciesTab");
        }
    }

    public boolean verifyPoliciesExists(WebDriver driver){
        try {
            return driver.findElement(policyHeader).isDisplayed();
        }catch (Exception e){
            testLogger.fail("failed to verify the policies :: verifyPoliciesExists" + e.getMessage());
            logger.error("failed to verify the policies :: verifyPoliciesExists");
            throw (e);
        }
    }

    public String getMyQuotesTabTitle(WebDriver driver) {
        try {
            WaitHelper.waitForElementVisibility(driver, myQuotesTab);
            return TextHelper.getText(driver, myQuotesTab, "text");
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: getMyQuotesTabTitle" + e.getMessage());
            logger.error("failed to verify the my quote tab :: getMyQuotesTabTitle");
            throw (e);
        }
    }

    public List<WebElement> getQuoteCardsList(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, quoteCard, 30);
        return driver.findElements(quoteCard);
    }

    public List<WebElement> getPolicyCardsList(WebDriver driver) {

        return driver.findElements(policyCard);
    }

    public String getMyPoliciesTabTitle(WebDriver driver) {
        try {
            WaitHelper.waitForElementVisibility(driver, myPoliciesTab);
            return TextHelper.getText(driver, myPoliciesTab, "text");
        } catch (Exception e) {
            testLogger.fail("failed to verify the my quote tab :: getMyPoliciesTabTitle" + e.getMessage());
            logger.error("failed to verify the my quote tab :: getMyPoliciesTabTitle");
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

    public String getGivenQuoteStatus(WebDriver driver, int index) throws InterruptedException {

        String quoteStatusXpath = "(//div[@data-qa='quote_card']//p[@data-qa='status'])["+index+"]";
        WaitHelper.pause(3000);
        WebElement element = driver.findElement(By.xpath(quoteStatusXpath));
        return element.getText();
    }

    public List<String> getAllQuotesCoverageName(WebDriver driver) {

        List<String> names = new ArrayList<>();

        List<WebElement> quoteNameElements = driver.findElements(quoteCoverageName);

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
        WaitHelper.waitForElementVisibilityCustom(driver, newQuoteButton, 45);
        ClickHelper.clickElement(driver, newQuoteButton);
        WaitHelper.pause(2000);
    }

    public void createNewQuote(WebDriver driver, String product, String applicantName, String website) throws InterruptedException {
        WebElement element = driver.findElement(selectCoverageDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, element, genericCoverageOption, product);
        WaitHelper.pause(2000);
        WaitHelper.waitForElementVisibilityCustom(driver, applicantNameField, 30);
        TextHelper.enterText(driver, applicantNameField, applicantName);
        if(website.contentEquals("No website")){
            website = "";
        }
        TextHelper.enterText(driver, websiteField,website);
    }

    public InsuredPageActions clickContinueButton(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, continueButton);
        Thread.sleep(10000);
        return PageObjectManager.getInsuredPageActions();
    }

    public void clickCancelButton(WebDriver driver) {

        ClickHelper.clickElement(driver, cancelButton);
    }

    public WebElement getCoverageDropdown(WebDriver driver) {

        return driver.findElement(selectCoverageDropdown);
    }

    public String getApplicantName(WebDriver driver) {

        return TextHelper.getText(driver, applicantNameField, "value");
    }

    public String getWebsite(WebDriver driver) {

        return TextHelper.getText(driver, websiteField, "value");
    }

    public WebElement getCoverageRequiredElement(WebDriver driver) {

        return driver.findElement(coverageRequiredText);
    }

    public WebElement nameRequiredElement(WebDriver driver) {

        return driver.findElement(nameRequiredText);
    }

    public String getPolicyStatus(WebDriver driver) throws InterruptedException {
        logger.info("this method returns first policy status");
        try{
            WaitHelper.waitForElementVisibilityCustom(driver, policyStatus,30);
            return TextHelper.getText(driver, policyStatus, "text").trim();
        }catch (Exception e){
            logger.error("failed to get the status of the policy :: getPolicyStatus "+e.getMessage());
            throw e;
        }
    }

    public LoginPageActions logoutApp(WebDriver driver) throws InterruptedException {
        logger.info("logging out from the application");
        clickProfileSettings(driver);
        signOutLink(driver).click();
        WaitHelper.waitForElementVisibility(driver, logInButton);
        return PageObjectManager.getLoginPageActions();
    }

    public void clickQuotesFilterList(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, quotesFilterListButton, 30);
        ClickHelper.clickElement(driver, quotesFilterListButton);
    }


    public void clickPoliciesFilterList(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, policiesFilterListButton, 30);
        ClickHelper.clickElement(driver, policiesFilterListButton);
    }

    public void clickFilterByCoverageName(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, filterByCoverageName);
        ClickHelper.clickElement(driver, filterByCoverageName);
    }

    public void selectActiveQuote(WebDriver driver) throws InterruptedException {
        try {
            List<WebElement> quotes = getQuoteCardsList(driver);
            for(WebElement quoteCard : quotes){
                if(quoteCard.getText().contains("Active")){
                    quoteCard.findElement(By.xpath("//button[text()='Continue']")).click();
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("No active quote is found "+e.getMessage());
            throw e;
        }
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
        WaitHelper.waitForElementVisibility(driver, noPolicyFoundText);
        return driver.findElement(noPolicyFoundText);
    }

    public WebElement noQuoteFound(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, noQuoteFoundText);
        return driver.findElement(noQuoteFoundText);
    }

    public boolean verifyWhetherResultsDisplayed(WebDriver driver) {
        return ClickHelper.isElementExist(driver, noSearchResultsText);
    }

    public String getSearchForNoResult(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, noSearchResultsText, 30);
        return TextHelper.getText(driver, noSearchResultsText, "text");
    }

    public String getFirstAvailableReferenceId(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, getFirstAvailableReferenceId, 45);
        return TextHelper.getText(driver, getFirstAvailableReferenceId, "text");
    }

    public String getFirstAvailablePolicyId(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, firstPolicyIdLocator, 45);
        return TextHelper.getText(driver, firstPolicyIdLocator, "text");
    }

    public String getFirstQuoteLegalName(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return TextHelper.getText(driver, getFirstAvailableLegalName, "text");
    }

    public String getFirstPolicyLegalName(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return TextHelper.getText(driver, firstPolicyCardLegalName, "text");
    }

    public boolean getAllPolicyLegalNames(WebDriver driver, String expected){
        List<String> allPoliciesLegalNames = new ArrayList<>();
        driver.findElements(policyLegalNames).forEach(legalName -> allPoliciesLegalNames.add(legalName.getText().trim()));
        for (String s : allPoliciesLegalNames) {
            if (s.contains(expected)) {
                return true;
            }
    }
        return false;
    }

    public List<String> getAllPoliciesStatus(WebDriver driver){
        try{
            List<String> allPoliciesStatus = new ArrayList<>();
            driver.findElements(policyStatus).forEach(status -> allPoliciesStatus.add(status.getText().trim()));
            return allPoliciesStatus;
        }catch (Exception e){
            logger.error("failed to get the policy status :: getAllPoliciesStatus "+e.getMessage());
            throw e;
        }
    }

    public String getFirstAvailableCreatedDate(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, firstAvailableCreatedDate, 45);
        return TextHelper.getText(driver, firstAvailableCreatedDate, "text");
    }

    public void clickQuoteSortBy(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, quoteSortBy);
        ClickHelper.clickElement(driver, quoteSortBy);
    }

    public void clickPolicySortBy(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, policySortBy);
        ClickHelper.clickElement(driver, policySortBy);
    }

    public void clickSortByNewest(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, sortByNewest);
        ClickHelper.clickElement(driver, sortByNewest);
    }

    public void clickSortByOldest(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, sortByOldest);
        ClickHelper.clickElement(driver, sortByOldest);
    }

    public void clickSortByExpiringLater(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, sortByExpiringLater);
        ClickHelper.clickElement(driver, sortByExpiringLater);
        WaitHelper.pause(2000);
    }

    public void clickSortByExpiringSoon(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, sortByExpiringSoon);
        ClickHelper.clickElement(driver, sortByExpiringSoon);
        WaitHelper.pause(2000);
    }

    public boolean verifyQuoteStatusInTable(WebDriver driver) {

        /**
         * this method verifies all the status of the quotes in the table
         * if table contains the quote with status not specified returns false
         */

        List<WebElement> quoteStatusList = driver.findElements(quoteStatus);
        boolean result = true;
        int count = quoteStatusList.size();
        Set<String> actualStatus = new HashSet<>();
        if (count > 0) {
            for (WebElement statusElement : quoteStatusList) {
                String status = statusElement.getText();
                if(!Objects.equals(status, "")){
                    actualStatus.add(status);
                }
            }
            logger.info("Actual Status::"+actualStatus);
            Set<String> expectedStatus = new HashSet<>();
            expectedStatus.add("Active");
            expectedStatus.add("In Review");
            expectedStatus.add("Approved");
            expectedStatus.add("Cancelled");
            expectedStatus.add("Declined");
            expectedStatus.add("Order Placed");
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

    public void selectCoverageInFilter(WebDriver driver, String product) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, allCoveragesDropdown);
        WebElement element = driver.findElement(allCoveragesDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, element, coverageOptions, product);
    }

    public void clickApplyFiltersButton(WebDriver driver) throws InterruptedException {
        ClickHelper.javaScriptExecutorClick(driver, applyFiltersButton);
        WaitHelper.pause(5000);
    }

    public void selectStatusInFilter(WebDriver driver, String status) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, allStatusDropdown, 30);
        WebElement dropdown = driver.findElement(allStatusDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, statusOptions, status);
    }

    public boolean selectPolicyStatusInFilter(WebDriver driver, String status) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, policyAllStatusDropdown);
        WebElement dropdown = driver.findElement(policyAllStatusDropdown);
        return DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, statusOptions, status);
    }

    public void clickPolicyFilterByStatus(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, policyFilterByStatus);
        ClickHelper.clickElement(driver, policyFilterByStatus);
    }

    public boolean clickFirstAvailableContinueButton (WebDriver driver) throws InterruptedException {
        if(ClickHelper.isElementExist(driver, fistAvailableContinueButton)){
            ClickHelper.clickElement(driver, fistAvailableContinueButton);
            WaitHelper.pause(5000);
            return true;
        }
        return false;
    }

    public void validateContinueSubmission(WebDriver driver) throws InterruptedException {
        List<WebElement> elementsContinueButton = driver.findElements(myPolicyCardGenericContinueButton);
        List<WebElement> elementStatus = driver.findElements(statusInDashboard);
        int count1 = elementStatus.size();
        Set<String> actualStatus = new HashSet<>();
        int count = elementsContinueButton.size();
        if (count > 0) {
            for (WebElement webElement : elementsContinueButton) {
                webElement.click();
                WaitHelper.pause(3000);
                break;
            }
        } else if (count1 > 0) {
            for (WebElement statusElement : elementStatus) {
                String status = statusElement.getText();
                actualStatus.add(status);
            }
            if (actualStatus.contains("cancelled") || actualStatus.contains("review") || actualStatus.contains("declined")) {
                assert count == 0;
            }
        }else {
            logger.error("======================== Continue Button is not available=======================================");
        }

    }
    public void clickExitRatingCriteria(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, exitRatingCriteria);
        ClickHelper.clickElement(driver, exitRatingCriteria);
    }

    public void enterCreateStartDate(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, createdStartDateField);
        TextHelper.enterText(driver, createdStartDateField, "01/07/2022");
    }

    public void enterCreateEndDate(WebDriver driver){
        TextHelper.enterText(driver, createdEndDateField, "04/07/2022");
    }

    public List<String> getQuoteCreatedDates(WebDriver driver){
        List<String> dates = new ArrayList<>();
        if(!ClickHelper.isElementExist(driver, noQuoteFoundText)){
            WaitHelper.waitForElementVisibility(driver, quoteCreatedDateGeneric);
            List<WebElement> createdDates = driver.findElements(quoteCreatedDateGeneric);
            if (!createdDates.isEmpty()){
                for (WebElement ele : createdDates) {
                    dates.add(ele.getText());
                }
            }
        }
        return dates;
    }

    public List<String> getPolicyExpirationDates(WebDriver driver){
        List<WebElement> createdDates = driver.findElements(policyExpirationDateGeneric);
        if (!createdDates.isEmpty()){
            List<String> dates = new ArrayList<>();
            for (WebElement ele : createdDates) {
                dates.add(ele.getText());
            }
            return dates;
        }
        return Collections.emptyList();
    }

    public void clickRenewButton (WebDriver driver) throws ParseException {

        List<String> dates = getPolicyExpirationDates(DriverManager.getDriver());
        Date actualDate;
        Date givenDate;
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

    public long getDifferenceInExpirationDateInDays (Date expirationDate, Date currentDate) {
        long difference = (expirationDate.getTime()-currentDate.getTime())/8640000;
        return Math.abs(difference);
    }


    public List<String> sortDates(List<String> dates) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        Map <Date, String> dateFormatMap = new TreeMap<>();
        for (String date: dates)
            dateFormatMap.put(f.parse(date), date);
        return new ArrayList<>(dateFormatMap.values());
    }

    public void clickSendRequestButton(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, supportRequestSendButton);
        WaitHelper.pause(4000);
    }

    public boolean supportTypeRequiredWarning(WebDriver driver){
        return ClickHelper.isElementExist(driver, supportTypeRequired);
    }

    public boolean supportRequestDetailsRequiredWarning(WebDriver driver){
        return ClickHelper.isElementExist(driver, supportRequestDetailsRequired);
    }

    public void selectSupportType(WebDriver driver, String supportType) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, supportTypeDropdown, 30);
        WebElement dropdown = driver.findElement(supportTypeDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, genericCoverageOption, supportType);
    }

    public void enterRequestDetails(WebDriver driver, String details){
        TextHelper.enterText(driver, supportRequestDetailTextArea, details);
    }

    public boolean isSupportTicketCreatedSuccessfully(WebDriver driver){
        return ClickHelper.isElementExist(driver, supportTicketSuccessMessage);
    }

    public void closeSuccessMessage(WebDriver driver){
        ClickHelper.clickElement(driver, closeSuccessMessageButton);
    }

    public void clickCancelSupportRequestButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, supportRequestCancelButton);
        ClickHelper.clickElement(driver, supportRequestCancelButton);
    }

    public void clickQuoteCardContinueButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quoteCardGenericContinueButton);
        ClickHelper.clickElement(driver, quoteCardGenericContinueButton);
    }

    public void clickFilterByStatus(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, clickFilterByStatus);
        ClickHelper.clickElement(driver, clickFilterByStatus);
    }

    public void clickClearSearch(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, clearSearch);
        WaitHelper.pause(5000);
    }

    public void verifyHideRenewButton(WebDriver driver, String status) throws InterruptedException {
        try {
               WebElement statusText = driver.findElement(getStatusText);
            if(status.equalsIgnoreCase(statusText.getText())) {
                 WaitHelper.pause(5000);
                 Assert.assertEquals(0, driver.findElements(renewButton).size());
            }
        }catch (Exception e){
            testLogger.fail("failed to verify hide renew button :: verifyHideRenewButton" + e.getMessage());
            logger.error("failed to verify hide renew button :: verifyHideRenewButton");
            throw (e);
        }
    }

    public void handleClearanceDialogIfDisplayed(WebDriver driver, String submitCancel){
        if(ClickHelper.isElementExist(driver, clearanceDialogPolicyDashboard)){
            if(submitCancel.equals("submit")){
                TextHelper.enterText(driver, clearanceDialogTextArea, "Testing Purpose");
                ClickHelper.clickElement(driver, clearanceDialogSubmitButton);
            }else{
                ClickHelper.clickElement(driver, clearanceDialogCancelButton);
            }
        }
    }

    public void renewSubmission(WebDriver driver) throws InterruptedException {
        int n = 1;
        while(n <= 3){
            List<WebElement> renewButtons = driver.findElements(genericRenewButtonLocator);
            if (!renewButtons.isEmpty()){
                renewButtons.get(0).click();
                WaitHelper.pause(6000);
                if(ClickHelper.isElementExist(driver, clearanceDialogPolicyDashboard)){
                    handleClearanceDialogIfDisplayed(driver, "submit");
                }
                break;
            }
            n++;
            ScrollHelper.scrollToBottom(driver);
        }
    }

    public String firstAvailableStatus(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, firstAvailableStatus);
        return TextHelper.getText(driver, firstAvailableStatus, "text");
    }

    public void clickQuoteIt(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quoteitLogo);
        driver.findElement(quoteitLogo).click();
    }

    public void clickFilterByType(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, quotesFilterByType);
        ClickHelper.clickElement(driver, quotesFilterByType);
    }

    public void selectTypeInFilter(WebDriver driver, String status) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, allTypesDropdown);
        WebElement dropdown = driver.findElement(allTypesDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, quoteTypeOption, status);
    }

    public List<WebElement> getAllQuotesBusinessType(WebDriver driver){

        return driver.findElements(quoteBusinessType);
    }

    public void clickClearFiltersButton(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, clearFilterButton);
        WaitHelper.pause(3000);
    }

    public String getSelectedCoverageName(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return driver.findElement(allCoveragesDropdown).getText();
    }

    public String getSelectedQuoteStatus(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(2000);
        return driver.findElement(allStatusDropdown).getText();
    }

    public String getSelectedQuoteBusinessType(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(2000);
        return driver.findElement(allTypesDropdown).getText();
    }

    public boolean verifyContactUnderwriterExists(WebDriver driver){
        try {
            return ClickHelper.isElementExist(driver, contactUnderwriter);
        }catch (Exception e){
            testLogger.fail("failed to verify the contactUnderwriter :: verifyContactUnderwriterExists" + e.getMessage());
            logger.error("failed to verify the contactUnderwriter :: verifyContactUnderwriterExists");
            throw (e);
        }
    }

    public void clickContactUnderwriter(WebDriver driver){
        try {
            ClickHelper.clickElement(driver, contactUnderwriter);
        }catch (Exception e){
            testLogger.fail("failed to click on the contact underwriter button " + e.getMessage());
            logger.error("failed to click on the contact underwriter button " + e.getMessage());
            throw (e);
        }
    }

    public String getSubmitForReviewDesc(WebDriver driver) throws InterruptedException {
        try {
            WaitHelper.pause(3000);
            return TextHelper.getText(driver, submitForReviewDesc, "text");
        }catch (Exception e){
            testLogger.fail("failed to get the text from Submit for Review Dialog " + e.getMessage());
            logger.error("failed to get the text from Submit for Review Dialog " + e.getMessage());
            throw (e);
        }
    }

    public void clickSubmitForReviewCancel(WebDriver driver){
        try {
            ClickHelper.clickElement(driver, submitForReviewCancel);
        }catch (Exception e){
            testLogger.fail("failed to click Submit for Review cancel button " + e.getMessage());
            logger.error("failed to click Submit for Review cancel button " + e.getMessage());
            throw (e);
        }
    }

    public void clickSubmitForReviewSubmit(WebDriver driver){
        try {
            ClickHelper.clickElement(driver, submitForReviewSubmit);
        }catch (Exception e){
            testLogger.fail("failed to click Submit for Review submit button " + e.getMessage());
            logger.error("failed to click Submit for Review submit button " + e.getMessage());
            throw (e);
        }
    }

    public void enterAdditionalInfoSubmitForReview(WebDriver driver){
        try {
            TextHelper.enterText(driver, clearanceDialogTextArea, ConstantVariable.SAMPLE_TEXT);
        }catch (Exception e){
            testLogger.fail("failed to enter additional info to Submit for Review dialog " + e.getMessage());
            logger.error("failed to enter additional info to Submit for Review dialog " + e.getMessage());
            throw (e);
        }
    }

    public boolean verifyUnderwriterReviewingButtonDisplayed(WebDriver driver){
        try {
            return ClickHelper.isElementExist(driver, underwriterReviewingButton);
        }catch (Exception e){
            testLogger.fail("failed to check whether underwriter reviewing button displayed or not " + e.getMessage());
            logger.error("failed to check whether underwriter reviewing button displayed or not  " + e.getMessage());
            throw (e);
        }
    }




    public WebElement getPolicyRenewButton(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        List<WebElement> renewButtons = driver.findElements(genericRenewButtonLocator);
        if(!renewButtons.isEmpty()){
            return renewButtons.get(0);
        }
        return null;
    }

    public boolean verifyIfPolicySearchResultsDisplayed(WebDriver driver){
        try{
            List<WebElement> results = driver.findElements(policySearchResults);
            if(!results.isEmpty()){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

}
