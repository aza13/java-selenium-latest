package dashboardPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.LoginPageActions;
import utils.dataProvider.TestDataProvider;

import java.sql.Driver;
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
        String titlePolicies = dashboardPageActions.getMyPoliciesTabTitle(DriverManager.getDriver());
        assert titlePolicies.contentEquals(map.get("myPolicies"));
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        assert dashboardPageActions.profileLink(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.signOutLink(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
//        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        List<WebElement> labels =  dashboardPageActions.getQuoteTableLabels(DriverManager.getDriver());
        assert labels.get(0).getText().equals(map.get("submissionLabel"));
        assert labels.get(1).getText().equals(map.get("dateLabel"));
        assert labels.get(2).getText().equals(map.get("startDateLabel"));
        assert labels.get(3).getText().equals(map.get("endDateLabel"));
        assert labels.get(4).getText().equals(map.get("statusLabel"));
        logger.info("verify logout functionality");
        dashboardPageActions.validateQuoteStatusColorCoding(DriverManager.getDriver());
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
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        /* Status color changes would be coming soon with hexa codes
        dashboardPageActions.validatePolicyStatusColorCoding(DriverManager.getDriver());*/
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testNewQuoteFieldsValidation(Map<String, String> map) throws InterruptedException {
        /**
         story - N2020-28289, test cases -
         **/
        logger.info("validating the fields on New Quote modal dialog :: testNewQuoteFieldsValidation");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
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
        assert name.equals("");
        String website = dashboardPageActions.getWebsite(DriverManager.getDriver());
        assert website.equals("");
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testCreateNewQuote(Map<String, String> map) throws InterruptedException {
        /**
         story - N2020-28291
         **/
        logger.info("verifying creating new quote creation :: testCreateNewQuote");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        assert insuredPageActions.newInsuredButton(DriverManager.getDriver()).isDisplayed();

    }


}
