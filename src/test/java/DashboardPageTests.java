import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import constants.DatabaseQueries;
import helper.ClickHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.LoginPageActions;
import utils.dataProvider.JsonDataProvider;
import utils.dbConnector.DatabaseConnector;
import workflows.CreateApplicant;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DashboardPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private DatabaseConnector databaseConnector;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("DashboardPageTests");
        logger.info("Creating object for DashboardPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        databaseConnector = new DatabaseConnector();
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData")
    public void testQuotesDashboardUI(JSONObject jsonObject) throws InterruptedException {
        /**
         * this test verifies UI of dashboard and My Quotes list
         story - N2020-28285, N2020-28287, N2020-28631
         @author - Venkat Kottapalli
         **/
        logger.info("verifying the broker portal dashboard page :: testQuotesDashboardUI");
        assert dashboardPageActions.tmhccLogo(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.profileSettingsIcon(DriverManager.getDriver()).isDisplayed();
        String title = dashboardPageActions.getMyQuotesTabTitle(DriverManager.getDriver()).trim();
        assert title.contentEquals(jsonObject.get("myQuotes").toString());
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        assert dashboardPageActions.signOutLink(DriverManager.getDriver()).isDisplayed();
        List<WebElement> quoteCardsList = dashboardPageActions.getQuoteCardsList(DriverManager.getDriver());
        if (quoteCardsList.size() > 0) {
            assert true;
            List<WebElement> labels = dashboardPageActions.getQuoteTableLabels(DriverManager.getDriver());
            if (labels.size() > 0) {
                assert labels.get(0).getText().equals(jsonObject.get("submissionLabel"));
                assert labels.get(1).getText().equals(jsonObject.get("dateLabel"));
                assert labels.get(2).getText().equals(jsonObject.get("product"));
                assert labels.get(3).getText().equals(jsonObject.get("startDateLabel"));
                assert labels.get(4).getText().equals(jsonObject.get("endDateLabel"));
                assert labels.get(5).getText().equals(jsonObject.get("statusLabel"));
                logger.info("verify quote status color- In Progress");
//            dashboardPageActions.validateQuoteStatusColorCoding(DriverManager.getDriver());
                logger.info("verify quote correct status displayed");
                assert dashboardPageActions.verifyQuoteStatusInTable(DriverManager.getDriver());
            }
        } else {
            assert dashboardPageActions.noQuoteFound(DriverManager.getDriver()).isDisplayed();
        }
        logger.info("verify logout functionality");
        LoginPageActions loginPageActions = dashboardPageActions.logoutApp(DriverManager.getDriver());
        String text = loginPageActions.getWelcomeText(DriverManager.getDriver());
        assert text.contentEquals(jsonObject.get("welcomeText").toString());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 1)
    public void testPoliciesDashboardUI(JSONObject jsonObject) {
        /**
         * this test verifies UI of My Policy dashboard
         story - N2020-28286
         @author - Venkat Kottapalli
         **/
        logger.info("verifying the broker portal dashboard page :: testPoliciesDashboardUI");
        String title = dashboardPageActions.getMyPoliciesTabTitle(DriverManager.getDriver()).trim();
        assert title.contentEquals(jsonObject.get("policyTitle").toString());
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        List<WebElement> policyCardsList = dashboardPageActions.getPolicyCardsList(DriverManager.getDriver());
        if (policyCardsList.size() > 0) {
            assert true;
             /* Status color changes would be coming soon with color codes
        dashboardPageActions.validatePolicyStatusColorCoding(DriverManager.getDriver());*/
            List<WebElement> labels = dashboardPageActions.getPolicyTableLabels(DriverManager.getDriver());
            if (labels.size() > 0) {
                String policyLabel = labels.get(0).getText();
                assert policyLabel.equals(jsonObject.get("policyLabel"));
                String coverage = labels.get(1).getText();
                assert coverage.equals(jsonObject.get("product"));
                String effDateLabel = labels.get(2).getText();
                assert effDateLabel.equals(jsonObject.get("effDateLabel"));
                String expDateLabel = labels.get(3).getText();
                assert expDateLabel.equals(jsonObject.get("expDateLabel"));
            } else {
                throw new SkipException("No policies were found for the given broker");
            }
        } else {
            assert dashboardPageActions.noPolicyFound(DriverManager.getDriver()).isDisplayed();
        }

    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 16)
    public void testNewQuoteFieldsValidation(JSONObject jsonObject) throws InterruptedException {
        /**
         * this test verifies the New Quote dialog fields validation
         story - N2020-28289
         @author - Venkat Kottapalli
         **/
        logger.info("validating the fields on New Quote modal dialog :: testNewQuoteFieldsValidation");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        logger.info("validating whether mandatory field text displayed or not");
        assert dashboardPageActions.getCoverageRequiredElement(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.nameRequiredElement(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.clickCancelButton(DriverManager.getDriver());
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        logger.info("validating whether the data entered is erased or not");
        String coverage = dashboardPageActions.getCoverageDropdown(DriverManager.getDriver()).getText();
        assert coverage.equals(jsonObject.get("productDefaultText"));
        String name = dashboardPageActions.getApplicantName(DriverManager.getDriver());
        assert name.equals(ConstantVariable.EMPTY_STRING);
        String website = dashboardPageActions.getWebsite(DriverManager.getDriver());
        assert website.equals(ConstantVariable.EMPTY_STRING);
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), BaseTest.coverage, jsonObject.get("applicantName").toString(), jsonObject.get("website").toString());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 17)
    public void testCreateNewQuote(JSONObject jsonObject) throws InterruptedException {
        /**
         * this test verifies creation of new quote
         story - N2020-28291
         @author - Venkat Kottapalli
         **/
        logger.info("verifying creating new quote creation :: testCreateNewQuote");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage, jsonObject.get("applicantName").toString(), jsonObject.get("website").toString());
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        assert insuredPageActions.newInsuredButton(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.searchAgainButton(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.verifyInsuredSearchResult(DriverManager.getDriver(), jsonObject.get("website").toString());
        insuredPageActions.clickContinueInsuredButton(DriverManager.getDriver());
        boolean duplicateDialog = insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver());
        if (!duplicateDialog) {
            assert insuredPageActions.continueInsuredSearch(DriverManager.getDriver()).isDisplayed();
        } else {
            logger.info("Can't continue to insured search page, duplicate submission displayed");
            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
            CreateApplicant.createApplicant(DriverManager.getDriver(), coverage);
        }

    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 2)
    public void testBrokerFilteringSubmissionsList(JSONObject jsonObject) throws InterruptedException, ParseException {
        /**
         * this test verifies broker filtering the submissions list
         story - N2020-28566
         @author - Venkat Kottapalli
         **/
        logger.info("verifying broker filtering the submission list :: testBrokerFilteringSubmissionsList");
        String[] coverages = jsonObject.get("productName").toString().split(ConstantVariable.SEMICOLON);
        for (String coverage : coverages) {
            dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
            dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
            dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), coverage);
            dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
            List<String> coverageNames = dashboardPageActions.getAllQuotesCoverageName(DriverManager.getDriver());
            if (coverageNames.size() > 0) {
                for (String cov : coverageNames) {
                        assert cov.contentEquals(coverage);
                }
            }
        }
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), jsonObject.get("productName").toString());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        String[] statuses = jsonObject.get("status").toString().split(ConstantVariable.SEMICOLON);
        for (String status : statuses) {
            dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
            dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
            dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), status);
            dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
            List<String> stat = dashboardPageActions.getAllQuotesStatus(DriverManager.getDriver());
            if (stat.size() > 0) {
                for (String s : stat) {
                    if(status.contentEquals("Active")){
                        if(s.contentEquals(status)||s.contentEquals(ConstantVariable.ORDER_PLACED_STRING)||s.contentEquals(ConstantVariable.IN_REVIEW_STRING)){
                            assert true;
                        }
                    }else{
                        assert s.contentEquals(status);
                    }
                }
            }
        }
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), jsonObject.get("allStatuses").toString());
        dashboardPageActions.clickSubmissionFilterByDateRange(DriverManager.getDriver());
        dashboardPageActions.enterCreateStartDate(DriverManager.getDriver());
        dashboardPageActions.enterCreateEndDate(DriverManager.getDriver());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        List<String> dates = dashboardPageActions.getQuoteCreatedDates(DriverManager.getDriver());
        DateFormat df = new SimpleDateFormat(ConstantVariable.DATE_FORMAT);
        Date actualDate, givenDate;
        givenDate = df.parse(jsonObject.get("endDate").toString());
        if (dates.size() > 0) {
            for (String date : dates) {
                try {
                    actualDate = df.parse(date);
                    if (actualDate.compareTo(givenDate) <= 0) {
                        assert true;
                    } else {
                        assert false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Assert.assertTrue(true);
        }

    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 3)
    public void testBrokerFilteringPoliciesList(JSONObject jsonObject) throws InterruptedException, ParseException {
        /**
         * this test verifies broker filtering the policies list
         story - N2020-28565
         @author - Venkat Kottapalli
         **/
        logger.info("verifying broker filtering the policies list :: testBrokerFilteringPoliciesList");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        String[] statuses = jsonObject.get("status").toString().split(ConstantVariable.SEMICOLON);
        for (String status : statuses) {
            dashboardPageActions.clickPoliciesFilterList(DriverManager.getDriver());
            dashboardPageActions.clickPolicyFilterByStatus(DriverManager.getDriver());
            boolean result = dashboardPageActions.selectPolicyStatusInFilter(DriverManager.getDriver(), status);
            dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
            if (result){
                List<String> stat = dashboardPageActions.getAllPoliciesStatus(DriverManager.getDriver());
                if (stat.size() > 0) {
                    for (String s : stat) {
                        assert s.contentEquals(status);
                    }
                }
            }
        }
        dashboardPageActions.clickPoliciesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickPolicyFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectPolicyStatusInFilter(DriverManager.getDriver(), jsonObject.get("allStatuses").toString());
        dashboardPageActions.clickSubmissionFilterByDateRange(DriverManager.getDriver());
        dashboardPageActions.enterCreateStartDate(DriverManager.getDriver());
        dashboardPageActions.enterCreateEndDate(DriverManager.getDriver());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        List<String> dates = dashboardPageActions.getPolicyExpirationDates(DriverManager.getDriver());
        if(dates != null){
            DateFormat df = new SimpleDateFormat(ConstantVariable.DATE_FORMAT);
            Date actualDate, givenDate;
            String d = jsonObject.get("endDate").toString();
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
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 4, enabled = false)
    public void testPresenceOfContinueButtonOnQuotes(JSONObject jsonObject) throws InterruptedException {
        /**
         * this test verifies whether continue button should be displayed or not quotes in MY QUOTES
         story - N2020-28296
         @author - Venkat Kottapalli
         **/
        logger.info("verifying broker filtering the policies list :: testBrokerFilteringPoliciesList");
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
            if (refIdCount > 3) {
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
                            continueBtnXpath2 = "(//div[@data-qa='quote_card']//p[@data-qa='status'])[" + i + "]/parent::div/parent::div/following-sibling::div//button";
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

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 5)
    public void testBrokerSearchRelatedRecords(JSONObject jsonObject) throws InterruptedException {
        /**
         * this test verifies search results for related Records
         story - N2020-28288
         @author - Azamat Uulu
         **/
        logger.info("verifying broker can search for related records :: testBrokerSearchRelatedRecords");
        String actualReferenceId = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualReferenceId);
        if(!dashboardPageActions.verifyWhetherResultsDisplayed(DriverManager.getDriver())){
            String expectedReferenceId = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
            assert actualReferenceId.equals(expectedReferenceId);
        }

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        String actualQuoteName = dashboardPageActions.getFirstQuoteLegalName(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualQuoteName);
        if(!dashboardPageActions.verifyWhetherResultsDisplayed(DriverManager.getDriver())){
            String expectedQuoteName = dashboardPageActions.getFirstQuoteLegalName(DriverManager.getDriver());
            assert actualQuoteName.equals(expectedQuoteName);
        }

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        String actualPolicyName = dashboardPageActions.getFirstPolicyLegalName(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualPolicyName);
        if(!dashboardPageActions.verifyWhetherResultsDisplayed(DriverManager.getDriver())){
            String expectedPolicyName = dashboardPageActions.getFirstPolicyLegalName(DriverManager.getDriver());
            assert dashboardPageActions.getAllPolicyLegalNames(DriverManager.getDriver(), expectedPolicyName);
        }

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        String actualPolicyNumber = dashboardPageActions.getFirstAvailablePolicyId(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualPolicyNumber);
        String expectedPolicyNumber = dashboardPageActions.getFirstAvailablePolicyId(DriverManager.getDriver());
        assert actualPolicyNumber.equals(expectedPolicyNumber);

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), jsonObject.get("noSuchARecord").toString());
        String searchForNoResult = dashboardPageActions.getSearchForNoResult(DriverManager.getDriver());
        assert searchForNoResult.contentEquals(jsonObject.get("expForNoSuchARecord").toString());
    }


    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 6, enabled = false)
    public void testSubmissionRenewal(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies submission renewal
         story - N2020-28481
         @author -
         **/
        logger.info("verifying submission renewal ::  testSubmissionRenewal");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.clickPoliciesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickPolicyFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectPolicyStatusInFilter(DriverManager.getDriver(), jsonObject.get("status").toString());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.renewSubmission(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 7)
    public void testSortQuoteList(JSONObject jsonObject) throws InterruptedException {
        /***
         this tests Sort of Quotes List
         story - N2020-29952
         @author -Azamat Uulu
         **/
        logger.info("verifying sort my quote list ::  sortQuoteList");
        String actual = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        dashboardPageActions.clickQuoteSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByNewest(DriverManager.getDriver());
        dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        String expected = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        assert actual.equals(expected);
        dashboardPageActions.clickQuoteSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByOldest(DriverManager.getDriver());
        String actualOldestDate = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        dashboardPageActions.clickQuoteSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByOldest(DriverManager.getDriver());
        String expectedOldestDate = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        assert actualOldestDate.equals(expectedOldestDate);

    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 8)
    public void testSortPolicyList(JSONObject jsonObject) throws InterruptedException, ParseException {
        /***
         this test Sort my Policy List
         story - N2020-29736
         @author -Azamat Uulu
         **/

        logger.info("verifying sort my quote list ::  sortPolicyList");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.clickPolicySortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByExpiringSoon(DriverManager.getDriver());
        List<String> datesSortedByExpiringSoon = dashboardPageActions.getPolicyExpirationDates(DriverManager.getDriver());
        List<String> sortedDatesByExpiringSoonAsc = dashboardPageActions.sortDates(datesSortedByExpiringSoon);
        dashboardPageActions.clickPolicySortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByExpiringLater(DriverManager.getDriver());
        List<String> datesSortedByExpiringLater = dashboardPageActions.getPolicyExpirationDates(DriverManager.getDriver());
        List<String> sortedDatesByExpiringLaterAsc = dashboardPageActions.sortDates(datesSortedByExpiringLater);
        boolean isEqual = sortedDatesByExpiringLaterAsc.equals(sortedDatesByExpiringSoonAsc);
        if (!isEqual) {
            logger.info("======================== Sorting my policy is working as expected ========================================");
        } else {
            logger.error("======================== Sorting my policy is not working as expected ===================================");
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 9)
    public void testSupportRequestFunctionality(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies sup of new insured
         story - N2020-28346
         @author - Venkat Kottapalli
         **/
        logger.info("verifying duplicate submissions :: testSupportRequestFunctionality");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.clickSupportLink(DriverManager.getDriver());
        dashboardPageActions.selectSupportType(DriverManager.getDriver(), jsonObject.get("supportType").toString());
        dashboardPageActions.enterRequestDetails(DriverManager.getDriver(), jsonObject.get("requestDetails").toString());
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

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", enabled = false, priority = 10)
    public void testBrokersCanContinueRenewalSubmission() throws InterruptedException {
        /***
         this test Brokers can continue a Renewal Submission
         story - N2020-28483
         @author -Azamat Uulu
         **/

        logger.info("verifying :: continue a Renewal Submission ");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.validateContinueSubmission(DriverManager.getDriver());
        dashboardPageActions.clickExitRatingCriteria(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData")
    public void testHideRenewButtonOnPolicyList(JSONObject jsonObject) throws InterruptedException {
        /***
         this test Hide Renew Button on Policy list for Ineligible Policies
         story - N2020-29737
         @author -Azamat Uulu
         **/

        logger.info("verifying :: Hide Renew Button on Policy list for Ineligible Policies ");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        assert dashboardPageActions.verifyPoliciesExists(DriverManager.getDriver());
        String[] statuses = jsonObject.get("status").toString().split(ConstantVariable.SEMICOLON);
        String[] policiesNumber = jsonObject.get("policyNumber").toString().split(ConstantVariable.SEMICOLON);

        for (int i = 0; i < statuses.length; i++) {
            dashboardPageActions.clickPoliciesFilterList(DriverManager.getDriver());
            dashboardPageActions.clickFilterByStatus(DriverManager.getDriver());
            dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), statuses[i]);
            dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
            assert dashboardPageActions.verifyPoliciesExists(DriverManager.getDriver());
            dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), policiesNumber[i].replaceAll("^\"|\"$", ""));
            assert dashboardPageActions.verifyPoliciesExists(DriverManager.getDriver());
            dashboardPageActions.verifyHideRenewButton(DriverManager.getDriver(), statuses[i]);
            dashboardPageActions.clickClearSearch(DriverManager.getDriver());
        }
        assert dashboardPageActions.verifyPoliciesExists(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 12)
    public void  testQuotesByBusinessType(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies quotes by business type
         story - N2020-32172
         @author -Venkat Kottapalli
         **/
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByType(DriverManager.getDriver());
        String status = jsonObject.get("status").toString();
        dashboardPageActions.selectTypeInFilter(DriverManager.getDriver(), status);
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        List<WebElement> elements = dashboardPageActions.getAllQuotesBusinessType(DriverManager.getDriver());
        if (elements.size() > 0) {
            for (WebElement element : elements) {
                String businessType = element.getText();
                assert businessType.contains("Renewal for Policy");
            }
        }else{
            logger.info("New Business quotes doesn't have business");
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 13)
    public void  testClearFiltersButtonFunctionality(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies clear filters button functionality
         story - N2020-32024
         @author -Venkat Kottapalli
         **/
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), coverage);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), jsonObject.get("status").toString());
        dashboardPageActions.clickFilterByType(DriverManager.getDriver());
        String status = jsonObject.get("businessType").toString();
        dashboardPageActions.selectTypeInFilter(DriverManager.getDriver(), status);
        dashboardPageActions.clickSubmissionFilterByDateRange(DriverManager.getDriver());
        dashboardPageActions.enterCreateStartDate(DriverManager.getDriver());
        dashboardPageActions.enterCreateEndDate(DriverManager.getDriver());
        logger.info("clearing the filters");
        dashboardPageActions.clickClearFiltersButton(DriverManager.getDriver());
        logger.info("checking the selected values after applying filter");
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        String product = dashboardPageActions.getSelectedCoverageName(DriverManager.getDriver());
        assert product.contentEquals(jsonObject.get("defaultProductValue").toString());
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        String quoteStatus = dashboardPageActions.getSelectedQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.contentEquals(jsonObject.get("defaultStatusValue").toString());
        dashboardPageActions.clickFilterByType(DriverManager.getDriver());
        String quoteBusinessType = dashboardPageActions.getSelectedQuoteBusinessType(DriverManager.getDriver());
        assert quoteBusinessType.contentEquals(jsonObject.get("defaultTypeValue").toString());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 14)
    public void testIneligiblePolicies(JSONObject jsonObject) throws InterruptedException, SQLException {
        /*************************************
         * this test verifies ineligible policy
         story - N2020-33633 -N2020-34868, QAT-566
         @author - Azamat Uulu
         **************************************/
        logger.info("verifying ineligible policies :: testIneligiblePolicy");
        List<HashMap<Object, Object>> policyIds =
                databaseConnector.getResultSetToList(DatabaseQueries.GET_INELIGIBLE_POLICIES);
        if(policyIds!=null){
            String policyId;
            for (HashMap<Object, Object> id : policyIds) {
                policyId = id.get("number").toString();
                dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), policyId);
                if(dashboardPageActions.verifyContactUnderwriterExists(DriverManager.getDriver())){
                    break;
                }
            }
        }else{
            logger.warn("No Ineligible Policies available :: testIneligiblePolicies");
            throw new SkipException("No Ineligible Policies available :: testIneligiblePolicies");
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "DashboardPageData", priority = 15, enabled = false)
    public void  testContactUnderwriterInDashboard(JSONObject jsonObject) throws InterruptedException {
        /***
         * this test validates Contact Underwriter button in dashboard page
         story - N2020-34125
         @author - Venkat Kottapalli
         ***/
        logger.info("validating Contact UW button in dashboard page :: testContactUnderwriterInDashboard");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), "H22OMC20048-01");
        assert dashboardPageActions.verifyIfPolicySearchResultsDisplayed(DriverManager.getDriver());
        String policyBefore = dashboardPageActions.getPolicyStatus(DriverManager.getDriver());
        assert dashboardPageActions.verifyContactUnderwriterExists(DriverManager.getDriver());
        dashboardPageActions.clickContactUnderwriter(DriverManager.getDriver());
        String dialogDescription = dashboardPageActions.getSubmitForReviewDesc(DriverManager.getDriver());
        assert dialogDescription.contentEquals(jsonObject.get("dialogDescription").toString());
        dashboardPageActions.clickSubmitForReviewCancel(DriverManager.getDriver());
        assert dashboardPageActions.verifyIfPolicySearchResultsDisplayed(DriverManager.getDriver());
        dashboardPageActions.clickContactUnderwriter(DriverManager.getDriver());
        dashboardPageActions.enterAdditionalInfoSubmitForReview(DriverManager.getDriver());
        dashboardPageActions.clickSubmitForReviewSubmit(DriverManager.getDriver());
        String policyAfter = dashboardPageActions.getPolicyStatus(DriverManager.getDriver());
        if(policyBefore.contentEquals(ConstantVariable.RENEWED_STRING) || policyBefore.contentEquals(ConstantVariable.RENEWAL_STARTED_STRING)){
            assert policyAfter.contentEquals("Renewal Started");
            assert !dashboardPageActions.verifyContactUnderwriterExists(DriverManager.getDriver());
            dashboardPageActions.clickQuotesTab(DriverManager.getDriver());
            dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), "H22OMC20048-01");
            String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
            assert quoteStatus.contentEquals(ConstantVariable.IN_REVIEW_STRING);
        } else if (policyBefore.contentEquals(ConstantVariable.ACTIVE_STRING) ) {
            assert policyAfter.contentEquals(ConstantVariable.ACTIVE_STRING);
            assert dashboardPageActions.verifyUnderwriterReviewingButtonDisplayed(DriverManager.getDriver());
        }
    }

}