import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import enums.ConstantVariable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.LoginPageActions;
import utils.dataProvider.TestDataProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        String policyLabel = labels.get(0).getText();
        assert policyLabel.equals(map.get("policyLabel"));
        String effDateLabel = labels.get(1).getText();
        assert effDateLabel.equals(map.get("effDateLabel"));
        String expDateLabel = labels.get(2).getText();
        assert expDateLabel.equals(map.get("expDateLabel"));
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
        insuredPageActions.clickSelectInsuredButton(DriverManager.getDriver());
        assert insuredPageActions.continueInsuredSearch(DriverManager.getDriver()).isDisplayed();
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
         * this test verifies whether continue button should be displayed or not
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
            String continueBtnXpath = null;
            List<String> statuses = dashboardPageActions.getAllQuotesStatus(DriverManager.getDriver());
            for (int i = 1; i <= quoteCount; i++) {
                continueBtnXpath = "(//div[@data-qa='quote_card']/div/div[last()]//button)["+i+"]";
                switch (statuses.get(i)) {
                    case ConstantVariable.CANCELLED_STRING:
                    case ConstantVariable.DECLINED_STRING:
                    case ConstantVariable.IN_REVIEW_STRING:
                        assert !DriverManager.getDriver().findElement(By.xpath(continueBtnXpath)).isDisplayed();
                        break;
                    case ConstantVariable.ACTIVE_STRING:
                        assert DriverManager.getDriver().findElement(By.xpath(continueBtnXpath)).isDisplayed();
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


}
