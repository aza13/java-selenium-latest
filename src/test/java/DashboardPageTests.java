import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import enums.ConstantVariable;
import helper.ClickHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.LoginPageActions;
import utils.dataProvider.TestDataProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageTests.class);
    private DashboardPageActions dashboardPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("DashboardPageTests");
        logger.info("Creating object for DashboardPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testQuotesDashboardUI(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies UI of dashboard and Mu Quotes list
         story - N2020-28285, N2020-28287, N2020-28631
         **/
        logger.info("verifying the broker portal dashboard page :: testQuotesDashboardUI");
        assert dashboardPageActions.tmhccLogo(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.profileSettingsIcon(DriverManager.getDriver()).isDisplayed();
        String title = dashboardPageActions.getMyQuotesTabTitle(DriverManager.getDriver());
        assert title.contentEquals(map.get("myQuotes"));
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        assert dashboardPageActions.profileLink(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.signOutLink(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        List<WebElement> quoteCardsList = dashboardPageActions.getQuoteCardsList(DriverManager.getDriver());
        if (quoteCardsList.size() > 0) {
            assert true;
        } else {
            assert dashboardPageActions.noQuoteFound(DriverManager.getDriver()).isDisplayed();
        }
        List<WebElement> labels = dashboardPageActions.getQuoteTableLabels(DriverManager.getDriver());
        if (labels.size()>0){
            assert labels.get(0).getText().equals(map.get("submissionLabel"));
            assert labels.get(1).getText().equals(map.get("dateLabel"));
            assert labels.get(2).getText().equals(map.get("startDateLabel"));
            assert labels.get(3).getText().equals(map.get("endDateLabel"));
            assert labels.get(4).getText().equals(map.get("statusLabel"));
            logger.info("verify quote status color");
            dashboardPageActions.validateQuoteStatusColorCoding(DriverManager.getDriver());
            logger.info("verify quote correct status displayed");
            assert dashboardPageActions.verifyQuoteStatusInTable(DriverManager.getDriver());
        }
        logger.info("verify logout functionality");
        LoginPageActions loginPageActions = dashboardPageActions.logoutApp(DriverManager.getDriver());
        String text = loginPageActions.getWelcomeText(DriverManager.getDriver());
        assert text.contentEquals("Welcome to the Broker Portal");

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testPoliciesDashboardUI(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies UI of My Policies dashboard
         story - N2020-28286
         **/
        logger.info("verifying the broker portal dashboard page :: testPoliciesDashboardUI");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        String title = dashboardPageActions.getMyPoliciesTabTitle(DriverManager.getDriver());
        assert title.contentEquals(map.get("policyTitle"));
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        List<WebElement> policyCardsList = dashboardPageActions.getPolicyCardsList(DriverManager.getDriver());
        if (policyCardsList.size() > 0) {
            assert true;
        } else {
            assert dashboardPageActions.noPolicyFound(DriverManager.getDriver()).isDisplayed();
        }
        /* Status color changes would be coming soon with hexa codes
        dashboardPageActions.validatePolicyStatusColorCoding(DriverManager.getDriver());*/
        List<WebElement> labels = dashboardPageActions.getPolicyTableLabels(DriverManager.getDriver());
        if(labels.size()>0){
            String policyLabel = labels.get(0).getText();
            assert policyLabel.equals(map.get("policyLabel"));
            String effDateLabel = labels.get(1).getText();
            assert effDateLabel.equals(map.get("effDateLabel"));
            String expDateLabel = labels.get(2).getText();
            assert expDateLabel.equals(map.get("expDateLabel"));
        }else{
            throw new SkipException("No policies were found for the given broker");
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testNewQuoteFieldsValidation(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies the New Quote dialog fields validation
         story - N2020-28289
         **/
        logger.info("validating the fields on New Quote modal dialog :: testNewQuoteFieldsValidation");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        logger.info("validating whether mandatory field text displayed or not");
        assert dashboardPageActions.productRequiredElement(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.nameRequiredElement(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.websiteRequiredElement(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        dashboardPageActions.clickCancelButton(DriverManager.getDriver());
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        logger.info("validating whether the data entered is erased or not");
        String product = dashboardPageActions.productDropdown(DriverManager.getDriver()).getText();
        assert product.equals(map.get("productDefaultText"));
        String name = dashboardPageActions.getApplicantName(DriverManager.getDriver());
        assert name.equals(ConstantVariable.EMPTY_STRING);
        String website = dashboardPageActions.getWebsite(DriverManager.getDriver());
        assert website.equals(ConstantVariable.EMPTY_STRING);
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testCreateNewQuote(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies creation of new quote
         story - N2020-28291
         **/
        logger.info("verifying creating new quote creation :: testCreateNewQuote");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        assert insuredPageActions.newInsuredButton(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.searchAgainButton(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.verifyInsuredSearchResult(DriverManager.getDriver(), map.get("applicantName"), map.get("website"));
        insuredPageActions.clickContinueInsuredButton(DriverManager.getDriver());
        boolean duplicateDialog = insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver());
        if(!duplicateDialog){
            assert insuredPageActions.continueInsuredSearch(DriverManager.getDriver()).isDisplayed();
        }else{
            logger.info("Can't continue to insured search page, duplicate submission displayed");
            throw new SkipException("The test is Ignored, because can't continue to insured search page, duplicate submission dialog is displayed");
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testBrokerFilteringSubmissionsList(Map<String, String> map) throws InterruptedException, ParseException {
        /**
         * this test verifies broker filtering the submissions list
         story - N2020-28566
         **/
        logger.info("verifying broker filtering the submission list :: testBrokerFilteringSubmissionsList");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        String[] products = map.get("productName").split(ConstantVariable.SEMICOLON);
        for (String product : products) {
            dashboardPageActions.clickFilterList(DriverManager.getDriver());
            dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
            dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), product);
            dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
            List<String> productNames = dashboardPageActions.getAllQuotesProductName(DriverManager.getDriver());
            if (productNames.size() > 0) {
                for (String prod : productNames) {
                    assert prod.contentEquals(product);
                }
            }
        }
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("allProducts"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        String[] statuses = map.get("status").split(ConstantVariable.SEMICOLON);
        for (String status : statuses) {
            dashboardPageActions.clickFilterList(DriverManager.getDriver());
            dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
            dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), status);
            dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
            List<String> stat = dashboardPageActions.getAllQuotesStatus(DriverManager.getDriver());
            if (stat.size() > 0) {
                for (String s : stat) {
                    assert s.contentEquals(status);
                }
            }
        }
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("allStatuses"));
        dashboardPageActions.clickSubmissionFilterByDateRange(DriverManager.getDriver());
        dashboardPageActions.enterCreateStartDate(DriverManager.getDriver());
        dashboardPageActions.enterCreateEndDate(DriverManager.getDriver());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        List<String> dates = dashboardPageActions.getQuoteCreatedDates(DriverManager.getDriver());
        DateFormat df = new SimpleDateFormat(ConstantVariable.DATE_FORMAT);
        Date actualDate, givenDate;
        givenDate = df.parse(map.get("endDate"));
        for (String date: dates) {
            try {
                actualDate = df.parse(date);
                if (actualDate.compareTo(givenDate)<=0){
                    assert true;
                }else{
                    assert false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testBrokerFilteringPoliciesList(Map<String, String> map) throws InterruptedException, ParseException {
        /**
         * this test verifies broker filtering the policies list
         story - N2020-28565
         **/
        logger.info("verifying broker filtering the policies list :: testBrokerFilteringPoliciesList");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        String[] statuses = map.get("status").split(ConstantVariable.SEMICOLON);
        for (String status : statuses) {
            dashboardPageActions.clickFilterList(DriverManager.getDriver());
            dashboardPageActions.clickPolicyFilterByStatus(DriverManager.getDriver());
            dashboardPageActions.selectPolicyStatusInFilter(DriverManager.getDriver(), status);
            dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
            List<String> stat = dashboardPageActions.getAllQuotesStatus(DriverManager.getDriver());
            if (stat.size() > 0) {
                for (String s : stat) {
                    assert s.contentEquals(status);
                }
            }
        }
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickPolicyFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectPolicyStatusInFilter(DriverManager.getDriver(), map.get("allStatuses"));
        dashboardPageActions.clickSubmissionFilterByDateRange(DriverManager.getDriver());
        dashboardPageActions.enterCreateStartDate(DriverManager.getDriver());
        dashboardPageActions.enterCreateEndDate(DriverManager.getDriver());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        List<String> dates = dashboardPageActions.getPolicyExpirationDates(DriverManager.getDriver());
        DateFormat df = new SimpleDateFormat(ConstantVariable.DATE_FORMAT);
        Date actualDate, givenDate;
        String d = map.get("endDate");
        givenDate = df.parse(d);
        for (String date: dates) {
            try {
                actualDate = df.parse(date);
                if (actualDate.compareTo(givenDate)<=0){
                    assert true;
                }else{
                    assert false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testPresenceOfContinueButtonOnQuotes(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies whether continue button should be displayed or not quotes in MY QUOTES
         story - N2020-28296
         **/
        logger.info("verifying broker filtering the policies list :: testBrokerFilteringPoliciesList");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        List<WebElement> quoteCards = dashboardPageActions.getQuoteCardsList(DriverManager.getDriver());
        int quoteCount = quoteCards.size();
        logger.info("validating whether continue button displayed for only specific statuses");
        if (quoteCount > 0) {
            String continueBtnXpath;
            for (int i = 1; i <= quoteCount; i++) {
                continueBtnXpath = "(//div[@data-qa='quote_card']//p[@data-qa='status'])["+i+"]/parent::div/parent::div/following-sibling::div//button";
                By continueButton = By.xpath(continueBtnXpath);
                String status = dashboardPageActions.getGivenQuoteStatus(DriverManager.getDriver(), i);
                switch (status) {
                    case ConstantVariable.CANCELLED_STRING:
                    case ConstantVariable.DECLINED_STRING:
                    case ConstantVariable.IN_REVIEW_STRING:
                        assert !ClickHelper.isElementExist(DriverManager.getDriver(), continueButton);
                        break;
                    case ConstantVariable.ACTIVE_STRING:
                        assert ClickHelper.isElementExist(DriverManager.getDriver(), continueButton);
                }
            }
            logger.info("validating the continue button in search results");
            List<String> referenceIds = dashboardPageActions.getAllQuoteReferenceIds(DriverManager.getDriver());
            int refIdCount = referenceIds.size();
            Random ran = new Random();
            if(refIdCount > 3) {
                for (int j = 0; j <= 3; j++) {
                    int index = ran.nextInt(refIdCount);
                    String refId = referenceIds.get(index);
                    dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), refId);
                    List<WebElement> quoteCards2 = dashboardPageActions.getQuoteCardsList(DriverManager.getDriver());
                    int quoteCount2 = quoteCards2.size();
                    if (quoteCount2 > 0) {
                        String continueBtnXpath2;
                        for (int i = 1; i <= quoteCount2; i++) {
                            String status2 = dashboardPageActions.getGivenQuoteStatus(DriverManager.getDriver(), i);
                            continueBtnXpath2 = "(//div[@data-qa='quote_card']//p[@data-qa='status'])["+i+"]/parent::div/parent::div/following-sibling::div//button";
                            By continueButton2 = By.xpath(continueBtnXpath2);
                            switch (status2) {
                                case ConstantVariable.CANCELLED_STRING:
                                case ConstantVariable.DECLINED_STRING:
                                case ConstantVariable.IN_REVIEW_STRING:
                                    assert !ClickHelper.isElementExist(DriverManager.getDriver(), continueButton2);
                                    break;
                                case ConstantVariable.ACTIVE_STRING:
                                    assert ClickHelper.isElementExist(DriverManager.getDriver(), continueButton2);
                            }
                        }
                    }
                    dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
                }
            }

        }


    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testBrokerSearchRelatedRecords(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies search results for related Records
         story - N2020-28288
         **/
        logger.info("verifying broker can search for related records :: testBrokerSearchRelatedRecords");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));

        String actualReferenceId = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(),actualReferenceId );
        String expectedReferenceId = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
        assert actualReferenceId.equals(expectedReferenceId);

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        String actualQuoteName = dashboardPageActions.getFirstAvailableLegalName(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualQuoteName);
        String expectedQuoteName = dashboardPageActions.getFirstAvailableLegalName(DriverManager.getDriver());
        assert actualQuoteName.equals(expectedQuoteName);

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        String actualPolicyName = dashboardPageActions.getFirstAvailableLegalName(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualPolicyName);
        String expectedPolicyName = dashboardPageActions.getFirstAvailableLegalName(DriverManager.getDriver());
        assert actualPolicyName.equals(expectedPolicyName);

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        String actualPolicyNumber = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualPolicyNumber);
        String expectedPolicyNumber = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
        assert actualPolicyNumber.equals(expectedPolicyNumber);

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("noSuchARecord"));
        String searchForNoResult = dashboardPageActions.getSearchForNoResult(DriverManager.getDriver());
        assert searchForNoResult.contentEquals(map.get("expForNoSuchARecord"));
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testSubmissionRenewal(Map<String, String> map) throws InterruptedException, ParseException {
        /***
         this test submission renewal
         story - N2020-28481
         **/
        logger.info("verifying submission renewal ::  testSubmissionRenewal");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.clickRenewButton(DriverManager.getDriver());

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void  testSortQuoteList(Map<String, String> map) throws InterruptedException, ParseException {
        /***
         this test Sort the My Quotes List
         story - N2020-29952
         **/

        logger.info("verifying sort my quote list ::  sortQuoteList");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        WaitHelper.pause(2000);
        String actual = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        dashboardPageActions.clickSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByNewest(DriverManager.getDriver());
        dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        String expected = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        assert actual.equals(expected);

        dashboardPageActions.clickSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByOldest(DriverManager.getDriver());
        String actualOldestDate = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        dashboardPageActions.clickSortByOldest(DriverManager.getDriver());
        String expectedOldestDate = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        assert actualOldestDate.equals(expectedOldestDate);

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void  testSortPolicyList(Map<String, String> map) throws InterruptedException, ParseException {
        /***
         this test Sort my Policy List
         story - N2020-29736
         **/

        logger.info("verifying sort my quote list ::  sortPolicyList");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.clickSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByExpiringSoon(DriverManager.getDriver());
        List<String> datesSortedByExpiringSoon = dashboardPageActions.getPolicyExpirationDates(DriverManager.getDriver());
        List<String> sortedDatesByExpiringSoonAsc = dashboardPageActions.sortDates((ArrayList<String>) datesSortedByExpiringSoon);
        dashboardPageActions.clickSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByExpiringLater(DriverManager.getDriver());
        List<String> datesSortedByExpiringLater = dashboardPageActions.getPolicyExpirationDates(DriverManager.getDriver());
        List<String> sortedDatesByExpiringLaterAsc = dashboardPageActions.sortDates((ArrayList<String>) datesSortedByExpiringLater);
        boolean isEqual = sortedDatesByExpiringLaterAsc.equals(sortedDatesByExpiringSoonAsc);
        if (!isEqual) {
            logger.info("======================== Sorting my policy is working as expected ========================================");
        }else {
            logger.error("======================== Sorting my policy is not working as expected ===================================");
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testSupportRequestFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies sup of new insured
         story - N2020-28346
         **/
        logger.info("verifying duplicate submissions :: testSupportRequestFunctionality");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.clickSupportLink(DriverManager.getDriver());
        dashboardPageActions.selectSupportType(DriverManager.getDriver(), map.get("supportType"));
        dashboardPageActions.enterRequestDetails(DriverManager.getDriver(), map.get("requestDetails"));
        dashboardPageActions.clickSendRequestButton(DriverManager.getDriver());
        assert dashboardPageActions.isSupportTicketCreatedSuccessfully(DriverManager.getDriver());
        dashboardPageActions.closeSuccessMessage(DriverManager.getDriver());
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.clickSupportLink(DriverManager.getDriver());
        dashboardPageActions.clickSendRequestButton(DriverManager.getDriver());
        assert dashboardPageActions.supportTypeRequiredWarning(DriverManager.getDriver());
        assert dashboardPageActions.supportRequestDetailsRequiredWarning(DriverManager.getDriver());
        dashboardPageActions.clickCancelSupportRequestButton(DriverManager.getDriver());
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void  testBrokersCanContinueRenewalSubmission(Map<String, String> map) throws InterruptedException, ParseException {
        /***
         this test Brokers can continue a Renewal Submission
         story - N2020-28483
         **/

        logger.info("verifying :: continue a Renewal Submission ");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.validateContinueSubmission(DriverManager.getDriver());
        dashboardPageActions.clickExitRatingCriteria(DriverManager.getDriver());
    }

}
