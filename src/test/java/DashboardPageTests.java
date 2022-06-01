import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.ClickHelper;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
    public void testQuotesDashboardUI(Map<String, String> map) {
        /**
         * this test verifies UI of dashboard and Mu Quotes list
         story - N2020-28285, N2020-28287, N2020-28631
         @author - Venkat Kottapalli
         **/
        logger.info("verifying the broker portal dashboard page :: testQuotesDashboardUI");
        assert dashboardPageActions.tmhccLogo(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.profileSettingsIcon(DriverManager.getDriver()).isDisplayed();
        String title = dashboardPageActions.getMyQuotesTabTitle(DriverManager.getDriver()).trim();
        assert title.contentEquals(map.get("myQuotes"));
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        assert dashboardPageActions.signOutLink(DriverManager.getDriver()).isDisplayed();
        List<WebElement> quoteCardsList = dashboardPageActions.getQuoteCardsList(DriverManager.getDriver());
        if (quoteCardsList.size() > 0) {
            assert true;
        } else {
            assert dashboardPageActions.noQuoteFound(DriverManager.getDriver()).isDisplayed();
        }
        List<WebElement> labels = dashboardPageActions.getQuoteTableLabels(DriverManager.getDriver());
        if (labels.size() > 0) {
            assert labels.get(0).getText().equals(map.get("submissionLabel"));
            assert labels.get(1).getText().equals(map.get("dateLabel"));
            assert labels.get(2).getText().equals(map.get("product"));
            assert labels.get(3).getText().equals(map.get("startDateLabel"));
            assert labels.get(4).getText().equals(map.get("endDateLabel"));
            assert labels.get(5).getText().equals(map.get("statusLabel"));
            logger.info("verify quote status color- In Progress");
//            dashboardPageActions.validateQuoteStatusColorCoding(DriverManager.getDriver());
            logger.info("verify quote correct status displayed");
            assert dashboardPageActions.verifyQuoteStatusInTable(DriverManager.getDriver());
        }
        logger.info("verify logout functionality");
        LoginPageActions loginPageActions = dashboardPageActions.logoutApp(DriverManager.getDriver());
        String text = loginPageActions.getWelcomeText(DriverManager.getDriver());
        assert text.contentEquals(map.get("welcomeText"));

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testPoliciesDashboardUI(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies UI of My Policies dashboard
         story - N2020-28286
         @author - Venkat Kottapalli
         **/
        logger.info("verifying the broker portal dashboard page :: testPoliciesDashboardUI");
        String title = dashboardPageActions.getMyPoliciesTabTitle(DriverManager.getDriver()).trim();
        assert title.contentEquals(map.get("policyTitle"));
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        List<WebElement> policyCardsList = dashboardPageActions.getPolicyCardsList(DriverManager.getDriver());
        if (policyCardsList.size() > 0) {
            assert true;
        } else {
            assert dashboardPageActions.noPolicyFound(DriverManager.getDriver()).isDisplayed();
        }
        /* Status color changes would be coming soon with Hexa codes
        dashboardPageActions.validatePolicyStatusColorCoding(DriverManager.getDriver());*/
        List<WebElement> labels = dashboardPageActions.getPolicyTableLabels(DriverManager.getDriver());
        if (labels.size() > 0) {
            String policyLabel = labels.get(0).getText();
            assert policyLabel.equals(map.get("policyLabel"));
            String coverage = labels.get(1).getText();
            assert coverage.equals(map.get("product"));
            String effDateLabel = labels.get(2).getText();
            assert effDateLabel.equals(map.get("effDateLabel"));
            String expDateLabel = labels.get(3).getText();
            assert expDateLabel.equals(map.get("expDateLabel"));
        } else {
            throw new SkipException("No policies were found for the given broker");
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testNewQuoteFieldsValidation(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies the New Quote dialog fields validation
         story - N2020-28289
         @author - Venkat Kottapalli
         **/
        logger.info("validating the fields on New Quote modal dialog :: testNewQuoteFieldsValidation");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        logger.info("validating whether mandatory field text displayed or not");
        assert dashboardPageActions.productRequiredElement(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.nameRequiredElement(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.clickCancelButton(DriverManager.getDriver());
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        logger.info("validating whether the data entered is erased or not");
        String product = dashboardPageActions.productDropdown(DriverManager.getDriver()).getText();
        assert product.equals(map.get("productDefaultText"));
        String name = dashboardPageActions.getApplicantName(DriverManager.getDriver());
        assert name.equals(ConstantVariable.EMPTY_STRING);
        String website = dashboardPageActions.getWebsite(DriverManager.getDriver());
        assert website.equals(ConstantVariable.EMPTY_STRING);
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, map.get("applicantName"), map.get("website"));
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        // waiting for clarification
       // assert dashboardPageActions.websiteRequiredElement(DriverManager.getDriver()).isDisplayed();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testCreateNewQuote(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies creation of new quote
         story - N2020-28291
         @author - Venkat Kottapalli
         **/
        logger.info("verifying creating new quote creation :: testCreateNewQuote");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, map.get("applicantName"), map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        assert insuredPageActions.newInsuredButton(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.searchAgainButton(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.verifyInsuredSearchResult(DriverManager.getDriver(), map.get("applicantName"), map.get("website"));
        insuredPageActions.clickContinueInsuredButton(DriverManager.getDriver());
        boolean duplicateDialog = insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver());
        if (!duplicateDialog) {
            assert insuredPageActions.continueInsuredSearch(DriverManager.getDriver()).isDisplayed();
        } else {
            logger.info("Can't continue to insured search page, duplicate submission displayed");
            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
            dashboardPageActions.clickNewQuote(DriverManager.getDriver());
            String newInsuredName = FakeDataHelper.fullName();
            String newInsuredWebsite = FakeDataHelper.website();
            dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName, newInsuredWebsite);
            dashboardPageActions.clickContinueButton(DriverManager.getDriver());
            boolean value = insuredPageActions.isCreateNewInsuredTextDisplayed(DriverManager.getDriver());
            assert value;
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testBrokerFilteringSubmissionsList(Map<String, String> map) throws InterruptedException, ParseException {
        /**
         * this test verifies broker filtering the submissions list
         story - N2020-28566
         @author - Venkat Kottapalli
         **/
        logger.info("verifying broker filtering the submission list :: testBrokerFilteringSubmissionsList");
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
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("productName"));
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
                    if(status.contentEquals("Active")){
                        if(s.contentEquals(status)||s.contentEquals("Order Placed")||s.contentEquals("In Review")){
                            assert true;
                        }
                    }else{
                        assert s.contentEquals(status);
                    }

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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testBrokerFilteringPoliciesList(Map<String, String> map) throws InterruptedException, ParseException {
        /**
         * this test verifies broker filtering the policies list
         story - N2020-28565
         @author - Venkat Kottapalli
         **/
        logger.info("verifying broker filtering the policies list :: testBrokerFilteringPoliciesList");
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
        if(dates != null){
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

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testPresenceOfContinueButtonOnQuotes(Map<String, String> map) throws InterruptedException {
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testBrokerSearchRelatedRecords(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies search results for related Records
         story - N2020-28288
         @author - Azamat Uulu
         **/
        logger.info("verifying broker can search for related records :: testBrokerSearchRelatedRecords");
        String actualReferenceId = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualReferenceId);
        String expectedReferenceId = dashboardPageActions.getFirstAvailableReferenceId(DriverManager.getDriver());
        assert actualReferenceId.equals(expectedReferenceId);

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        String actualQuoteName = dashboardPageActions.getFirstQuoteLegalName(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualQuoteName);
        String expectedQuoteName = dashboardPageActions.getFirstQuoteLegalName(DriverManager.getDriver());
        assert actualQuoteName.equals(expectedQuoteName);

        dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        String actualPolicyName = dashboardPageActions.getFirstPolicyLegalName(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), actualPolicyName);
        String expectedPolicyName = dashboardPageActions.getFirstPolicyLegalName(DriverManager.getDriver());
        assert actualPolicyName.contains(expectedPolicyName);

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
    public void testSubmissionRenewal(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies submission renewal
         story - N2020-28481
         @author -
         **/
        logger.info("verifying submission renewal ::  testSubmissionRenewal");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickPolicyFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectPolicyStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.renewSubmission(DriverManager.getDriver());
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testSortQuoteList(Map<String, String> map) {
        /***
         this test Sort the My Quotes List
         story - N2020-29952
         @author -Azamat Uulu
         **/
        logger.info("verifying sort my quote list ::  sortQuoteList");
        String actual = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        dashboardPageActions.clickSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByNewest(DriverManager.getDriver());
        dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        String expected = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        assert actual.equals(expected);
        dashboardPageActions.clickSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByOldest(DriverManager.getDriver());
        String actualOldestDate = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        dashboardPageActions.clickSortBy(DriverManager.getDriver());
        dashboardPageActions.clickSortByOldest(DriverManager.getDriver());
        String expectedOldestDate = dashboardPageActions.getFirstAvailableCreatedDate(DriverManager.getDriver());
        assert actualOldestDate.equals(expectedOldestDate);

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testSortPolicyList(Map<String, String> map) throws InterruptedException, ParseException {
        /***
         this test Sort my Policy List
         story - N2020-29736
         @author -Azamat Uulu
         **/

        logger.info("verifying sort my quote list ::  sortPolicyList");
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
        } else {
            logger.error("======================== Sorting my policy is not working as expected ===================================");
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testSupportRequestFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies sup of new insured
         story - N2020-28346
         @author - Venkat Kottapalli
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData", enabled = false)
    public void testBrokersCanContinueRenewalSubmission(Map<String, String> map) throws InterruptedException {
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testHideRenewButtonOnPolicyList(Map<String, String> map) throws InterruptedException {
        /***
         this test Hide Renew Button on Policy list for Ineligible Policies
         story - N2020-29737
         @author -Azamat Uulu
         **/

        logger.info("verifying :: Hide Renew Button on Policy list for Ineligible Policies ");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        assert dashboardPageActions.verifyPoliciesExists(DriverManager.getDriver());
        String[] statuses = map.get("status").split(ConstantVariable.SEMICOLON);
        String[] policiesNumber = map.get("policyNumber").split(ConstantVariable.SEMICOLON);

        for (int i = 0; i < statuses.length; i++) {
            dashboardPageActions.clickFilterList(DriverManager.getDriver());
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void  testQuotesByBusinessType(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies quotes by business type
         story - N2020-32172
         @author -Venkat Kottapalli
         **/
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByType(DriverManager.getDriver());
        String status = map.get("status");
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void  testClearFiltersButtonFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies clear filters button functionality
         story - N2020-32024
         @author -Venkat Kottapalli
         **/
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("product"));
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickFilterByType(DriverManager.getDriver());
        String status = map.get("businessType");
        dashboardPageActions.selectTypeInFilter(DriverManager.getDriver(), status);
        dashboardPageActions.clickSubmissionFilterByDateRange(DriverManager.getDriver());
        dashboardPageActions.enterCreateStartDate(DriverManager.getDriver());
        dashboardPageActions.enterCreateEndDate(DriverManager.getDriver());
        logger.info("clearing the filters");
        dashboardPageActions.clickClearFiltersButton(DriverManager.getDriver());
        logger.info("checking the selected values after applying filter");
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        String product = dashboardPageActions.getSelectedProductName(DriverManager.getDriver());
        assert product.contentEquals(map.get("defaultProductValue"));
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        String quoteStatus = dashboardPageActions.getSelectedQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.contentEquals(map.get("defaultStatusValue"));
        dashboardPageActions.clickFilterByType(DriverManager.getDriver());
        String quoteBusinessType = dashboardPageActions.getSelectedQuoteBusinessType(DriverManager.getDriver());
        assert quoteBusinessType.contentEquals(map.get("defaultTypeValue"));
    }

}